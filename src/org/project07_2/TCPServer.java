package org.project07_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    static final int PORT = 10000;
    static final int PACKET_SIZE = 16;
    static final String FILENAME = "input.txt";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server ready");
        Socket socket = serverSocket.accept();
        System.out.println("Client ready, start transfer");
        OutputStream socketOut = socket.getOutputStream();
        FileInputStream inputStream = new FileInputStream(FILENAME);
        byte[] buffer = new byte[PACKET_SIZE];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            socketOut.write(buffer, 0, read);
        }
        socket.close();
        serverSocket.close();
    }
}
