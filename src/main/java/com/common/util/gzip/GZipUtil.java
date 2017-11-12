package com.common.util.gzip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by madali on 2017/4/27.
 */
public class GZipUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GZipUtil.class);

    private static final int BUFFER = 1024;

    /**
     * 数据压缩
     *
     * @param data
     * @return
     */
    public static byte[] compress(byte[] data) {

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            //压缩
            compress(bais, baos);

            data = baos.toByteArray();
        } catch (Throwable t) {

            LOGGER.error(t.getMessage(), t);

            throw new RuntimeException(t);
        } finally {
            try {
                baos.flush();
                baos.close();

                bais.close();
            } catch (IOException ioe) {
                LOGGER.error(ioe.getMessage(), ioe);
            }
        }

        return data;
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     */
    public static byte[] decompress(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            //解压缩
            decompress(bais, baos);

            data = baos.toByteArray();
        } catch (Throwable t) {

            LOGGER.error(t.getMessage(), t);

            throw new RuntimeException(t);
        } finally {
            try {
                baos.flush();
                baos.close();

                bais.close();
            } catch (IOException ioe) {
                LOGGER.error(ioe.getMessage(), ioe);
            }
        }

        return data;
    }

    private static void compress(InputStream is, OutputStream os) throws Exception {

        GZIPOutputStream gos = new GZIPOutputStream(os);

        int count;
        byte[] data = new byte[BUFFER];
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            gos.write(data, 0, count);
        }

        gos.finish();

        gos.flush();
        gos.close();
    }

    private static void decompress(InputStream is, OutputStream os) throws Exception {

        GZIPInputStream gis = new GZIPInputStream(is);

        int count;
        byte[] data = new byte[BUFFER];
        while ((count = gis.read(data, 0, BUFFER)) != -1) {
            os.write(data, 0, count);
        }

        gis.close();
    }
}
