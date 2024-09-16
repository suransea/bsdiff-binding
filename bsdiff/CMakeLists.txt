cmake_minimum_required(VERSION 3.14)

project(bsdiff)

set(CMAKE_C_STANDARD 11)

include(FetchContent)

set(ENABLE_STATIC_LIB ON CACHE BOOL "" FORCE) # for bzip2
FetchContent_Declare(
        bzip2
        GIT_REPOSITORY https://gitlab.com/bzip2/bzip2.git
        GIT_TAG 66c46b8c9436613fd81bc5d03f63a61933a4dcc3
)
FetchContent_MakeAvailable(bzip2)
set(BZIP2_INCLUDE_DIR ${CMAKE_BINARY_DIR}/_deps/bzip2-src)

# Executable
add_executable(bsdiff)
add_executable(bspatch)

target_sources(bsdiff PRIVATE bsdiff.c bsdiff_main.c)
target_sources(bspatch PRIVATE bspatch.c bspatch_main.c)

target_include_directories(bsdiff PRIVATE include ${BZIP2_INCLUDE_DIR})
target_include_directories(bspatch PRIVATE include ${BZIP2_INCLUDE_DIR})

target_link_libraries(bsdiff PRIVATE bz2_static)
target_link_libraries(bspatch PRIVATE bz2_static)

# C Library
add_library(bsdiff-c)
target_sources(bsdiff-c PRIVATE bsdiff.c bspatch.c INTERFACE include/bsdiff.h)
target_include_directories(bsdiff-c PRIVATE ${BZIP2_INCLUDE_DIR} PUBLIC include)
target_link_libraries(bsdiff-c PRIVATE bz2_static)
set_target_properties(bsdiff-c PROPERTIES OUTPUT_NAME bsdiff)
