#ifndef BSDIFF_BSERR_H
#define BSDIFF_BSERR_H

#undef err
#define err(_status, ...) \
{ \
    if (errmsg) { \
        size_t _len1 = snprintf(NULL, 0, __VA_ARGS__); \
        const char *_str_err = strerror(errno); \
        size_t _len2 = strlen(_str_err) + 2; \
        *errmsg = malloc(_len1 + _len2 + 1); \
        if (*errmsg) { \
            snprintf(*errmsg, _len1 + 1, __VA_ARGS__); \
            snprintf(*errmsg + _len1, _len2 + 1, ": %s", _str_err); \
        } \
    } \
    status = _status; \
    goto cleanup; \
} \

#undef errx
#define errx(_status, ...) \
{ \
    if (errmsg) { \
        size_t _len = snprintf(NULL, 0, __VA_ARGS__); \
        *errmsg = malloc(_len + 1); \
        if (*errmsg) snprintf(*errmsg, _len + 1, __VA_ARGS__); \
    } \
    status = _status; \
    goto cleanup; \
} \

#endif //BSDIFF_BSERR_H
