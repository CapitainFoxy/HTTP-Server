package net.server;
import java.io.*;
import java.net.*;



public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

  
    @Override
    public void run() {

      
        try {
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            


          
            String requestLine = reader.readLine();
            System.out.println("Received request: " + requestLine);
            

          
            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            

          
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/plain");
            writer.println();
            


            writer.println("Hello from the Server!");



          
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
