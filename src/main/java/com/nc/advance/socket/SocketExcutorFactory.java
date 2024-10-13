package com.nc.advance.socket;

public class SocketExcutorFactory {
    private SocketPool pool = null;

    public SocketExcutorFactory(String host, int port, boolean daemonFlag) {
        pool = new SocketPool(host, port);
        if (daemonFlag) {
            //守护线程
            Thread daemonThread = new Thread(new DaemonThread(pool));
            daemonThread.start();
        }
    }

    public SocketExcutorFactory(String host, int port) {
        this(host, port, true);
    }

    /**
     * 实例化executor 并从池中拿出一个socket注进去
     *
     * @return
     * @Description: TODO
     */
    public HTMLSocketExecutor getHttpExecutor() {
        HTMLSocketExecutor executor = null;
        executor = new HTMLSocketExecutor(pool.getSocket());
        return executor;
    }
}
