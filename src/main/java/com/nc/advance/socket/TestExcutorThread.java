package com.nc.advance.socket;

public class TestExcutorThread implements Runnable {
    HTMLSocketExecutor excutor = null;
    private static SocketExcutorFactory factory = new SocketExcutorFactory("www.baidu.com", 80);

    public TestExcutorThread() {
        excutor = factory.getHttpExecutor();
    }

    public void run() {
        excutor.setRequest("/");
        String res = excutor.getResponse();
        if (res.contains("302 Found")) {
            System.out.println(Thread.currentThread().getName() + " success");
        } else {
            System.out.println(Thread.currentThread().getName() + " fail");
        }

    }

    public static void main(String[] args) {
        Thread thread = null;
        for (int i = 0; i < 10; i++) {
            thread = new Thread(new TestExcutorThread());
            thread.start();
        }
    }
}
