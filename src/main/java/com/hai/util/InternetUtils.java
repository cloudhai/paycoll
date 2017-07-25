package com.hai.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by cloud on 2017/3/22.
 */
public class InternetUtils {

    public static String getLocalIp(){
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()){
                NetworkInterface ni = interfaces.nextElement();
                System.out.println("disp name:"+ni.getDisplayName()+"\nname:"+ni.getName()+"\nip:\n");
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while(ips.hasMoreElements()){
                    System.out.println(ips.nextElement().getHostAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getRemoteIP(HttpServletRequest request){
        String ipString ="";
        try{
            if (request == null) {
                throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
            }
            ipString = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
                ipString = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
                ipString = request.getHeader("WL-Proxy-Client-IP");
            }
            if(StringUtils.isBlank(ipString) || "X-Real-IP".equalsIgnoreCase(ipString)){
                ipString = request.getHeader("X-Real-IP");
            }
            if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
                ipString = request.getRemoteAddr();
            }
            // 多个路由时，取第一个非unknown的ip
            final String[] arr = ipString.split(",");
            for (final String str : arr) {
                if (!"unknown".equalsIgnoreCase(str)) {
                    ipString = str;
                    break;
                }
            }
        }catch(Exception ex){

            ex.toString();
        }

        return ipString;
    }

    public static void main(String[] args) {
        getLocalIp();
    }
}
