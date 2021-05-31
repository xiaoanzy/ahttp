package com.jhxaa.http.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CookieUtil {

    public static Map<String, String> toCookiesMap(String cookie) {
        String[] toArray = cookie.split(";");
        String[] mapKV = null;
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (String item : toArray) {
            mapKV = item.split("=");
            if (mapKV.length == 1) {
                map.put(mapKV[0], "");
            } else if (mapKV.length == 2) {
                map.put(mapKV[0], StringUtil.formatEmptyStr(mapKV[1]));
            } else {
                throw new RuntimeException("cookie format error.");
            }
        }
        return map;
    }

    public static String toCookieStr(Map<String, String> cookies) {
        StringBuilder builder = new StringBuilder();
        cookies.forEach((k, v) -> {
            builder.append(k).append("=").append(StringUtil.formatEmptyStr(v)).append(";").append(" ");
        });
        return builder.toString();
    }
}
