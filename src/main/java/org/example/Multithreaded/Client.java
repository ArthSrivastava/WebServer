package org.example.Multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable{

    @Override
    public void run() {
        int port = 8080;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            Socket clientSocket = new Socket(inetAddress, port);

            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toServer.println("Hello server! This is client!");
            System.out.println("Text received from server: " + fromServer.readLine());
            toServer.close();
            fromServer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        for (int i = 0; i < 100; i++) {
            new Thread(client).start();
        }
    }
}
