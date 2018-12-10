package com.mada.commons.demo.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JAVA解析Excel工具：阿里easyexcel
 * <p>
 * Created by madali on 2018/12/10 10:59
 */
public class TestExcel {

    // 读excel
    @Test
    public void testRead() {
        InputStream inputStream = TestExcel.class.getClassLoader().getResourceAsStream("test-read.xls");
        try {
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, new AnalysisEventListener() {
                @Override
                public void invoke(Object obj, AnalysisContext analysisContext) {
                    System.out.println("当前sheet" + analysisContext.getCurrentSheet().getSheetNo()
                            + " 当前行：" + analysisContext.getCurrentRowNum()
                            + " 当前data:" + obj);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    System.out.println("excel读取完毕.");
                }
            });
            reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 写excel
    @Test
    public void testWrite() throws FileNotFoundException {
        String dirPath = System.getProperty("user.dir") + "\\exceltest";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        OutputStream out = new FileOutputStream(dirPath + "\\test-write.xls");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
            //写第一个sheet
            Sheet sheet = new Sheet(2, 3, PersonInfo.class);
            writer.write(getPersonInfoList(), sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<PersonInfo> getPersonInfoList() {
        List<PersonInfo> list = new ArrayList<>();

        PersonInfo personInfo1 = new PersonInfo("name1", 16, "16@qq.com");
        PersonInfo personInfo2 = new PersonInfo("name2", 17, "17@qq.com");
        PersonInfo personInfo3 = new PersonInfo("name3", 18, "18@qq.com");

        list.add(personInfo1);
        list.add(personInfo2);
        list.add(personInfo3);

        return list;
    }

}
