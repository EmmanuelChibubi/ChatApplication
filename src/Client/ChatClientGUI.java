package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import client.FileSender; // Import FileSender class

public class ChatClientGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Socket socket;
    private PrintWriter out;

    public ChatClientGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Chat Application");
        frame.setBounds(100, 100, 400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(chatArea), BorderLayout.CENTER);

        inputField = new JTextField();
        frame.getContentPane().add(inputField, BorderLayout.SOUTH);

        sendButton = new JButton("Send");
        frame.getContentPane().add(sendButton, BorderLayout.EAST);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton sendPhotoButton = new JButton("Send Photo");
        frame.getContentPane().add(sendPhotoButton, BorderLayout.NORTH);

        sendPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    new FileSender(socket).sendFile(selectedFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);

        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        chatArea.append("Server: " + response + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        out.println(message);
        chatArea.append("You: " + message + "\n");
        inputField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatClientGUI();
            }
        });
    }
}
