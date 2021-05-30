package com.jhxaa.http;

import com.jhxaa.http.proxy.HttpProxy;
import com.jhxaa.http.util.CollectionUtil;
import com.jhxaa.http.util.ObjectUtil;

import java.util.Map;

public class RequestBuild implements Build<Response> {


    Map<String, String> headers;
    Map<String, String> cookies;
    Map<String, String> params;
    HttpProxy baseProxy;
    Request.Method method;
    String url;

    public static RequestBuild createBuild() {
        return new RequestBuild();
    }

    void checkBuildParam() {
        if (ObjectUtil.isEmptyString(url)) {
            throw new RequestException("request url not null.");
        }
        if (ObjectUtil.isEmpty(method)) {
            method = Request.Method.GET;
        }
        if (ObjectUtil.isEmptyMap(headers)) {
            headers = CollectionUtil.newKVStringLinkedHashMap();
        }
        if (ObjectUtil.isEmptyMap(cookies)) {
            cookies = CollectionUtil.newKVStringLinkedHashMap();
        }
        if (ObjectUtil.isEmptyMap(params)) {
            params = CollectionUtil.newKVStringLinkedHashMap();
        }
    }

    public String getUrl() {
        return url;
    }

    public RequestBuild setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestBuild setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public RequestBuild setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public RequestBuild setParams(Map<String, String> params) {
        this.params = params;
        return this;

    }

    public RequestBuild setParam(String k, String v) {
        if (ObjectUtil.isEmptyMap(params)) {
            params = CollectionUtil.newKVStringLinkedHashMap();
        }
        params.put(k, v);
        return this;
    }

    public RequestBuild setHeader(String k, String v) {
        if (ObjectUtil.isEmptyMap(headers)) {
            headers = CollectionUtil.newKVStringLinkedHashMap();
        }
        headers.put(k, v);
        return this;
    }

    public HttpProxy getBaseProxy() {
        return baseProxy;
    }

    public RequestBuild setBaseProxy(HttpProxy baseProxy) {
        this.baseProxy = baseProxy;
        return this;

    }

    public Request.Method getMethod() {
        return method;

    }

    public RequestBuild setMethod(Request.Method method) {
        this.method = method;
        return this;
    }

    @Override
    public Response build() {
        checkBuildParam();
        Request request = new Request(this);
        return request.build();
    }
}
