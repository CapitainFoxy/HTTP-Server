package net.server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {

    private static final String STATIC_FILES_PATH = "static"; 
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

            if (requestLine != null) {
                String[] parts = requestLine.split(" ");
                if (parts.length >= 2) {
                    String method = parts[0];
                    String path = parts[1];

                    if (method.equals("GET")) {
                        handleGetRequest(path);
                    } else {
                        sendResponse(405, "Method Not Allowed", "Only GET method is supported.");
                    }
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetRequest(String path) throws IOException {
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        if (path.equals("/")) {
            sendResponse(200, "OK", "Welcome to the server!");
        } else if (path.equals("/json")) {
            sendJsonResponse();
        } else {
            serveStaticFile(path, writer, output);
        }
    }

    private void serveStaticFile(String path, PrintWriter writer, OutputStream output) throws IOException {
        File file = new File(STATIC_FILES_PATH + path);

        if (file.exists() && !file.isDirectory()) {
            String contentType = Files.probeContentType(Paths.get(file.getPath()));
            byte[] fileContent = Files.readAllBytes(file.toPath());

            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: " + (contentType != null ? contentType : "application/octet-stream"));
            writer.println("Content-Length: " + fileContent.length);
            writer.println();
            output.write(fileContent);
        } else {
            sendResponse(404, "Not Found", "The requested resource was not found.");
        }
    }

    private void sendJsonResponse() throws IOException {
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        String jsonResponse = "{\"message\": \"Hello, this is a JSON response!\"}";
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: application/json");
        writer.println("Content-Length: " + jsonResponse.length());
        writer.println();
        writer.println(jsonResponse);
    }

    private void sendResponse(int statusCode, String statusText, String body) throws IOException {
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        writer.println("HTTP/1.1 " + statusCode + " " + statusText);
        writer.println("Content-Type: text/plain");
        writer.println("Content-Length: " + body.length());
        writer.println();
        writer.println(body);
    }
}
