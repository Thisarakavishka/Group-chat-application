package controller;

import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChatFromController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXTextArea txtArea;

    @FXML
    private JFXButton btnSend;

    private Client client;
    private String userName;

    @FXML
    void btnSendOnAction(ActionEvent event) {
        client.sendMessage(txtField.getText());
        txtArea.appendText("me - "+txtField.getText()+"\n");   //user message
        txtField.setText("");
    }

    @FXML
    void txtFieldOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    public void writeMessage(String message) {
        txtArea.appendText(message+"\n");    //client message
    }

    public void setData(Client client, String userName) {
        this.client = client;
        this.userName = userName;
    }
}
