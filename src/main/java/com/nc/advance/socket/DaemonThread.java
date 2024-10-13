package com.nc.advance.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class DaemonThread implements Runnable{
    private SocketPool pool;
    public DaemonThread(SocketPool pool){
        this.pool=pool;
    }
    public void run() {
        List<CSocket> container=pool.getSocketContainer();
        while(true){
            //10秒检查一次
            try {
                Thread.sleep(10000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            //遍历检查是否连接
            for(int i=0;i<container.size();i++){
                CSocket member=container.get(i);
                Socket socket=member.getSocket();
                //给此socket加锁
                synchronized (socket) {
                    if(!member.isInUse()){

                        if(socket.isConnected()){
                            //如果连接发送心跳包
                            KeepAliveExcutor excutor=new KeepAliveExcutor(member);
                            excutor.request();

                        }else{
                            //如果失败重新建立socket
                            try {
                                socket=new Socket(pool.getHost(), pool.getPort());
                                member.setSocket(socket);
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(socket.getLocalPort()+" 断线重连");
                        }
                    }
                }
            }
        }

    }
}
