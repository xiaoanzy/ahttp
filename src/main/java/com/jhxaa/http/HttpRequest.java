package com.jhxaa.http;

import com.jhxaa.http.proxy.HttpProxy;

import java.util.Map;

public class HttpRequest extends Request {


    public HttpRequest() {

    }

    public HttpRequest(HttpRequestBuild build) {
        // super(build);
    }


    @Override
    public Response build() {
        return null;
    }


    class HttpRequestBuild implements Build<HttpRequest> {
        Map<String, String> headers;
        Map<String, String> cookies;
        Map<String, String> datas;
        HttpProxy httpProxy;
        Method method;

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
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

        public Map<String, String> getDatas() {
            return datas;
        }

        public void setDatas(Map<String, String> datas) {
            this.datas = datas;
        }

        public HttpProxy getProxyIp() {
            return httpProxy;
        }

        public void setProxyIp(HttpProxy httpProxy) {
            this.httpProxy = httpProxy;
        }


        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
