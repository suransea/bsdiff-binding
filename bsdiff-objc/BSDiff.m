#import <Foundation/Foundation.h>

#import "bsdiff.h"

BOOL bsdiff_diff(NSString * _Nonnull oldPath, NSString * _Nonnull newPath, NSString * _Nonnull patchPath,  NSError * _Nullable * _Nullable err) {
    char *msg = NULL;
    const char *old = [oldPath UTF8String];
    const char *new = [newPath UTF8String];
    const char *patch = [patchPath UTF8String];
    int result = bsdiff(old, new, patch, &msg);
    if (result == BSDIFF_SUCCESS) {
        return YES;
    }
    if (err) {
        NSDictionary *userInfo = nil;
        if (msg) {
            userInfo = @{ NSLocalizedDescriptionKey: [NSString stringWithUTF8String:msg] };
        }
        *err = [NSError errorWithDomain:@"com.shsuco.bsdiff" code:result userInfo:userInfo];
    }
    if (msg) free(msg);
    return NO;
}

BOOL bsdiff_patch(NSString * _Nonnull oldPath, NSString * _Nonnull newPath, NSString * _Nonnull patchPath,  NSError * _Nullable * _Nullable err) {
    char *msg = NULL;
    const char *old = [oldPath UTF8String];
    const char *new = [newPath UTF8String];
    const char *patch = [patchPath UTF8String];
    int result = bspatch(old, new, patch, &msg);
    if (result == BSDIFF_SUCCESS) {
        return YES;
    }
    if (err) {
        NSDictionary *userInfo = nil;
        if (msg) {
            userInfo = @{ NSLocalizedDescriptionKey: [NSString stringWithUTF8String:msg] };
        }
        *err = [NSError errorWithDomain:@"com.shcuco.bsdiff" code:result userInfo:userInfo];
    }
    if (msg) free(msg);
    return NO;
}
