// swift-tools-version: 5.4
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "BSDiff",
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "BSDiffSwift",
            targets: ["BSDiffSwift", "BSDiffC"]),
        .library(
            name: "BSDiffObjc",
            targets: ["BSDiffObjc", "BSDiffC"]),
        .library(
            name: "BSDiffC",
            targets: ["BSDiffC"]),
        .executable(
            name: "bsdiff",
            targets: ["bsdiff"]),
        .executable(
            name: "bspatch",
            targets: ["bspatch"]),
    ],
    dependencies: [
        // Dependencies declare other packages that this package depends on.
        // .package(url: /* package url */, from: "1.0.0"),
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .target(
            name: "BSDiffSwift",
            dependencies: ["BSDiffC"],
            path: "bsdiff-swift/Sources"),
        .target(
            name: "BSDiffC",
            dependencies: [],
            path: "bsdiff",
            sources: [
                "bsdiff.c",
                "bspatch.c",
            ],
            cSettings: [
                .headerSearchPath("include")
            ],
            linkerSettings: [
                .linkedLibrary("bz2")
            ]),
        .target(
            name: "BSDiffObjc",
            dependencies: ["BSDiffC"],
            path: "bsdiff-objc",
            cSettings: [
                .headerSearchPath("include")
            ]),
        .executableTarget(
            name: "bsdiff",
            dependencies: ["BSDiffC"],
            path: "bsdiff",
            sources: [
                "bsdiff_main.c",
            ]),
        .executableTarget(
            name: "bspatch",
            dependencies: ["BSDiffC"],
            path: "bsdiff",
            sources: [
                "bspatch_main.c",
            ]),
        .testTarget(
            name: "BSDiffSwiftTests",
            dependencies: ["BSDiffSwift"],
            path: "bsdiff-swift/Tests"),
    ]
)
