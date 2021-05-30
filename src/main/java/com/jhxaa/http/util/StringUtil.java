package com.jhxaa.http.util;

public class StringUtil {

    public static final String ENCODING_UTF_8 = "utf-8";


    public static String formatEmptyStr(String t) {
        if (ObjectUtil.isEmptyString(t)) {
            return "";
        }
        return t;
    }

//    public static String formatEmptyString(String text) {
//        if (ObjectUtil.isEmptyString(text)) {
//            return "";
//        }
//        return text;
//    }

}
