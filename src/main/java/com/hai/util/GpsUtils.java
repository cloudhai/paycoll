package com.hai.util;

/**
 * Created by cloud on 2017/5/4.
 */
public class GpsUtils {

    private final static double pi = 3.1515926;

    public static double getDistance(double lat_a,double lng_a,double lat_b,double lng_b){
        double pk = (double)(180 / 3.1415926);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }

    public static void main(String[] args) {
        System.out.println(getDistance(0,0,2,0));
    }

    public static double getMeridinal(double lat){
//        double l = 7915.7044*Math.tan(pi/4+lat/2);
        return 0.0;
    }
}
