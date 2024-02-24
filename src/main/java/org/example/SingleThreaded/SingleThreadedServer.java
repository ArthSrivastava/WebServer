package org.example.SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadedServer {
    
    public void run() throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            serverSocket.setSoTimeout(120000);
            while (true) {
                System.out.println("Server is listening on port: " + port);
                Socket socket = serverSocket.accept();
                System.out.println("Connection established with client: " + socket.getLocalAddress().getHostAddress());
                processReq(socket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    private void processReq(Socket socket) {
        try {
            PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toClient.println("Hello from server");
            String textReceived = fromClient.readLine();
            System.out.println("Text received from client: " + textReceived);
            toClient.close();
            fromClient.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SingleThreadedServer singleThreadedServer = new SingleThreadedServer();
        try {
            singleThreadedServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
