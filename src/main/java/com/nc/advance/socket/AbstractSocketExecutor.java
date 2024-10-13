package com.nc.advance.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AbstractSocketExecutor {
    protected CSocket socketMember = null;
    protected Socket socket = null;
    protected String host;
    protected int port;

    public AbstractSocketExecutor(CSocket socketMember) {
        this.socketMember = socketMember;
        if (socketMember != null) {
            this.socket = socketMember.getSocket();
        }

        if(socket != null) {
            host = socket.getInetAddress().getHostName();
            port = socket.getPort();
        }
    }

    /**
     * 发送请求
     *
     * @param request
     * @param charset
     * @Description: TODO
     */
    protected void request(String request, String charset) {
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
            out.write(request.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回响应stream
     *
     * @return
     * @Description: TODO
     */
    protected InputStream getResStream() {
        InputStream in = null;
        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    /**
     * 设置状态为未使用
     *
     * @Description: TODO
     */
    public void back() {
        //不能关闭流，否则socket会被关闭
/*        try {
            socket.getOutputStream().close();
            socket.getInputStream().close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
        socketMember.setInUse(false);
    }
}
