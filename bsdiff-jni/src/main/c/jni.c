#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include <bsdiff.h>

JNIEXPORT jint JNICALL
Java_com_shsuco_bsdiff_BSDiff_nativeDiff(JNIEnv *env, jclass thiz, jstring old_path,
                                             jstring new_path, jstring patch_path,
                                             jobjectArray msg_out) {
    const char *old = (*env)->GetStringUTFChars(env, old_path, NULL);
    const char *new = (*env)->GetStringUTFChars(env, new_path, NULL);
    const char *patch = (*env)->GetStringUTFChars(env, patch_path, NULL);
    char *msg = NULL;
    int result = bsdiff(old, new, patch, &msg);
    if (msg != NULL) {
        (*env)->SetObjectArrayElement(env, msg_out, 0, (*env)->NewStringUTF(env, msg));
        free(msg);
    }
    (*env)->ReleaseStringUTFChars(env, old_path, old);
    (*env)->ReleaseStringUTFChars(env, new_path, new);
    (*env)->ReleaseStringUTFChars(env, patch_path, patch);
    return result;
}

JNIEXPORT jint JNICALL
Java_com_shsuco_bsdiff_BSDiff_nativePatch(JNIEnv *env, jclass thiz, jstring old_path,
                                              jstring new_path, jstring patch_path,
                                              jobjectArray msg_out) {
    const char *old = (*env)->GetStringUTFChars(env, old_path, NULL);
    const char *new = (*env)->GetStringUTFChars(env, new_path, NULL);
    const char *patch = (*env)->GetStringUTFChars(env, patch_path, NULL);
    char *msg = NULL;
    int result = bspatch(old, new, patch, &msg);
    if (msg != NULL) {
        (*env)->SetObjectArrayElement(env, msg_out, 0, (*env)->NewStringUTF(env, msg));
        free(msg);
    }
    (*env)->ReleaseStringUTFChars(env, old_path, old);
    (*env)->ReleaseStringUTFChars(env, new_path, new);
    (*env)->ReleaseStringUTFChars(env, patch_path, patch);
    return result;
}
