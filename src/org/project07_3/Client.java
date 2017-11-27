/*
Класс клиент. Два потока: один слушает, другой отправляет
*/

package org.project07_3;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private static MulticastSocket server;

    public static void main(String[] args) {
        new Listener().start();
        new Sender().start();
    }

    // этот поток работает по протоколу UDP. слушает сообщения из UDP-группы сервера
    private static class Listener extends Thread {

        Listener() {
            try {
                server = new MulticastSocket(Server.UDPADDRESS.getPort());
                server.joinGroup(Server.UDPADDRESS.getAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // сложно сказать, какой объем информации отправит нам сервер, ставим предел 4 кб
            DatagramPacket packet = new DatagramPacket(new byte[4096], 4096);
            String message;
            // регулярка. фильтр сообщений вида "отправитель to: имя_клиента текст сообщения".
            // первая группа - отправитель, вторая - "to:", третья - кому, четвертая - сообщение (латиница, кириллица, спецсимволы)
            final String regex = "(\\w+)\\s(to:)\\s(\\w+)\\s([a-zA-Z\u0430-\u044f\u0410-\u042f0-9_\\s\\[\\\\\\^$.\u2116,\\\\:\"|?!@#%=/\\-'*+&()]+)";
            final Pattern pattern = Pattern.compile(regex); //разбор регулярки
            Matcher matcher;
            while (true) {
                try {
                    server.receive(packet); // принимаем пакет
                    message = new String(packet.getData(), packet.getOffset(), packet.getLength());
                    matcher = pattern.matcher(message);
                    // разбор сообщения по группам согласно регулярному выражению
                    while (matcher.find()) {
                        if (matcher.group(2).equalsIgnoreCase( "to:")) {           // если сообщение содержит "to: " во второй группе
                            if (matcher.group(3).equalsIgnoreCase(Sender.getSenderName())) {    // если сообщение адресовано нам
                                System.out.print("\n" + matcher.group(1) + " sent message to you: " + matcher.group(4) + "\n" + matcher.group(3) + ": ");   // выводим сообщение на экран
                            }
                        }
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
    }

    // поток работает по TCP. Общение с сервером. Здесь даже комментировать нечего.
    public static class Sender extends Thread {
        private static String name;
        private Socket socket;
        private PrintWriter out;

        Sender() {
            try {
                socket = new Socket(InetAddress.getLocalHost(), Server.PORT);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                try {
                    socket.close();
                } catch (IOException e1) {
                    System.err.println(e.getMessage());
                }
            }
        }

        static String getSenderName() {
            return name;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите Ваш ник (одно слово латиницей): ");
            name = scanner.next();
            scanner.nextLine();
            System.out.println("Для отправки сообщения другому клиенту введите 'to: имя_клиента текст сообщения'");
            System.out.println("Для получения сонета введите команду 'sonnet'");
            System.out.println("Введите 'bye' для выхода");
            String message;
            while (true) {
                System.out.print(name + ": ");
                message = scanner.nextLine();
                out.println(name);      // передача серверу имени клиента
                out.println(message);   // передача серверу сообщения
                if (message.equalsIgnoreCase("bye")) break;
            }
            // корректно закрываем соединения
            try {
                server.leaveGroup(Server.UDPADDRESS.getAddress());
                server.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
