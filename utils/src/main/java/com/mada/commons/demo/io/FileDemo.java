package com.mada.commons.demo.io;

import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Created by madali on 2019/5/10 15:12
 */
public class FileDemo {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
    public void t1() {
        String dirPath = System.getProperty("user.dir") + "\\filetest";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            // mkdir只会创建一级目录，mkdirs会创建多级目录
            if (dir.mkdirs()) {
                // 根据日期动态创建文件
                String path = dirPath + "\\" + dateTimeFormatter.format(LocalDate.now());
                File file = new File(path);
                if (!file.exists()) {
                    if (file.mkdirs()) {
                        String fileName = "1.txt";
                        File file2 = new File(path + "\\" + fileName);
                        if (!file2.exists()) {
                            try {
                                System.out.println(file2.getPath() + "创建结果:" + file2.createNewFile());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void t2() {
        String filePath = "D:\\houseId.txt";

        writeFile(filePath);
        readFile(filePath);
    }

    private void writeFile(String filePath) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            for (int i = 0; i < 10; i++) {
                bufferedWriter.write("数据----" + i + "\r\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(bufferedWriter);
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void readFile(String filePath) {
        FileReader reader = null;
        BufferedReader br = null;
        try {
            reader = new FileReader(filePath);
            br = new BufferedReader(reader);

            String line;
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
