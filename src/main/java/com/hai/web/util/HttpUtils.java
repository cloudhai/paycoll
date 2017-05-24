package com.hai.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Created by cloud on 2017/2/24.
 */
public class HttpUtils {
    private static final String CHARSET = "UTF-8";
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";

/**********************http 请求********************************/
    public static String doPost(String postUrl, String data) throws Exception {
        URL orderUrl = new URL(postUrl);
        HttpURLConnection conn = (HttpURLConnection) orderUrl.openConnection();
        conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
        conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
        conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
        conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
        conn.setUseCaches(false); // Post 请求不能使用缓存
        // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
//        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Type","application/json");
//        conn.setRequestProperty("Content-Type","text/xml");
        conn.setRequestMethod(HTTP_POST);// 设定请求的方法为"POST"，默认是GET
        conn.setRequestProperty("Content-Length", data.length() + "");
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(conn.getOutputStream(), CHARSET);
            out.write(data.toString());
            out.flush();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            out.close();
        }
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }
        StringBuffer strBuf = new StringBuffer();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), CHARSET));
            String line = "";
            while ((line = in.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            in.close();

        }
        String result = strBuf.toString().trim();
        return result;
    }



    public  static String doGet(String url){
        StringBuffer strBuf = new StringBuffer();
        BufferedReader in = null;
        try {
            URL orderUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) orderUrl.openConnection();
            conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
            conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
            // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            conn.setRequestMethod(HTTP_GET);// 设定请求的方法为"POST"，默认是GET
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), CHARSET));
            String line = "";
            while ((line = in.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = strBuf.toString().trim();
        return result;
    }

    public String doGet(String url,Map<String,String> data){
        if(data != null){
            StringBuffer sb = new StringBuffer(data.size());
            for(Map.Entry<String,String> entry:data.entrySet()){
                sb.append(entry.getKey()+"="+entry.getValue()+"&");
            }
            String param = sb.substring(0,sb.length()-1);
            if(url.contains("?")){
                return doGet(url+"&"+param);
            }else{
                return doGet(url+"?"+param);
            }
        }
        return doGet(url);
    }
/*********************************https 请求*******************************************/




    /**
     * Send GET request
     */
    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), HTTP_GET, headers);
            conn.connect();
            return readResponseString(conn);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null);
    }

    public static String get(String url) {
        return get(url, null, null);
    }


    /**
     * Send POST request
     */
    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), HTTP_POST, headers);
            conn.connect();

            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes(CHARSET));
            out.flush();
            out.close();

            return readResponseString(conn);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, null);
    }

    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    public static String post(String url, String data) {
        return post(url, null, data, null);
    }




    /**
     * https 域名校验
     */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https 证书管理
     */
    private static class TrustAnyTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = {new TrustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new TrustAnyHostnameVerifier();

    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws Exception {
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection)conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(19000);
        conn.setReadTimeout(19000);
        conn.setUseCaches(false); // Post 请求不能使用缓存
        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (headers != null && !headers.isEmpty())
            for (Map.Entry<String, String> entry : headers.entrySet())
                conn.setRequestProperty(entry.getKey(), entry.getValue());

        return conn;
    }

    private static String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Build queryString of the url
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty())
            return url;

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf("?") == -1) {
            isFirst = true;
            sb.append("?");
        }
        else {
            isFirst = false;
        }

        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) isFirst = false;
            else sb.append("&");

            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isEmpty(value))
                try {value = URLEncoder.encode(value, CHARSET);} catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }




















    public static void main(String[] args) {
        String url = "http://192.168.1.10:8080/paycoll/api/wx/message?signature=22936d9ebe4c390781a7c17d9063810a94a69ea2&timestamp=135247856&nonce=124578963";
        String data = "<xml><ToUserName><![CDATA[gh_cd77095ad934]]></ToUserName>\n" +
                "<FromUserName><![CDATA[omm-iv0HbOZliIKyiEg9tmKTMwk8]]></FromUserName>\n" +
                "<CreateTime>1488336693</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[你好]]></Content>\n" +
                "<MsgId>6392357423267959517</MsgId>\n" +
                "</xml>";
        try {
            String res = doPost(url,data);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
