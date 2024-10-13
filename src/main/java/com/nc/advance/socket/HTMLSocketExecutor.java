package com.nc.advance.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HTMLSocketExecutor extends AbstractSocketExecutor {
    private String charset="UTF-8";
    public HTMLSocketExecutor(CSocket socketMember) {
        super(socketMember);
    }

    public void setRequest(String path){
        StringBuilder requestStr=new StringBuilder();
        requestStr.append("GET ");
        requestStr.append(path);
        requestStr.append(" HTTP/1.1\r\n");
        requestStr.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36\r\n");
        requestStr.append("Host: ");
        requestStr.append(super.host);
        if(port!=80){
            requestStr.append(":");
            requestStr.append(super.port);
        }
        requestStr.append("\r\n");
        requestStr.append("\r\n");
        super.request(requestStr.toString(), charset);
    }

    public String getResponse(){
        StringBuilder responseResult=new StringBuilder();
        BufferedReader socketReader=null;
        try {
            socketReader=new BufferedReader(new InputStreamReader(super.getResStream(), charset)) ;
            String line=null;
            char[] body=null;
            int contentLength=-1;
            boolean isHeadEnd=false;
            while((line=socketReader.readLine())!=null){
                //System.out.println(line);
                responseResult.append(line);
                responseResult.append("\r\n");
                //检查请求头是否结束
                if(!isHeadEnd){
                    isHeadEnd=isBlankLine(line);
                    if(isHeadEnd){
                        //立即判断是否为请求头结束 如果是且有content-length 那么立即一次读取body 结束循环
                        if(contentLength!=-1){
                            if(contentLength==0){
                                break;
                            }
                            //如果content-length>0 那么一次性读出响应体
                            body=new char[contentLength];
                            int bodyReadEndFlag=socketReader.read(body);
                            responseResult.append(body);
                            if(bodyReadEndFlag==-1){
                                break;
                            }

                        }
                    }
                    if(contentLength==-1){
                        //没有赋值 检查content-length并赋值
                        contentLength=getContentLength(line);
                    }
                }else{
                    //已经读过了head结束行
                    if(isHTMLEnd(line)){
                        //如果碰到</html>
                        break;
                    }
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally{
            super.back();
        }

        return responseResult.toString();

    }

    private int getContentLength(String line){
        int result=-1;
        if(line.contains("Content-Length")){
            int splitIndex=line.indexOf(":");
            String length=line.substring(splitIndex+1,line.length());
            length=length.trim();
            result=Integer.valueOf(length);
        }
        return result;
    }

    private boolean isBlankLine(String line){
        boolean result=false;
        if("".equals(line)){
            result=true;
        }
        return result;
    }

    private boolean isHTMLEnd(String line){
        boolean result=false;
        if(line.indexOf("</html>")>-1){
            result=true;
        }
        return result;
    }


}
