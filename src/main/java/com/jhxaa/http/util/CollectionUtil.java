package com.jhxaa.http.util;

import java.util.LinkedHashMap;

public class CollectionUtil {

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Class<K> kClass, Class<V> vClass) {
        return new LinkedHashMap<K, V>();
    }

    public static LinkedHashMap<String, String> newKVStringLinkedHashMap() {
        return CollectionUtil.newLinkedHashMap(String.class, String.class);
    }
}
