package org.example.Multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class MultithreadedServer {
    
    public Consumer<Socket> getConsumer() {
        return this::processReq;
    }
    public void run() throws IOException {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(20000);
            while (true) {
                System.out.println("Server is listening on port: " + port);
                Socket socket = serverSocket.accept();
                System.out.println("Connection established with client: " + socket.getLocalAddress().getHostAddress());
                new Thread(() -> this.getConsumer().accept(socket)).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
        MultithreadedServer multithreadedServer = new MultithreadedServer();
        try {
            multithreadedServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
