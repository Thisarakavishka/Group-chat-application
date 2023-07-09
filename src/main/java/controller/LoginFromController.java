package controller;

import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.Socket;

public class LoginFromController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXButton btnLaunch;

    @FXML
    void btnLaunchOnAction(ActionEvent event) {
        try {
            Client client = new Client(new Socket("localhost", 3002), txtField.getText());
            client.readMessage();
            txtField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtFieldOnAction(ActionEvent event) {
        btnLaunchOnAction(event);
    }
}
