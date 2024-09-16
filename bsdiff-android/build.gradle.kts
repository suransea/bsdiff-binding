plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.shsuco.android.bsdiff"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        prefabPublishing = true
    }
    externalNativeBuild {
        cmake {
            path("src/main/c/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    prefab {
        create("bsdiff-c") {
            headers = "../bsdiff/include"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
