package com.jhxaa.http.proxy;

import java.net.Proxy;

public class HttpProxy extends BaseProxy {


    public HttpProxy() {
        super();
    }

    public HttpProxy(String ip, int port) {
        super(ip, port, Proxy.Type.HTTP);
    }


    @Override
    public Proxy createProxy() {
        return super.createProxy();
    }
}
