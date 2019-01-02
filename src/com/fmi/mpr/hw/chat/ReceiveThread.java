package com.fmi.mpr.hw.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReceiveThread implements Runnable {
    final static int BUFF_LEN = 4096;

    private MulticastSocket socket;
    private InetAddress addr;
    private String userName;
    private int port;
    private byte[] buff;

    ReceiveThread(MulticastSocket _socket, InetAddress _addr, String _userName, int _port) {
        socket = _socket;
        addr = _addr;
        userName = _userName;
        port = _port;
    }

    @Override
    public void run() {
        while (true) {
            buff = new byte[BUFF_LEN];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            try {
                socket.receive(packet);
                String msg = new String(buff, 0, buff.length);
                System.out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
