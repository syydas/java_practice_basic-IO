package com.thoughtworks.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    /**
     * 完成复制文件夹方法:
     * 1. 把给定文件夹from下的所有文件(包括子文件夹)复制到to文件夹下
     * 2. 保证to文件夹为空文件夹，如果to文件夹不存在则自动创建
     * <p>
     * 例如把a文件夹(a文件夹下有1.txt和一个空文件夹c)复制到b文件夹，复制完成以后b文件夹下也有一个1.txt和空文件夹c
     */
    public static void copyDirectory(File from, File to) throws IOException {
        if (!to.exists()) {
            to.mkdir();
        } else {
            deleteAllFile(to);
        }

        File[] fromDir = from.listFiles();
        if (fromDir != null) {
            for (File fromFile : fromDir) {
                String newFile = File.separator + fromFile.getName();
                if (fromFile.isFile()) {
                    try (InputStream input = new FileInputStream(fromFile.getPath());
                         OutputStream output = new FileOutputStream(to.getPath() + newFile)) {
                        int len;
                        byte[] bytes = new byte[1024];
                        while ((len = input.read(bytes)) != -1) {
                            output.write(bytes, 0, len);
                            output.flush();
                        }
                    }
                } else {
                    copyDirectory(fromFile, new File(to.getPath() + newFile));
                }
            }
        }
    }

    public static void deleteAllFile(File to) {
        if (to.isDirectory()) {
            File[] toDir = to.listFiles();
            assert toDir != null;
            for (File toFile : toDir) {
                deleteAllFile(toFile);
            }
        } else {
            to.delete();
        }
    }
}
