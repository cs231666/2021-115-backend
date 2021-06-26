package com.daoyun.demo.util;

public class LoginUtil {

    static String isEmail = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    static String isPhone = "^[1][34578]\\d{9}$";
    public static String check(String str){
        if (str.matches(isEmail)){
            return "email";
        }
        if (str.matches(isPhone)){
            return "phone";
        }

        return "";
    }
}
