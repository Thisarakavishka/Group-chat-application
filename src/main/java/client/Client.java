package client;

import controller.ChatFromController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private String userName;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ChatFromController chatFromController;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.userName = userName;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(userName);
            dataOutputStream.flush();
            launchScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchScene() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ChatFrom.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle(userName + "' Room ");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
            chatFromController = fxmlLoader.getController();
            chatFromController.setData(this, userName);
            stage.setOnCloseRequest(windowEvent -> {
                close();
            });
        } catch (IOException e) {
            close();
        }
    }

    private void close() {
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


    public void readMessage() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String message = dataInputStream.readUTF();
                    if ("--image--".equals(message)) {
                        readImage();
                    } else {
                        chatFromController.writeMessage(message);
                    }
                } catch (IOException e) {
                    close();
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            close();
        }
    }

    public void sendImage(byte[] bytes) {
        try {
            dataOutputStream.writeUTF("--image--");
            dataOutputStream.writeInt(bytes.length);
            dataOutputStream.write(bytes);
            dataOutputStream.flush();
        } catch (IOException e) {
            close();
        }
    }

    private void readImage() {
        System.out.println("image receive successfully");
        try {
            String sender = dataInputStream.readUTF();
            int length = dataInputStream.readInt();
            byte[] bytes = new byte[length];
            dataInputStream.readFully(bytes);
            chatFromController.writeImage(sender, bytes);
        } catch (IOException e) {
            close();
        }
    }
}
