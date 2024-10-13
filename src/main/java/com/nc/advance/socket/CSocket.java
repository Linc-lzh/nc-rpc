package com.nc.advance.socket;

import java.net.Socket;

public class CSocket {
    //socket对象
    private Socket socket;
    //是否正被使用
    private boolean inUse = false;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
