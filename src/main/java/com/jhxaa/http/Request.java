package com.jhxaa.http;

import com.jhxaa.http.commons.HttpRequestCoomons;
import com.jhxaa.http.proxy.BaseProxy;
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
    static Map<String, String> headers;
    static Map<String, String> cookies;
    static Map<String, String> datas;

    static {

    }

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

    public void openConnection(String url) {
        // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
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
     * 构造请求头
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
    }

    public void initRequestTimeOut() {
        // 设置连接主机服务器的超时时间：15000毫秒
        connection.setConnectTimeout(DEFAULT_REQUEST_TIME);//connection.setRequestProperty();
        // 设置读取远程返回的数据时间：60000毫秒
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
                // 发送POST请求必须设置如下两行
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 通过连接对象获取一个输出流
                os = connection.getOutputStream();
                // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
                os.write(UrlUtil.toParamsStr(datas).getBytes());
            } else {
                throw new RuntimeException("无效的请求方式：" + method.toString());
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
