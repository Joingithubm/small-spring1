package com.zeng.demo.jvm.classpath.impl;

import com.zeng.demo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fanchao
 * @Date: 2024-09-12 21:21
 * @Description: 混合
 **/
public class CompositeEntry implements Entry {


    private final List<Entry> entryList = new ArrayList<Entry>();

    public CompositeEntry(String pathList){
        String[] paths = pathList.split(File.pathSeparator);
        for (String path : paths) {
            entryList.add(Entry.create(path));
        }
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for (Entry entry : entryList) {
            try {
                return entry.readClass(className);
            }catch (Exception ignored){
                // ignored
            }
        }
        throw new IOException("class not found " + className);
    }

    @Override
    public String toString() {
        String[] strs = new String[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            strs[i] = entryList.get(i).toString();
        }
        return String.join(File.pathSeparator, strs);
    }
}
