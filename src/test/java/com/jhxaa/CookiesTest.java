package com.jhxaa;

import com.jhxaa.http.util.CookieUtil;
import org.junit.Test;

import java.util.HashMap;

public class CookiesTest {

    @Test
    public void cookies() {
        HashMap<String, String> map = new HashMap<>();
        map.put("dasdsa", "213");
        map.put("dqwq", "aaa");

        System.out.println(CookieUtil.toCookieStr(map));
    }

}
