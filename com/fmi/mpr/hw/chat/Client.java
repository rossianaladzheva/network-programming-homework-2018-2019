package fmi.mpr.hw.chat;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    final static String IP = "224.0.0.1";
    final static int PORT = 4444;
    final static int BUFF_LEN = 4096;
    private static Scanner sc = new Scanner(System.in);

    private MulticastSocket socket;
    private InetAddress addr;
    private String userName;
    private byte[] buff;

    Client(MulticastSocket _socket, String _groupIP, String _userName) {
        userName = _userName;
        buff = new byte[BUFF_LEN];
        try {
            socket = new MulticastSocket(PORT);
            addr = InetAddress.getByName(_groupIP);
            socket.joinGroup(addr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String msg) {
        try (DatagramSocket dgSocket = new DatagramSocket()) {
            String data = userName + msg;
            DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, addr, PORT);
            socket.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







