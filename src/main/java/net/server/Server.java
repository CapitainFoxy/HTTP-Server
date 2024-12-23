package net.server;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;



public class Server {


  
    private static final int PORT = 1111; // ;)
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);


  
    public static void main(String[] args) {
        System.out.println("Server is starting on port " + PORT);


      
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                


              
                executorService.submit(new ClientHandler(clientSocket));
            }

          
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
