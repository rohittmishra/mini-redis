package com.rohit.miniredis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket door = new ServerSocket(6380);
        System.out.println("Waiting at port 6380");
        while (true) {
            Socket visitor = door.accept();
            System.out.println("Someone Arrived!");
            while (true) {
                InputStream pipe = visitor.getInputStream();
                byte[] letterBytes = new byte[1024];
                int count = pipe.read(letterBytes);
                if (count == -1)
                    break;

                String text = new String(letterBytes, 0, count);
                System.out.println(text);

                String answer;
                if (text.toUpperCase().contains("PING")) {
                    answer = "+PONG\r\n";
                } else {
                    answer = "-ERR unknown command\r\n";
                }

                OutputStream returnPipe = visitor.getOutputStream();
                byte[] replyBytes = answer.getBytes();
                returnPipe.write(replyBytes);
            }
            visitor.close();

        }

    }

}
