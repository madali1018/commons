package com.mada.utils.xml;

import com.mada.commons.entity.lombok.LombokEntity;

import java.io.File;
import java.io.IOException;

public class Demo {

    public static void main(String[] args) {

        String dirPath = System.getProperty("user.dir") + "\\filetest";
        //如果不存在,创建文件
        File dir = new File(dirPath);
        if (!dir.exists()) {
            // mkdir只会创建一级目录，mkdirs会创建多级目录
            dir.mkdirs();

            File file = new File(dirPath + "\\1.xml");
            if (!file.exists()) {
                try {
                    file.createNewFile();

                    LombokEntity lombokEntity = new LombokEntity("name", false, true, 0);
                    XmlUtil.createXmlDocument(lombokEntity, file.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
