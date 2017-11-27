package org.project07_3;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Server {
    public static final int PORT = 10000;
    private static Socket socket;
    static final InetSocketAddress UDPADDRESS = new InetSocketAddress("239.1.1.1", 12345);

    public static void main(String[] args) {
        System.out.println("Server started");
        while (true) {
            try (ServerSocket s = new ServerSocket(PORT)) {
                socket = s.accept();
                new Listener().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Listener extends Thread {
        private BufferedReader in;
        DatagramSocket udp_sender;

        Listener() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                udp_sender = new DatagramSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String name;
                String message;
                byte[] byte_message;
                while ((message = in.readLine()) != null) {
                    name = message;
                    message = in.readLine();
                    System.out.println(name + ": " + message);
                    byte_message = ("\n" + name + " " + message + "\n").getBytes();
                    if (message.equalsIgnoreCase("sonnet")) {
                        byte_message = getSonnet(name);
                    }
                    DatagramPacket packet = new DatagramPacket(byte_message, byte_message.length, UDPADDRESS);
                    udp_sender.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] getSonnet(String name) {
        String message = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonnets?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=false", "mysqluser", "");
            Statement statement = conn.createStatement();
            ResultSet sonnets = statement.executeQuery("SELECT * FROM sonnets");

            int randomNum = ThreadLocalRandom.current().nextInt(1, 30 + 1);
            sonnets.relative(randomNum);    // переход к выбранному случайным образом сонету
            String path = sonnets.getString("path");

            InputStream stream = new FileInputStream(path);
            byte[] buffer = new byte[stream.available()];

            int n;
            while ((n = stream.read(buffer)) != -1) {
                message += new String(buffer, 0, n, "cp1251");
            }

            message = "\nserver to: " + name + " " + "\nСонет №" + randomNum + "\n-------------\n" + message + "\n";
            // закрываем соединения
            stream.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return message.getBytes();
    }
}

