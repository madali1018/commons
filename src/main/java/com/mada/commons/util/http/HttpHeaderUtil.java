package com.mada.commons.util.http;

import javax.servlet.http.HttpServletRequest;

/**
 * 从http header中获取数据的工具
 * <p>
 * Created by madali on 2018/12/26 17:54
 */
public class HttpHeaderUtil {

    public static String getHeader(HttpServletRequest request, String header, String... alias) {
        String result = request.getHeader(header);
        if (null != result) {
            return result;
        }
        for (String s : alias) {
            result = request.getHeader(s);
            if (null != result) {
                return result;
            }
        }
        return null;
    }

}
