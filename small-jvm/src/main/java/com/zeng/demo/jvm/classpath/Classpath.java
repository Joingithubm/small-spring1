package com.zeng.demo.jvm.classpath;

import com.zeng.demo.jvm.classpath.impl.CompositeEntry;
import com.zeng.demo.jvm.classpath.impl.WildcardEntry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author: fanchao
 * @Date: 2024-09-12 21:21
 * @Description:
 **/
public class Classpath {

    private Entry bootstrapClasspath; //启动类路径
    private Entry extensionClasspath; //扩展类路径
    private Entry userClasspath;      //用户类路径

    public Classpath(String jreOption, String cpOption){
        // 启动类&拓展类 “C:\\ Java\jdk1.8\jre”
        bootstrapAndExtensionClasspath(jreOption);
        // 用户类 /Users/admin/IdeaProjects/idea_jungong/small-spring/small-jvm/src/test/java/com/zeng/demo/test/HelloWorld.java
        parseUserClasspath(cpOption);
    }

    private void parseUserClasspath(String cpOption) {
        if (cpOption == null){
            cpOption = ".";
        }
        userClasspath = Entry.create(cpOption);
    }

    private void bootstrapAndExtensionClasspath(String jreOption) {

        String jreDir = getJreDir(jreOption);
        // ..jre/lib/*
        String jreLibPath = Paths.get(jreDir, "lib") + File.separator + "*";
        bootstrapClasspath = new WildcardEntry(jreLibPath);

        //..jre/lib/ext/*
        String jreExtPath = Paths.get(jreDir, "lib", "ext") + File.separator + "*";
        extensionClasspath = new WildcardEntry(jreExtPath);
    }

    private String getJreDir(String jreOption) {
        if (jreOption != null && Files.exists(Paths.get(jreOption))) {
            return jreOption;
        }

        if (Files.exists(Paths.get("./jre"))){
            return "./jre";
        }
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != null) {
            return Paths.get(javaHome, "jre").toString();
        }
        throw new RuntimeException("Can not find JRE folder!");
    }

    public byte[] readClass(String className) throws IOException {
        className = className + ".class";

        //[readClass]启动类路径
        try {
            return bootstrapClasspath.readClass(className);
        } catch (IOException e) {
            // ignored
        }

        //[readClass]拓展类路径
        try {
            return extensionClasspath.readClass(className);
        } catch (IOException e) {
            // ignored
        }

        //[readClass]用户类
        return userClasspath.readClass(className);
    }

}
