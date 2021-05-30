package com.jhxaa.http.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class BaseProxy {

    String ip;
    int port;
    Proxy.Type type;

    public BaseProxy() {
    }

    public BaseProxy(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public BaseProxy(String ip, int port, Proxy.Type type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
    }

    public Proxy.Type getType() {
        return type;
    }

    public void setType(Proxy.Type type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Proxy createProxy() {
        if (getType() == null) {
            throw new RuntimeException("not set proxy type.");
        }
        return new Proxy(getType(), new InetSocketAddress(getIp(),
                getPort()));
    }

}
