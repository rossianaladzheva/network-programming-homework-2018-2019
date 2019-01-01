package fmi.mpr.hw.chat;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    final static String IP = "224.0.0.1";
    final static int BUFF_LEN = 4096;
    final static int PORT = 4444;

    private MulticastSocket socket;
    private InetAddress addr;
    private String userName;
    private byte[] buff;

    Client(int _port, String _addr, String _userName) {
        userName = _userName;
        try {
            buff = new byte[BUFF_LEN];
            socket = new MulticastSocket(_port);
            addr = InetAddress.getByName(_addr);
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

    public void communicate(){
        Thread receiving = new Thread(new ReceiveThread(socket, addr, userName, PORT));
        receiving.start();
        while(true){
            System.out.println("What type of message are you sending? (TEXT, IMAGE, VIDEO) ");
            Scanner sc = new Scanner(System.in);
            String msgType = sc.nextLine();

            System.out.println("Enter your message: ");
            String msg = sc.nextLine();
            sendData(msg);
        }
    }

    public static void main(String[] args) {
        System.out.println("Username: ");
        Scanner sc = new Scanner(System.in);
        String userName = sc.nextLine();
        Client client = new Client(PORT,IP,userName );
        client.communicate();
    }
}







