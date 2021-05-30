package com.jhxaa.http;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {


    int statusCode;

    Map<String, String> headers;

    Map<String, String> cookies;

    byte[] resultByte;

    String resultBody;

    InputStream resultStream;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public byte[] getResultByte() {
        return resultByte;
    }

    public void setResultByte(byte[] resultByte) {
        this.resultByte = resultByte;
    }

    public String getResultBody() {
        return resultBody;
    }

    public void setResultBody(String resultBody) {
        this.resultBody = resultBody;
    }

    public InputStream getResultStream() {
        return resultStream;
    }

    public void setResultStream(InputStream resultStream) {
        this.resultStream = resultStream;
    }
}
