#ifndef BSDIFF_BSDIFF_HPP
#define BSDIFF_BSDIFF_HPP

#include <memory>
#include <string>

#include <bsdiff.h>

namespace bsdiff {

constexpr inline int ok = BSDIFF_SUCCESS;

namespace err {

constexpr inline int io = BSDIFF_ERR_IO;
constexpr inline int alloc = BSDIFF_ERR_ALLOC;
constexpr inline int bz2 = BSDIFF_ERR_BZ2;
constexpr inline int corrupt_patch = BSDIFF_ERR_CORRUPT_PATCH;

}; // namespace err

struct result {
  int code;
  std::string msg;

  constexpr inline explicit operator bool() const noexcept {
    return code == ok;
  }
};

inline result diff(const std::string_view &old_path, const std::string &new_path,
                   const std::string &patch_path) {
  char *msg = nullptr;
  int status =
      bsdiff(old_path.c_str(), new_path.c_str(), patch_path.c_str(), &msg);
  if (msg) {
    auto msg_ptr = std::unique_ptr<char, decltype(std::free) *>(msg, std::free);
    return {.code = status, .msg{msg_ptr.get()}};
  }
  return {.code = status, .msg{}};
}

inline result patch(const std::string &old_path, const std::string &new_path,
                    const std::string &patch_path) {
  char *msg = nullptr;
  int status =
      bspatch(old_path.c_str(), new_path.c_str(), patch_path.c_str(), &msg);
  if (msg) {
    auto msg_ptr = std::unique_ptr<char, decltype(std::free) *>(msg, std::free);
    return {.code = status, .msg{msg_ptr.get()}};
  }
  return {.code = status, .msg{}};
}

} // namespace bs

#endif // BSDIFF_BSDIFF_HPP
