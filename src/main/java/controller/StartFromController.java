package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class StartFromController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnStart;

    @FXML
    void btnStartOnAction(ActionEvent event) {
        try {
            launchServer();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginFrom.fxml"))));
            stage.setTitle("Login Form");
            stage.setOnCloseRequest(windowEvent -> {
                System.exit(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void launchServer() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(3002);
                Server server = new Server(serverSocket);
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
