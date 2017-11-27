/*
    клиент отправляет, сервер принимает
*/

package org.project07_2;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class UDPServer {
    private static final String OUTPUTFILENAME = "output.txt";
    private static final String CHARSET = "CP1251";
    private static final int PACKET_SIZE = UDPClient.PACKET_SIZE;

    public static void main(String[] args) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(OUTPUTFILENAME);

        try (MulticastSocket server = new MulticastSocket(UDPClient.ADDRESS.getPort())) {
            server.joinGroup(UDPClient.ADDRESS.getAddress());

            DatagramPacket packet = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
            String str;
            System.out.println("UDP server is ready to receive data");
            while (true) {
                server.receive(packet);
                if (!(str = new String(packet.getData(), packet.getOffset(), packet.getLength(), CHARSET)).equals("bye")) {
                    outputStream.write(str.getBytes());
                    System.out.print(str);
                } else break;
            }
            outputStream.close();
            server.leaveGroup(UDPClient.ADDRESS.getAddress());
            server.close();
        }

        System.out.println("\n\n\tMD5 of transmitted file:\t" + HashSum.getMD5(UDPClient.FILENAME));
        System.out.println("\tMD5 of received file:\t\t" + HashSum.getMD5(OUTPUTFILENAME));
        System.out.println("\n\n\tSHA256 of transmitted file:\t" + HashSum.getSHA(UDPClient.FILENAME));
        System.out.println("\tSHA256 of received file:\t" + HashSum.getSHA(OUTPUTFILENAME));
    }
}
