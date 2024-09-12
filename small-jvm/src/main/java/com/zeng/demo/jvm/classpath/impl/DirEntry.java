package com.zeng.demo.jvm.classpath.impl;

import com.zeng.demo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: fanchao
 * @Date: 2024-09-12 21:32
 * @Description:
 **/
public class DirEntry implements Entry {


    private Path absolutePath;

    public DirEntry(String path){
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }


    @Override
    public byte[] readClass(String className) throws IOException {
        return Files.readAllBytes(absolutePath.resolve(className));
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}
