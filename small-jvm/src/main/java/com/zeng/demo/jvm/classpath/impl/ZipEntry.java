package com.zeng.demo.jvm.classpath.impl;

import com.zeng.demo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.*;

/**
 * @Author: fanchao
 * @Date: 2024-09-12 21:47
 * @Description: zip/zar 文件形式类路径
 **/
public class ZipEntry implements Entry {

    private Path absolutePath;

    public ZipEntry(String path){
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        try(FileSystem zipFs = FileSystems.newFileSystem(absolutePath, null)) {
            return Files.readAllBytes(zipFs.getPath(className));
        }
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}
