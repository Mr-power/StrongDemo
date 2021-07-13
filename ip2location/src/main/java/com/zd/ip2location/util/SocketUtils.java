package com.zd.ip2location.util;

import com.zd.ip2location.config.serviceIpConfig;

import java.io.IOException;
import java.net.*;

public class SocketUtils {
    public static int send(){
        try {
            //实列化套接字

            MulticastSocket Socket=new MulticastSocket();
            for(String  l1 : serviceIpConfig.DEFAULT_IP) {

                String[] ipStr = l1.split("\\.");
                byte[] ipBuf = new byte[4];
                for(int i = 0; i < 4; i++){
                    ipBuf[i] = (byte)(Integer.parseInt(ipStr[i])&0xff);
                }
                InetAddress netAdd=InetAddress.getByAddress(ipBuf);
                Socket.joinGroup(netAdd);
            }
            return  Socket.getTimeToLive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 被动接受发送的数据
     * @param port    监听收数据的端口
     * @return
     */
    public static String receive(int port){
        String data=null;
        try {
            //在本机上监视这个port端口
            DatagramSocket datagramSocket=new DatagramSocket(port);

            //定义接受数据的包
            byte[] buf=new byte[1024];
            DatagramPacket datagramPacket=new DatagramPacket(buf,0,buf.length);
            //接受到次包
            datagramSocket.receive(datagramPacket);
            //从这个包中取出数据
            data=new String(datagramPacket.getData(),0,datagramPacket.getLength());

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
