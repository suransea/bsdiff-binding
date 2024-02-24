package com.shsuco.bsdiff;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public class BSDiff {
    private static final Result LOAD_RESULT = loadLibrary();

    public static Result diff(File oldFile, File newFile, File patchFile) {
        if (!LOAD_RESULT.isSuccess()) {
            return LOAD_RESULT;
        }
        return diff(oldFile.getAbsolutePath(), newFile.getAbsolutePath(), patchFile.getAbsolutePath());
    }

    public static Result patch(File oldFile, File newFile, File patchFile) {
        if (!LOAD_RESULT.isSuccess()) {
            return LOAD_RESULT;
        }
        return patch(oldFile.getAbsolutePath(), newFile.getAbsolutePath(), patchFile.getAbsolutePath());
    }

    public static Result diff(String oldPath, String newPath, String patchPath) {
        if (!LOAD_RESULT.isSuccess()) {
            return LOAD_RESULT;
        }
        String[] msgOut = new String[1];
        int result = nativeDiff(oldPath, newPath, patchPath, msgOut);
        return new Result(result, msgOut[0]);
    }

    public static Result patch(String oldPath, String newPath, String patchPath) {
        if (!LOAD_RESULT.isSuccess()) {
            return LOAD_RESULT;
        }
        String[] msgOut = new String[1];
        int result = nativePatch(oldPath, newPath, patchPath, msgOut);
        return new Result(result, msgOut[0]);
    }

    private static native int nativeDiff(String oldPath, String newPath, String patchPath, String[] msgOut);

    private static native int nativePatch(String oldPath, String newPath, String patchPath, String[] msgOut);

    private static Result loadLibrary() {
        String resourceName = libraryResourceName();
        if (resourceName == null) {
            return new Result(Result.ERROR_LOAD_LIBRARY, "unsupported platform");
        }
        return loadLibraryFromResource(resourceName);
    }

    private static String libraryResourceName() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
        if (os.contains("win")) {
            return "/libs/" + arch + "-windows/bsdiff.dll";
        } else if (os.contains("mac")) {
            return "/libs/" + arch + "-darwin/libbsdiff.dylib";
        } else if (os.contains("linux")) {
            return "/libs/" + arch + "-linux/libbsdiff.so";
        }
        return null;
    }

    @SuppressWarnings("UnsafeDynamicallyLoadedCode")
    private static Result loadLibraryFromResource(String name) {
        try (InputStream in = BSDiff.class.getResourceAsStream(name)) {
            if (in == null) {
                return new Result(Result.ERROR_LOAD_LIBRARY, "library resource not found: " + name);
            }
            Path path = Files.createTempFile("bsdiff", ".lib");
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
            System.load(path.toAbsolutePath().toString());
            Files.deleteIfExists(path);
            return new Result(Result.SUCCESS, "");
        } catch (Exception e) {
            return new Result(Result.ERROR_LOAD_LIBRARY, e.getMessage());
        }
    }

    public static class Result {
        public static final int SUCCESS = 0;
        public static final int ERROR_IO = 1;
        public static final int ERROR_ALLOC = 2;
        public static final int ERROR_BZ2 = 3;
        public static final int ERROR_CORRUPT_PATCH = 4;
        public static final int ERROR_LOAD_LIBRARY = -1;

        public int code;
        public String msg;

        public Result(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public boolean isSuccess() {
            return code == SUCCESS;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
}
