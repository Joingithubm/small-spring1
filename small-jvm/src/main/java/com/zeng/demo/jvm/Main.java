package com.zeng.demo.jvm;

import com.zeng.demo.jvm.classpath.Classpath;

import java.io.IOException;

/**
 * @Author: fanchao
 * @Date: 2024-09-12 21:02
 * @Description:
 **/
public class Main {
    public static void main(String[] args) {

        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if (cmd.versionFlag) {
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        System.out.printf("classpath:%s class:%s args:%s\n", classpath, cmd.getMainClass(), cmd.getAppArgs());
        //获取className
        String className = cmd.getMainClass().replace(".", "/");
        try {
            byte[] classData = classpath.readClass(className);
            System.out.println("classData:");
            for (byte b : classData) {
                // 十六进制输出
                System.out.println(String.format("%02x", b & 0xff) + " ");
            }
        } catch (IOException e) {
            System.out.println("Could not find or load main class" + cmd.getMainClass());
            e.printStackTrace();
        }
    }
}
