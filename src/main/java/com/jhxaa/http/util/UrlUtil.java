package com.jhxaa.http.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class UrlUtil {


    public static String toParamsStr(Map<String, String> params) {
        if (ObjectUtil.isEmptyMap(params)) {
            return "";
        }
        StringBuilder builder = new StringBuilder("?");
        params.forEach((k, v) -> {
            builder.append(k).append("=").append(StringUtil.formatEmptyStr(v)).append("&");
        });
        return builder.substring(0, builder.length() - 1);
    }

    public static void main(String[] args) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("name", "test");
        linkedHashMap.put("content", null);
        linkedHashMap.put("age", "20");
        String toParamsStr = UrlUtil.toParamsStr(linkedHashMap);
        System.out.println(toParamsStr.substring(1));
    }
}
