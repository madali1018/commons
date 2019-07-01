package com.mada.commons.demo.io.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Java8 Files类读写文件操作示例
 * <p>
 * Created by madali on 2019/5/17 14:14
 */
public class FileDemo2 {

    @Test
    public void t1() {
        String filePath = "D:\\houseId.txt";

        try {
            // 将字符串写入到文件。Files默认使用UTF-8编码
            Files.write(Paths.get(filePath), "java8字符串1".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将内容追加到指定文件可以使用write方法的第三个参数OpenOption:
        try {
            Files.write(Paths.get(filePath), "java8按行写入-行1".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(filePath), "java8按行写入-行2".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // 使用Files类快速实现文件操作：按行读取文件
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                System.out.println(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取文件内容
        byte[] data;
        try {
            data = Files.readAllBytes(Paths.get(filePath));
            String content = new String(data, StandardCharsets.UTF_8);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
