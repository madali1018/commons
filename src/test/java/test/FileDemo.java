package test;

import java.io.File;

/**
 * Created by madali on 2018/4/2.
 * <p>
 * Java  递归读取文件夹下的文件
 */
public class FileDemo {

    public static void main(String[] args) {

//        testLoopOutAllFileName("D:\\");
        testLoopOutAllFileName("D:\\111111111111记录");
    }

    private static void testLoopOutAllFileName(String testFileDir) {

        if (testFileDir == null) {
            //因为new File(null)会空指针异常,所以要判断下
            return;
        }

        File[] testFile = new File(testFileDir).listFiles();
        if (testFile == null) {
            return;
        }

        for (File file : testFile) {
            if (file.isFile()) {
                System.out.println(file.getName());
            } else if (file.isDirectory()) {
                System.out.println("-------this is a directory, and its files are as follows:-------");
                testLoopOutAllFileName(file.getPath());
            } else {
                System.out.println("文件读入有误！");
            }
        }

    }

}
