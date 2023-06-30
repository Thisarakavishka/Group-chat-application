package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String userName;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.userName = dataInputStream.readUTF();
            clientHandlers.add(this);  //add this instance for clientHandler arraylist
            serverMessage("[SERVER] -" + userName + " is joined the chat");
        } catch (IOException e) {
            close(socket, dataOutputStream, dataInputStream);
        }
    }

    private void publicMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.userName.equals(userName)) {
                clientHandler.sendMessage(userName + " - " + message);
            }
        }
    }

    private void serverMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.userName.equals(userName)) {
                clientHandler.sendMessage(message);
            }
        }
    }

    private void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            close(socket, dataOutputStream, dataInputStream);
        }
    }

    @Override
    public void run() {
        String reply;
        while (socket.isConnected()) {
            try {
                reply = dataInputStream.readUTF();
                publicMessage(reply);
            } catch (IOException e) {
                close(socket, dataOutputStream, dataInputStream);
                System.out.println("client socket is closing");
                break;
            }
        }
    }

    private void close(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        removeClientHandler();
        try {
            if (socket != null) {
                socket.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClientHandler() {
        System.out.println("this is removeClientHandler()");
        clientHandlers.remove(this);
        serverMessage("[SERVER] -" + userName + " is left the chat");
    }
}