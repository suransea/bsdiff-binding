cmake_minimum_required(VERSION 3.14)

project(bsdiff-binding)

set(CMAKE_C_STANDARD 11)
set(CMAKE_CXX_STANDARD 17)

add_subdirectory(bsdiff)

add_library(bsdiff-cpp INTERFACE)
target_sources(bsdiff-cpp INTERFACE bsdiff-cpp/include/bsdiff.hpp)
target_include_directories(bsdiff-cpp INTERFACE bsdiff-cpp/include)
target_link_libraries(bsdiff-cpp INTERFACE bsdiff-library)

if (APPLE)
    add_library(bsdiff-objc)
    target_sources(bsdiff-objc PRIVATE bsdiff-objc/BSDiff.m INTERFACE bsdiff-objc/include/BSDiff.h)
    target_include_directories(bsdiff-objc PUBLIC bsdiff-objc/include)
    target_link_libraries(bsdiff-objc PRIVATE "-framework Foundation" bsdiff-library)
    set_target_properties(bsdiff-objc PROPERTIES OUTPUT_NAME bsdiff)
endif ()
