package programm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private String userName;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = bufferedReader.readLine();
            clientHandlers.add(this);
            publicMessage("[SERVER] - " + userName + " has joined the chat room");
        } catch (IOException e) {
            close(socket, bufferedReader, bufferedWriter);
        }

    }

    private void publicMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.userName.equalsIgnoreCase(userName)) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientHandler() {
        clientHandlers.remove(this);
        publicMessage("[SERVER] - " + userName + " has left the chat!");
    }

    @Override
    public void run() {
        String reply;
        while (socket.isConnected()) {
            try {
                reply = bufferedReader.readLine();
                publicMessage(reply);
            } catch (IOException e) {
                close(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
}
