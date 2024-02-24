cmake_minimum_required(VERSION 3.22.1)

project("bsdiff-jni")

find_package(JNI REQUIRED)

add_subdirectory(../../../../ bsdiff-binding)

add_library(bsdiff-jni SHARED)
target_sources(bsdiff-jni PRIVATE jni.c)
target_link_libraries(bsdiff-jni PRIVATE bsdiff-library JNI::JNI)
set_target_properties(bsdiff-jni PROPERTIES OUTPUT_NAME bsdiff)
