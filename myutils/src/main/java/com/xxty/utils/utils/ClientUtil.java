package com.xxty.utils.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 */
public class ClientUtil {
	/**
	 * 获取客户端真实ip
	 * @param request
	 * @return
	 * @author
	 */
	public static String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		String mateStr = "unknown";
		if (ip==null||ip.length()==0||mateStr.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip==null||ip.length()==0||mateStr.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip==null||ip.length()==0||mateStr.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
