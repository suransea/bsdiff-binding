package com.shsuco.android.bsdiff;

import java.io.File;

public class BSDiff {

    static {
        System.loadLibrary("bsdiff");
    }

    public static Result diff(File oldFile, File newFile, File patchFile) {
        return diff(oldFile.getAbsolutePath(), newFile.getAbsolutePath(), patchFile.getAbsolutePath());
    }

    public static Result patch(File oldFile, File newFile, File patchFile) {
        return patch(oldFile.getAbsolutePath(), newFile.getAbsolutePath(), patchFile.getAbsolutePath());
    }

    public static Result diff(String oldPath, String newPath, String patchPath) {
        String[] msgOut = new String[1];
        int result = nativeDiff(oldPath, newPath, patchPath, msgOut);
        return new Result(result, msgOut[0]);
    }

    public static Result patch(String oldPath, String newPath, String patchPath) {
        String[] msgOut = new String[1];
        int result = nativePatch(oldPath, newPath, patchPath, msgOut);
        return new Result(result, msgOut[0]);
    }

    private static native int nativeDiff(String oldPath, String newPath, String patchPath, String[] msgOut);

    private static native int nativePatch(String oldPath, String newPath, String patchPath, String[] msgOut);

    public static class Result {
        public static final int SUCCESS = 0;
        public static final int ERROR_IO = 1;
        public static final int ERROR_ALLOC = 2;
        public static final int ERROR_BZ2 = 3;
        public static final int ERROR_CORRUPT_PATCH = 4;

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
