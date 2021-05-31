package com.jhxaa.http;

import com.jhxaa.http.commons.HttpRequestCoomons;
import com.jhxaa.http.util.CookieUtil;

public class HttpRequest extends Request {

    public HttpRequest() {
        super();
    }

    public HttpRequest(RequestBuild build) {
        super(build);
    }

    public static Request get(String url) {
        return RequestBuild.createBuild().setUrl(url).setMethod(Method.GET).getRequest();
    }

    public static Request post(String url) {
        return RequestBuild.createBuild().setUrl(url).setMethod(Method.POST).getRequest();
    }

    public Request setCookieStr(String c) {
        CookieUtil.toCookiesMap(c).forEach((k, v) -> {
            super.setCookie(k, v);
        });
        return this;
    }

    public Request setUserAgent(String userAgentStr) {
        super.setHeader(HttpRequestCoomons.REQUEST_UER_AGENT, userAgentStr);
        return this;
    }

    @Override
    public Response build() {
        return super.build();
    }
}
