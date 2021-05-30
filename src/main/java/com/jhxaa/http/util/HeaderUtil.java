package com.jhxaa.http.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HeaderUtil {

    public static final String HEADER_KEY_SET_COOKIE = "Set-Cookie";


    public static Map<String, String> getHeaderMap(Map<String, List<String>> headerFields) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        headerFields.forEach((k, v) -> {
            StringBuilder builder = new StringBuilder();
            for (String valueTiem : v) {
                builder.append(valueTiem).append("; ");
            }
            map.put(k, builder.substring(0, builder.length() - 1));
        });
        return map;
    }

    public static Map<String, String> getCookiesMap(Map<String, List<String>> headerFields) {
        List<String> list = headerFields.get(HEADER_KEY_SET_COOKIE);
        if (ObjectUtil.isListEmpty(list)) {
            return CollectionUtil.newKVStringLinkedHashMap();
        }
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        list.forEach((value) -> {
            String[] toArray = value.split(";");
            String[] mapKV = null;
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
        });
        return map;
    }

}
