package fmi.mpr.hw.chat;

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
        buff = new byte[BUFF_LEN];

    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buff, buff.length);
                socket.receive(packet);
                String msg = new String(buff, 0, buff.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
