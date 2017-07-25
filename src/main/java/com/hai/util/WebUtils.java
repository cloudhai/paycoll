package com.hai.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.SystemMetaObject;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by cloud on 2017/7/16.
 */
public class WebUtils {

    public static String getLocalIp(String prefix){
        Enumeration<NetworkInterface> interfaces;
        try{
            interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()){
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while(addresses.hasMoreElements()){
                    InetAddress address = addresses.nextElement();
                    if(address.isSiteLocalAddress()){
                        if(StringUtils.isNotEmpty(prefix)){
                            if(address.getHostAddress().startsWith(prefix)){
                                return address.getHostAddress();
                            }
                        }else{
                            return address.getHostAddress();
                        }

                    }
                }
            }
        }catch (Exception e){

        }
        return null;
    }

    public static String getLocalIp(){
        return getLocalIp(null);
    }


    /* 获取服务端口号
     * @return 端口号
     * @throws ReflectionException
     * @throws MBeanException
     * @throws InstanceNotFoundException
     * @throws AttributeNotFoundException
     */
    private static String getServerPort(boolean secure) throws Exception {
        MBeanServer mBeanServer = null;
        if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
            mBeanServer = (MBeanServer)MBeanServerFactory.findMBeanServer(null).get(0);
        }

        if (mBeanServer == null) {
//            logger.debug("调用findMBeanServer查询到的结果为null");
            return "";
        }

        Set<ObjectName> names = null;
        try {
            names = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
        } catch (Exception e) {
            return "";
        }
        Iterator<ObjectName> it = names.iterator();
        ObjectName oname = null;
        while (it.hasNext()) {
            oname = (ObjectName)it.next();
            String protocol = (String)mBeanServer.getAttribute(oname, "protocol");
            String scheme = (String)mBeanServer.getAttribute(oname, "scheme");
            Boolean secureValue = (Boolean)mBeanServer.getAttribute(oname, "secure");
            Boolean SSLEnabled = (Boolean)mBeanServer.getAttribute(oname, "SSLEnabled");
            if (SSLEnabled != null && SSLEnabled) {// tomcat6开始用SSLEnabled
                secureValue = true;// SSLEnabled=true但secure未配置的情况
                scheme = "https";
            }
            if (protocol != null && ("HTTP/1.1".equals(protocol) || protocol.contains("http"))) {
                if (secure && "https".equals(scheme) && secureValue) {
                    return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
                } else if (!secure && !"https".equals(scheme) && !secureValue) {
                    return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
                }
            }
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getLocalIp("192.168.1"));
    }
}
