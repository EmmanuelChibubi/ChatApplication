package server;

import java.io.*;
import java.net.Socket;

public class FileReceiver {
    private Socket clientSocket;

    public FileReceiver(Socket socket) {
        this.clientSocket = socket;
    }

    public void receiveFile(String fileName) throws IOException {
        File file = new File("resources/files/" + fileName);
        try (FileOutputStream fos = new FileOutputStream(file);
             InputStream is = clientSocket.getInputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("File received: " + fileName);
    }
}
