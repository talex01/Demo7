package org.project07_2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    private static final String OUTPUTFILENAME = "output.txt";
    private static final String CHARSET = "CP1251";
    private static final int PACKET_SIZE = TCPServer.PACKET_SIZE;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), TCPServer.PORT);
        InputStream inputStream = socket.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(OUTPUTFILENAME);
        byte[] buffer = new byte[PACKET_SIZE];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            System.out.print(new String(buffer, 0, read, CHARSET));
            outputStream.write(buffer, 0, read);
        }
        outputStream.close();
        inputStream.close();
        socket.close();

        System.out.println("\n\n\tMD5 of transmitted file:\t" + HashSum.getMD5(TCPServer.FILENAME));
        System.out.println("\tMD5 of received file:\t\t" + HashSum.getMD5(OUTPUTFILENAME));
        System.out.println("\n\n\tSHA256 of transmitted file:\t" + HashSum.getSHA(TCPServer.FILENAME));
        System.out.println("\tSHA256 of received file:\t" + HashSum.getSHA(OUTPUTFILENAME));
    }
}
