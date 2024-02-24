package org.example.SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private void singleThreadedClient() throws IOException {
        int port = 8080;
        InetAddress inetAddress = InetAddress.getLocalHost();
        Socket clientSocket = new Socket(inetAddress, port);

        PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        toServer.println("Hello server! This is client!");
        System.out.println("Text received from server: " + fromServer.readLine());
        toServer.close();
        fromServer.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        Client client = new Client();

        try {
            client.singleThreadedClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
