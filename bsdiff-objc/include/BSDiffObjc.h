#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, BSDiffError) {
    BSDiffErrorIO = 1,
    BSDiffErrorAlloc = 2,
    BSDiffErrorBZ2 = 3,
    BSDiffErrorCorruptPatch = 4,
};

BOOL bsdiff_diff(NSString * _Nonnull oldPath, NSString * _Nonnull newPath, NSString * _Nonnull patchPath,  NSError * _Nullable * _Nullable err);

BOOL bsdiff_patch(NSString * _Nonnull oldPath, NSString * _Nonnull newPath, NSString * _Nonnull patchPath,  NSError * _Nullable * _Nullable err);
