package com.jhxaa.http;

import com.jhxaa.http.commons.HttpRequestCoomons;
import com.jhxaa.http.proxy.BaseProxy;
import com.jhxaa.http.proxy.HttpProxy;
import com.jhxaa.http.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Request implements Build<Response> {
    public static int DEFAULT_REQUEST_TIME = 10000;
    Map<String, String> headers;
    Map<String, String> cookies;
    Map<String, String> datas;

    BaseProxy baseProxy;
    Method method;
    String url;
    private InputStream is = null;
    private BufferedReader br = null;
    private OutputStream os = null;
    private HttpURLConnection connection = null;

    public Request() {

    }

    public Request(RequestBuild build) {
        this.headers = build.headers;
        this.cookies = build.cookies;
        this.datas = build.params;
        this.baseProxy = build.baseProxy;
        this.method = build.method;
        this.url = build.url;
    }

    public String getUrl() {
        return url;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Request setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public Request setCookie(String k, String v) {
        if (ObjectUtil.isEmptyMap(cookies)) {
            cookies = CollectionUtil.newKVStringLinkedHashMap();
        }
        cookies.put(k, v);
        return this;
    }

    public Map<String, String> getParams() {
        return datas;
    }

    public Request setParams(Map<String, String> params) {
        this.datas = params;
        return this;

    }

    public Request setParam(String k, String v) {
        if (ObjectUtil.isEmptyMap(datas)) {
            datas = CollectionUtil.newKVStringLinkedHashMap();
        }
        datas.put(k, v);
        return this;
    }

    public Request setHeader(String k, String v) {
        if (ObjectUtil.isEmptyMap(headers)) {
            headers = CollectionUtil.newKVStringLinkedHashMap();
        }
        headers.put(k, v);
        return this;
    }

    public BaseProxy getBaseProxy() {
        return baseProxy;
    }

    public Request setBaseProxy(HttpProxy baseProxy) {
        this.baseProxy = baseProxy;
        return this;

    }

    public Request.Method getMethod() {
        return method;
    }

    public Request setMethod(Request.Method method) {
        this.method = method;
        return this;
    }

    public void openConnection(String url) {
        // ????????????url??????????????????????????????????????????httpURLConnection???
        try {
            if (connection == null) {
                if (ObjectUtil.isEmpty(baseProxy)) {
                    connection = (HttpURLConnection) new URL(url).openConnection();
                } else {
                    connection = (HttpURLConnection) new URL(url).openConnection(baseProxy.createProxy());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????
     */
    public void initHeader() {
        if (ObjectUtil.isNotEmptyMap(headers)) {
            headers.forEach((k, v) -> {
                connection.setRequestProperty(k, v);
            });
        }
        if (ObjectUtil.isEmptyString(headers.get(HttpRequestCoomons.CONTENT_TYPE))) {
            boolean flag = Method.POST.toString().equals(method.toString());
            String cententType = flag ? HttpRequestCoomons.REQUEST_TYPE_POST : HttpRequestCoomons.REQUEST_TYPE_JSON;
            connection.setRequestProperty(HttpRequestCoomons.CONTENT_TYPE, cententType);
        }
        if (ObjectUtil.isNotEmptyMap(cookies)) {
            connection.setRequestProperty("Cookie", CookieUtil.toCookieStr(cookies));
        }
    }

    public void initRequestTimeOut() {
        // ?????????????????????????????????????????????15000??????
        connection.setConnectTimeout(DEFAULT_REQUEST_TIME);//connection.setRequestProperty();
        // ??????????????????????????????????????????60000??????
        connection.setReadTimeout(DEFAULT_REQUEST_TIME);
    }

    public void sendRequest() {
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRequestObject() {
        String GET = Method.GET.toString();
        String POST = Method.POST.toString();
        try {
            if (GET.equals(method.toString())) {
                openConnection(new StringBuilder(url).append(UrlUtil.toParamsStr(datas)).toString());
                initHeader();
                connection.setRequestMethod(GET);
            } else if (POST.equals(method.toString())) {
                openConnection(this.url);
                initHeader();
                connection.setRequestMethod(POST);
                // ??????POST??????????????????????????????
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // ???????????????????????????????????????
                os = connection.getOutputStream();
                // ???????????????????????????????????????/????????????,?????????????????????????????????
                os.write(UrlUtil.toParamsStr(datas).getBytes());
            } else {
                throw new RuntimeException("????????????????????????" + method.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response build() {
        initRequestObject();
        initRequestTimeOut();
        sendRequest();
        Response response = new Response();
        try {
            int responseCode = connection.getResponseCode();
            is = connection.getInputStream();
            response.setStatusCode(responseCode);
            if (responseCode == 200) {
                Map<String, List<String>> headerFields = connection.getHeaderFields();
                byte[] streamToByteArray = IoUtil.inputStreamToByteArray(is, false);
                response.setResultStream(is);
                response.setResultByte(streamToByteArray);
                response.setResultBody(new String(streamToByteArray, StringUtil.ENCODING_UTF_8));
                response.setHeaders(HeaderUtil.getHeaderMap(headerFields));
                response.setCookies(HeaderUtil.getCookiesMap(headerFields));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.colse(is, br, os);
        }
        return response;
    }

    enum Method {
        GET, POST
    }
}
