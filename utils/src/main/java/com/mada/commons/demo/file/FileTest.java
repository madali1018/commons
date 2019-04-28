package com.mada.commons.demo.file;

import org.junit.Test;

import java.io.*;
import java.util.Objects;

/**
 * Created by madali on 2019/3/20 16:19
 */
public class FileTest {

    @Test
    public void t1() {
        readFile();
        writeFile();
    }

    private static String filePath = "D:\\houseId.txt";

    private static void readFile() {
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
                if (br != null) {
                    br.close();
                }

                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeFile() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            for (int i = 0; i < 20; i++) {
                bufferedWriter.write(10000 + i + "\r\n");
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

}
