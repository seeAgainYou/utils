package com.xxty.utils.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IpUtils
 *
 * @author Sunnly
 * @since 2019/7/24 16:26
 */
public class IpUtils {

    private static final String UNKNOWN = "unknown";
    private static final String X_FORWARDED_FOR = "x-forwarded-for";
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static final String LOCAL_IPV4 = "127.0.0.1";
    private static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";
    private static final String SPLIT = ",";
    private static final int IP_LENGTH = 15;
    private static final int MAX_LINE = 15;


    private IpUtils() {
    }

    /**
     * 获取当前网络ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader(IpUtils.X_FORWARDED_FOR);
        if (ipAddress == null || ipAddress.length() == 0 || IpUtils.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IpUtils.PROXY_CLIENT_IP);
        }
        if (ipAddress == null || ipAddress.length() == 0 || IpUtils.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IpUtils.WL_PROXY_CLIENT_IP);
        }
        if (ipAddress == null || ipAddress.length() == 0 || IpUtils.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (StringUtils.equals(ipAddress, IpUtils.LOCAL_IPV4) || StringUtils.equals(ipAddress, IpUtils.LOCAL_IPV6)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > IpUtils.IP_LENGTH) {
            if (ipAddress.indexOf(IpUtils.SPLIT) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(IpUtils.SPLIT));
            }
        }
        return ipAddress;
    }

    /**
     * 获得MAC地址
     *
     * @param ip
     * @return
     */
    public static String getMacAddress(String ip) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < IpUtils.MAX_LINE; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }
}