package com.daoyun.demo.util;

public class DistanceUtil {
    private static double EARTH_RADIUS = 6378.137;
    private static double rad(double d){
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离
     * @param lat1 经度1
     * @param lng1 维度1
     * @param lat2 精度2
     * @param lng2 维度2
     * @return 两点之间的距离（单位：千米）
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2){
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    public static void main(String[] args) {
        System.out.println(DistanceUtil.GetDistance(22.3193039, 114.16936109999999, 26.065101093910325, 119.19664393236677));
    }
}
