package client;

import java.io.*;
import java.net.Socket;

public class FileSender {
    private Socket socket;

    public FileSender(Socket socket) {
        if (socket.isClosed()) {
            throw new IllegalStateException("Socket is already closed");
        }
        this.socket = socket;
    }

    public void sendFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = socket.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        }
        System.out.println("File sent: " + file.getName());
    }
}
