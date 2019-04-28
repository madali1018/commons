package com.mada.commons.demo.file;

import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileDemo {

    // 根据日期动态创建文件
    @Test
    public void test1() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            list.add(i);
        }
        map.put(1, list);

        loopMap(map);
    }

    private static void loopMap(Map<Integer, List<Integer>> map) {
        List<String> list = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int cityId = entry.getKey();
            List<Integer> xqIdList = entry.getValue();
            int xqIdSize = xqIdList.size();

            for (int i = 0; i < xqIdSize; i += 50) {
                int num = (i % 50 == 0) ? (i / 50) : (i / 50 + 1);
                String fileName = cityId + "-" + num + ".txt";
                list.add(fileName);
            }
        }

        Date date = new Date();
        // 在当前路径下，根据日期创建文件夹
        String path = System.getProperty("user.dir") + "\\filetest\\" + new SimpleDateFormat("yyyy-MM-dd").format(date);
        //如果不存在,创建文件夹
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        OutputStream out = null;
        for (String s : list) {
            try {
                out = new FileOutputStream(path + "\\" + s);
                System.out.println("================filename:" + s + "创建成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    Objects.requireNonNull(out).flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void t2() throws IOException {
        Date date = new Date();
        // 在当前路径下，根据日期创建文件夹
        String path = System.getProperty("user.dir") + "\\filetest\\" + new SimpleDateFormat("yyyy-MM-dd").format(date);
        //如果不存在,创建文件夹
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String fileName = "1.txt";
        OutputStream out = new FileOutputStream(path + "\\" + fileName);
        out.flush();
        out.close();
    }

}
