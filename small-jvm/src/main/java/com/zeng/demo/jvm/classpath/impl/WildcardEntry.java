package com.zeng.demo.jvm.classpath.impl;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @Author: fanchao
 * @Date: 2024-09-12 21:37
 * @Description: 通配符路径， 继承CompositeEntry
 **/
public class WildcardEntry extends CompositeEntry{

    public WildcardEntry(String path){
        super(toPathList(path));
    }

    private static String toPathList(String wildcardPath) {
        String baseDir = wildcardPath.replace("*", ""); //remove *
        try {
            return Files.walk(Paths.get(baseDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".jar" )|| p.endsWith(".JAR"))
                    .collect(Collectors.joining(File.pathSeparator));
        }catch (IOException e){
            return "";
        }
    }


}
