/*
    клиент отправляет, сервер принимает
*/

package org.project07_2;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClient {
    static final String FILENAME = "input.txt";
    static final int PACKET_SIZE = 16;
    static final InetSocketAddress ADDRESS = new InetSocketAddress("239.1.1.1", 12345);

    public static void main(String[] args) {
        try (DatagramSocket client = new DatagramSocket()) {
            byte[] buffer = new byte[PACKET_SIZE];
            int read;
            FileInputStream inputStream = new FileInputStream(FILENAME);
            System.out.println("Multicast file transfer started...");
            while ((read = inputStream.read(buffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(buffer, read, ADDRESS);
                client.send(packet);
            }
            // посылаем признак окончания передачи
            client.send(new DatagramPacket("bye".getBytes(), 3, ADDRESS));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
