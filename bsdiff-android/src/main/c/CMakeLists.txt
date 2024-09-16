cmake_minimum_required(VERSION 3.22.1)

project("bsdiff-android")

add_subdirectory(../../../.. bsdiff-binding)

add_library(bsdiff-android SHARED)
target_sources(bsdiff-android PRIVATE jni.c)
target_link_libraries(bsdiff-android PRIVATE bsdiff-c)
set_target_properties(bsdiff-android PROPERTIES OUTPUT_NAME bsdiff)
