#include <stdio.h>
#include <err.h>

#include "bsdiff.h"

int main(int argc, char *argv[]) {
    if (argc != 4) errx(1, "usage: %s oldfile newfile patchfile", argv[0]);
    char *msg = NULL;
    int result = bsdiff(argv[1], argv[2], argv[3], &msg);
    if (result != BSDIFF_SUCCESS) {
        errx(result, "%s", msg);
    }
    return 0;
}
