// package client;

// import java.io.*;
// import java.net.*;

// public class ChatClient {
//     public static void main(String[] args) throws IOException {
//         Socket socket = new Socket("localhost", 12345);
//         BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
//         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//         new Thread(() -> {
//             try {
//                 String response;
//                 while ((response = in.readLine()) != null) {
//                     System.out.println("Server: " + response);
//                 }
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }).start();

//         String message;
//         while ((message = userIn.readLine()) != null) {
//             out.println(message);
//         }
//     }
// }
