package com.mada.utils.rc4;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Created by madali on 2019/9/27 15:10
 */
public class Rc4Util {

    public static String decry(String data, String key) {
        if (StringUtils.isEmpty(data) || StringUtils.isEmpty(key)) {
            return null;
        }

        return new String(rc4Base(hexStrToBytes(data), key));
    }

    public static byte[] encry(String data, String key) throws Exception {
        if (StringUtils.isEmpty(data) || StringUtils.isEmpty(key)) {
            return null;
        }

        return rc4Base(data.getBytes("UTF-8"), key);
    }

    public static String encryToStr(String data, String key) throws Exception {
        if (StringUtils.isEmpty(data) || StringUtils.isEmpty(key)) {
            return null;
        }

        byte[] bytes = encry(data, key);
        if (bytes != null) {
            return toHexStr(asString(bytes));
        }

        return null;
    }

    private static String asString(byte[] buf) {
        StringBuffer result = new StringBuffer(buf.length);

        for (byte b : buf) {
            result.append((char) b);
        }

        return result.toString();
    }

    private static byte[] initKey(String aKey) {
        byte[] bytes = aKey.getBytes();
        if (bytes.length == 0) {
            return null;
        }

        byte[] state = new byte[256];
        IntStream.range(0, 256).forEach(i -> state[i] = (byte) i);

        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < 256; i++) {
            index2 = ((bytes[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % bytes.length;
        }

        return state;
    }

    private static String toHexStr(String s) {
        String str = "";

        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s1 = Integer.toHexString(ch & 0xFF);
            if (s1.length() == 1) {
                s1 = '0' + s1;
            }
            str = str + s1;
        }

        return str;
    }

    private static byte[] hexStrToBytes(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        int size = str.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = str.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }

        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        char b0 = (char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        b0 = (char) (b0 << 4);
        char b1 = (char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        return (byte) (b0 ^ b1);
    }

    private static byte[] rc4Base(byte[] input, String key) {
        int x = 0;
        int y = 0;
        byte[] bytes = initKey(key);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((Objects.requireNonNull(bytes)[x] & 0xff) + y) & 0xff;
            byte tmp = bytes[x];
            bytes[x] = bytes[y];
            bytes[y] = tmp;
            xorIndex = ((bytes[x] & 0xff) + (bytes[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ bytes[xorIndex]);
        }

        return result;
    }

}
