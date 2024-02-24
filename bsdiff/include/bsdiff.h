#ifndef BSDIFF_BSDIFF_H
#define BSDIFF_BSDIFF_H

#ifdef __cplusplus
extern "C" {
#endif

#define BSDIFF_SUCCESS (0)
#define BSDIFF_ERR_IO (1)
#define BSDIFF_ERR_ALLOC (2)
#define BSDIFF_ERR_BZ2 (3)
#define BSDIFF_ERR_CORRUPT_PATCH (4)

int bsdiff(const char *old_path, const char *new_path, const char *patch_path, char **errmsg);

int bspatch(const char *old_path, const char *new_path, const char *patch_path, char **errmsg);

#ifdef __cplusplus
}
#endif

#endif //BSDIFF_BSDIFF_H
