package com.mada.commons.util.http;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by madali on 2018/12/26 17:51
 */
public class IpUtil {

    // 通常不会更换网卡
    public static final String ETH0_IP = getLocalIPETH0();

    public static final String LOCAL_IP = getLocalAddress().getHostAddress();

    /**
     * Get the real remote client's IP
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = null;
        if (null != HttpHeaderUtil.getHeader(request, "HTTP_X_FORWARDED_FOR", "x-forwarded-for")
                && !"127.0.0.1".equals(HttpHeaderUtil.getHeader(request, "HTTP_X_FORWARDED_FOR", "x-forwarded-for"))) {
            ip = HttpHeaderUtil.getHeader(request, "HTTP_X_FORWARDED_FOR", "x-forwarded-for");
            ip = StringUtils.split(ip, ",")[0];
        } else if (null != HttpHeaderUtil.getHeader(request, "HTTP_X_REAL_IP", "x-real-ip")) {
            ip = HttpHeaderUtil.getHeader(request, "HTTP_X_REAL_IP", "x-real-ip");
        } else if (null != HttpHeaderUtil.getHeader(request, "HTTP_CLIENTIP", "clientip")) {
            ip = HttpHeaderUtil.getHeader(request, "HTTP_CLIENT_IP", "clientip");
        } else if (null != request.getRemoteAddr()) {
            ip = request.getRemoteAddr();
        }

        int pos = ip.indexOf(',');
        if (pos > 0) {
            ip = ip.substring(0, pos);
        }
        pos = ip.indexOf(':');
        if (pos > 0) {
            ip = ip.substring(0, pos);
        }
        return ip.trim();
    }

    public static String getLocalIPETH0() {
        String ip = "127.0.0.1";
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("window") < 0) {
                NetworkInterface ni = NetworkInterface.getByName("eth0");
                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {

        }
        return ip;
    }

    public static InetAddress getLocalAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }

            // 如果没有发现 non-loopback地址.只能用最次选的方案
            return InetAddress.getLocalHost();
        } catch (Exception e) {

        }

        return null;
    }

}
