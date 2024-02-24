plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.register<Exec>("compileJniWithCMake") {
    group = "build"
    description = "Compile JNI code using CMake"

    val arch = System.getProperty("os.arch").lowercase()
    val os = System.getProperty("os.name").lowercase()
    val (triple, name) = when {
        os.contains("win") -> "$arch-windows" to "bsdiff.dll"
        os.contains("mac") -> "$arch-darwin" to "libbsdiff.dylib"
        os.contains("linux") -> "$arch-linux" to "libbsdiff.so"
        else -> throw GradleException("Unsupported OS: $os")
    }

    val sourceDir = project.layout.projectDirectory.file("src/main/c").asFile
    val buildDir = project.layout.buildDirectory.file("jni").get().asFile
    val outputDir = buildDir.resolve("output")

    doFirst {
        buildDir.mkdirs()
        exec {
            commandLine(
                "cmake",
                "-S", sourceDir.absolutePath,
                "-B", buildDir.absolutePath,
                "-DCMAKE_BUILD_TYPE=Release",
                "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=${outputDir.absolutePath}"
            )
        }
    }

    commandLine("cmake", "--build", buildDir.absolutePath)

    doLast {
        val resourceDir = project.layout.projectDirectory.file("src/main/resources").asFile
        val dest = resourceDir.resolve("libs/$triple/$name")
        outputDir.resolve(name).copyTo(dest, overwrite = true)
    }
}

tasks.named("build") {
    dependsOn("compileJniWithCMake")
}
