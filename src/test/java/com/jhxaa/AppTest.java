package com.jhxaa;

import com.alibaba.fastjson.JSON;
import com.jhxaa.http.RequestBuild;
import com.jhxaa.http.Response;
import com.jhxaa.http.util.StringUtil;
import org.junit.Test;

import java.util.LinkedHashMap;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        long s = System.currentTimeMillis();
        Response build = RequestBuild.createBuild().setUrl("http://www.jhxaa.com/api/findLinkInfoByOneMenuId").build();
        System.out.println(build.getCookies());
//        System.out.println(JSON.toJSONString(build));
        System.out.println(build.getResultStream());
        System.out.println(System.currentTimeMillis() - s);
    }


    @Test
    public void formatCookieValueTest() {
        String value = "X_CACHE_KEY=12a530c45352fb5f4e67a2f2c4f57d6d; path=/; Expires=Fri, 31-Dec-9999 23:59:59 GMT;";
        String[] toArray = value.split(";");
        String[] mapKV = null;
        LinkedHashMap<String, String> map = new LinkedHashMap<>(toArray.length);
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
        System.out.println(JSON.toJSONString(map));
    }
}
