package com.nc.advance.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SocketPool {
    //host
    private String host;
    //port
    private int port;
    //初始化socket数
    private int initSize = 5;
    //最大socket数
    private int maxSize = 5;

    private List<CSocket> socketContainer = new ArrayList<>(initSize);

    public SocketPool(String host, int port) {
        this.host = host;
        this.port = port;
        initSocketPool();
    }

    //给socket容器增加成员 一次增加initSize个成员
    private List<CSocket> initSocketPool() {
        List<CSocket> items = new ArrayList<>(initSize);
        CSocket item = null;
        if (initSize > 0 && maxSize > socketContainer.size()) {
            int count = maxSize - socketContainer.size();
            for (int i = 0; i < count; i++) {
                Socket socket = null;
                try {
                    socket = new Socket(host, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                item = new CSocket();
                item.setSocket(socket);
                items.add(item);
            }
        }

        if (items.size() > 0) {
            socketContainer.addAll(items);
        } else {
            try {
                throw new Exception("扩大池容量失败");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    //获取池中空闲的socket 已做线程安全处理
    public CSocket getSocket() {
        CSocket item = null;
        //同步块获取对象
        synchronized (this) {
            for (int i = 0; i < socketContainer.size(); i++) {
                CSocket itemInPool = socketContainer.get(i);
                boolean inUse = itemInPool.isInUse();
                if (inUse == false) {
                    itemInPool.setInUse(true);
                    item = itemInPool;
                    System.out.println("成功获取对象,在池中的位置为：" + ++i);
                    break;
                }
            }
            //pool中没有空余
            if (item == null) {
                if (socketContainer.size() < maxSize) {
                    //扩大池容量
                    List<CSocket> newPoolPart = initSocketPool();
                    //从新扩大的部分拿出一个来用
                    Optional<CSocket> first = newPoolPart.stream().filter(x -> !x.isInUse()).findFirst();
                    if(first.isPresent()){
                        item = first.get();
                        item.setInUse(true);
                    }

                    System.out.println("成功扩大池容量,当前size为:" + socketContainer.size());
                }
            }
        }
        //如果超过最大容量 等待 递归
        if (item == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            item = getSocket();
        }
        return item;

    }

    public int getInitSize() {
        return initSize;
    }

    public void setInitSize(int initSize) {
        this.initSize = initSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<CSocket> getSocketContainer() {
        return socketContainer;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
