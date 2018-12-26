package com.mada.commons.util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

/**
 * java原生HttpURLConnection
 *
 * @Auther: madali
 * @Date: 2018/8/16 10:58
 */
public class HttpClientUtil2 {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil2.class);
    private static final int CONNECT_TIME_OUT = 500;
    private static final int READ_TIME_OUT = 1000;

    static {
        // 设置为true, 否则无法设置"x-"开头的header
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }

    /**
     * 根据method自动调用get或post, post只支持content-type为x-www-form-urlencoded
     *
     * @param url
     * @param request
     * @return
     */
    public static String requestFromUrl(String url, HttpServletRequest request) {
        if (null != request && "POST".equalsIgnoreCase(request.getMethod())) {
            return doPostFromUrl(url, request);
        } else {
            return doGetFromUrl(url, request);
        }
    }

    public static String doGetFromUrl(String url) {
        return doGetFromUrl(url, null);
    }

    public static String doGetFromUrl(String url, HttpServletRequest request) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            if (null != request) {
                setCookieAndHeader(conn, request);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return convertInputStream2String(conn.getInputStream());
            }
        } catch (IOException ex) {
            logger.error("调用HTTP GET接口获取数据出现问题：url=" + url, ex);
        }
        return "";
    }

    public static String doPostFromUrl(String url) {
        return doPostFromUrl(url, null);
    }

    public static String doPostFromUrl(String url, HttpServletRequest request) {
        try {
            if (null != request) {
                StringBuilder urlBuilder = new StringBuilder();
                int index = url.indexOf("?");
                if (index >= 0) {
                    urlBuilder.append(url.substring(0, index));
                } else {
                    urlBuilder.append(url);
                }
                urlBuilder.append("?1=1");
                Enumeration<String> paramNames = request.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String name = paramNames.nextElement();
                    String value = request.getParameter(name);
                    urlBuilder.append("&")
                            .append(name).append("=").append(URLEncoder.encode(value, "UTF-8"));
                }
                url = urlBuilder.toString();
            }
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            if (null != request) {
                setCookieAndHeader(conn, request);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return convertInputStream2String(conn.getInputStream());
            }
        } catch (IOException ex) {
            logger.error("调用HTTP POST接口获取数据出现问题：url=" + url);
        }
        return "";
    }

    /**
     * POST请求, 参数以json格式放到body
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPostFromUrlWithJson(String url, JSONObject params) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(params.toJSONString().getBytes("utf-8"));
            os.flush();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return convertInputStream2String(conn.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("调用HTTP GET接口获取数据出现问题：url=" + url, e);
        }
        return "{}";
    }

    public static String doPostFromUrlWithFormEncoded(String url, Map<String, String> params) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(buildQueryString(params).getBytes("utf-8"));
            os.flush();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return convertInputStream2String(conn.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("调用HTTP GET接口获取数据出现问题：url=" + url, e);
        }
        return null;
    }

    public static String buildQueryString(Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        for (String key : params.keySet()) {
            try {
                ret.append(key).append("=").append(URLEncoder.encode(params.get(key), "utf-8")).append("&");
            } catch (UnsupportedEncodingException ignore) {

            }
        }
        if (ret.length() != 0) {
            ret.deleteCharAt(ret.length() - 1);
        }
        return ret.toString();
    }

    private static void setCookieAndHeader(HttpURLConnection conn, HttpServletRequest request) {
        // 1. 请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String value = request.getHeader(headerName);
                if ("Host".equalsIgnoreCase(headerName)) {
                    conn.setRequestProperty("Host", conn.getURL().getHost());
                } else if ("accept-encoding".equalsIgnoreCase(headerName)) {
                    // 暂时直接返回未经压缩的数据, 否则需要自行处理压缩数据
                    conn.setRequestProperty("accept-encoding", "identity");
                } else if ("POST".equalsIgnoreCase(request.getMethod())
                        && "Content-Length".equalsIgnoreCase(headerName)) {
                    // do nothing
                } else {
                    conn.setRequestProperty(headerName, value);
                }
            }
        }

        // set custom header for php to get the client ip
        conn.setRequestProperty("X-appfang-Forwarded-For", IpUtil.getClientIP(request));

        // 2. coookie
        Cookie[] cookies = request.getCookies();
        StringBuilder cookie = new StringBuilder();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                cookie.append(cookies[i].getName()).append("=").append(cookies[i].getValue()).append(";");
            }
        }
        if (cookie.length() > 0) {
            cookie.deleteCharAt(cookie.length() - 1);
            conn.setRequestProperty("Cookie", cookie.toString());
        }
    }

    private static String convertInputStream2String(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder jsonResult = new StringBuilder();
        String temp = null;
        while ((temp = bufferedReader.readLine()) != null) {
            jsonResult.append(temp);
        }
        return jsonResult.toString();
    }


    public static String sendGet(String url) {
        return sendGet(url, null);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = StringUtils.isEmpty(param) ? url : url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(Constants.CONNECT_TIME_OUT);
            connection.setReadTimeout(Constants.READ_TIME_OUT);

            // 建立实际的连接
            connection.connect();
//            // 获取所有响应头字段
//            Map<String, List<String>> collection.map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : collection.map.keySet()) {
//                System.out.println(key + "--->" + collection.map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url) {
        return sendPost(url, null);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(Constants.CONNECT_TIME_OUT);
            connection.setReadTimeout(Constants.READ_TIME_OUT);

            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
