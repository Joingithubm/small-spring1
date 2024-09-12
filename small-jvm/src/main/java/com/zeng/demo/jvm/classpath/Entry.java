package com.zeng.demo.jvm.classpath;

import com.zeng.demo.jvm.classpath.impl.CompositeEntry;
import com.zeng.demo.jvm.classpath.impl.DirEntry;
import com.zeng.demo.jvm.classpath.impl.WildcardEntry;
import com.zeng.demo.jvm.classpath.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

/**
 * @Author: fanchao
 * @Date: 2024-09-12 21:21
 * @Description: 类路径接口
 **/
public interface Entry {

    byte[] readClass(String className) throws IOException;

    static Entry create(String path){
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        if (path.contains("*")) {
            return new WildcardEntry(path);
        }

        if (path.endsWith(".jar") || path.endsWith(".JAR")
                || path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }

        return new DirEntry(path);
    }
}
