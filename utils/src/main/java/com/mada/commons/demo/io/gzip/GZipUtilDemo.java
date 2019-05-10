package com.mada.commons.demo.io.gzip;

import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Created by madali on 2019/5/10 15:50
 */
public class GZipUtilDemo {

    @Test
    public void t1() {
        String s = "XSDjhfsdkdshkgbjk1e234235-9897675@%&……（*￥%";

        byte[] data = s.getBytes(Charset.defaultCharset());
        byte[] data2 = GZipUtil.compress(data);
        byte[] data3 = GZipUtil.decompress(data2);

        System.out.println("原始数据的byte[]:" + new String(data));
        System.out.println("原始数据压缩后的byte[]:" + new String(data2));
        System.out.println("原始数据解压缩后的byte[]:" + new String(data3));
    }

}
