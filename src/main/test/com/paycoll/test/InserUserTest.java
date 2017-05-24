package com.paycoll.test;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.String;

/**
 * Created by cloud on 2017/5/10.
 */
public class InserUserTest {

    public static final String url = "jdbc:mysql://kpdbtest.mysql.rds.aliyuncs.com:3306/prod_db_kp?characterEncoding=UTF-8";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "prod_db_kp";
    public static final String password = "prodkaipai";
    public static final String csvPath = "d:\\python\\jwyh.csv";
    public static final String csvPath2 = "d:\\python\\dlst.csv";

    public static final String intsql = "insert into kp_users(`user_name`,`nick_name`,`mobile`,`user_type`," +
            "`auth_flg`,`kp_no`,`user_state`,`source`,`user_pwd`,`reg_tm`) values({0},{1},{2},''U'',''0'',{3},''1'',''APP'','''',now());";

    public static final String stocksql ="insert into `xf_user_stock`(`stock_code`, `user_id`, `trade_num`, `cost_amt_ttl`,`create_tm`) VALUES(''MV0010'',{0},{1},{2},now());";
    public Connection conn = null;
    public PreparedStatement pst = null;

    public static final double price = 0.89;

    private static Set<String> set = new HashSet<>();

    public void init() {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String  execute (String mobile) throws SQLException {
        String sql = "select user_id from kp_users where user_name = ?";
        pst =  conn.prepareStatement(sql);//准备执行语句
        pst.setString(1,mobile);
        ResultSet rst = pst.executeQuery();
        if(rst != null){
            while(rst.next()){
                return rst.getString(1);
            }
        }
        return null;
    }


    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<String> createSql() throws SQLException {
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        try {
            CSVParser parser = CSVParser.parse(new File(csvPath2), Charset.forName("gbk"),CSVFormat.DEFAULT);
            for(CSVRecord record:parser){
                String userId = execute(record.get(1));
                String nums = record.get(2);
                int num = (int) (Math.ceil(Double.parseDouble(nums)/100)*100);
                String cost = new BigDecimal(num*price).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                String sql = MessageFormat.format(stocksql,userId,num+"",cost);
                System.out.println(sql);

//                if(!execute(record.get(1))){
//                    String mobile = "\'"+record.get(1)+"\'";
//                    String name = "\'"+record.get(0)+"\'";
//                    String kpno = "\'"+genKPUserNo(record.get(1))+"\'";
//                    String sql = MessageFormat.format(intsql,mobile,name,mobile,kpno);
//                    System.out.println(sql);
//                    set.add(record.get(1));
//                }else{
////                    System.out.println("用户存在"+record.get(1));
//                }

            }
//            System.out.println("###################");
//            CSVParser dlParser = CSVParser.parse(new File(csvPath2), Charset.forName("gbk"),CSVFormat.DEFAULT);
//            for(CSVRecord record:dlParser){
//                if(!execute(record.get(1))) {
//                    if (!set.contains(record.get(1))) {
//                        String mobile = "\'" + record.get(1) + "\'";
//                        String name = "\'" + record.get(0) + "\'";
//                        String kpno = "\'" + genKPUserNo(record.get(1)) + "\'";
//                        String sql = MessageFormat.format(intsql, mobile, name, mobile, kpno);
//                        System.out.println(sql);
//                    } else {
////                        System.out.println(record.get(1) + "  is in ");
//                    }
//                }else{
////                    System.out.println("用户存在"+record.get(1));
//                }
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static String genKPUserNo(String tokenChr){
        StringBuffer odrBuffer = new StringBuffer();
        odrBuffer.append("KP");
        int hashCodeIP = tokenChr.hashCode();
        StringBuffer hashCodech = new StringBuffer(""+Math.abs(hashCodeIP));
        String hashIp = hashCodech.toString();
        hashCodech.append(getTenRandom());
        if(hashCodech.length()>15){
            hashIp = hashCodech.substring(0, 14);
        }
        odrBuffer.append(getSystemTimestamp());
        odrBuffer.append(genFivteenZero(Long.valueOf(hashIp)));
        odrBuffer.append(geOneRandom());
        odrBuffer.append(geOneRandom());
        return odrBuffer.toString();
    }

    private static String getSystemTimestamp() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String systemdate = sdf.format(Calendar.getInstance().getTime());
        return systemdate.substring(0, systemdate.length());
    }
    private static String genFivteenZero(long code){
        java.text.DecimalFormat zeroFmt = new java.text.DecimalFormat("00000000000000");
        String fmtZero  = zeroFmt.format(code);
        return fmtZero;
    }
    private static String geOneRandom(){
        long random1 = (long) (Math.random() * 9 + 1);
        String random = String.valueOf(random1);
        return random;
    }
    private static String getTenRandom(){
        long random1 = (long) (Math.random() * 900000000 + 100000000);
        String random = String.valueOf(random1);
        return random;
    }

    public static void main(String[] args) throws SQLException {
        InserUserTest inserUserTest = new InserUserTest();
        inserUserTest.init();
        inserUserTest.createSql();
        inserUserTest.close();
//        System.out.println(genKPUserNo("sdfs"));
    }
}
