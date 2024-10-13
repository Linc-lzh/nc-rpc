package com.nc.advance.socket;

public class TestPool {
    public static void main(String[] args) {
        String host = "www.cnblogs.com";
        int port = 80;
        SocketExcutorFactory factory = new SocketExcutorFactory(host, port, false);
        HTMLSocketExecutor executor = factory.getHttpExecutor();
        executor.setRequest("/zhangweizhong/p/5772330.html");
        String res = executor.getResponse();
        System.out.println(res);
    }
}
