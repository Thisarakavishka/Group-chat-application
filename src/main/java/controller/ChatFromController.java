package controller;

import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatFromController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXButton btnImage;

    @FXML
    private JFXButton btnEmoji;

    @FXML
    private VBox vbox;

    private Client client;
    private String userName;

    @FXML
    void btnEmojiOnAction(ActionEvent event) {
        System.out.println("Emoji");
    }

    @FXML
    void btnImageOnAction(ActionEvent event) {
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        client.sendMessage(txtField.getText());

        // Create Hbox and set the style properties of the HBox
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right; -fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10");

        // Create Label and add it to the HBox
        Label label = new Label(txtField.getText());
        label.setStyle(" -fx-alignment: center-left; -fx-background-color:  #a11d3f; -fx-background-radius:15; -fx-font-size: 18; -fx-text-fill: #ffffff; -fx-wrap-text: true; -fx-content-display: left; -fx-max-width: 350; -fx-padding: 10;");
        hBox.getChildren().add(label);
        vbox.getChildren().add(hBox);
        txtField.setText("");
    }

    @FXML
    void txtFieldOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    public void writeMessage(String message) {
        // Create Hbox and set the style properties of the HBox
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left; -fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10");

        // Create Label and add it to the HBox
        Label label = new Label(message);
        label.setStyle(" -fx-alignment: center-left; -fx-background-color:  #45bea8; -fx-background-radius:15; -fx-font-size: 18; -fx-text-fill: #ffffff; -fx-wrap-text: true; -fx-content-display: left; -fx-max-width: 350; -fx-padding: 10;");
        hBox.getChildren().add(label);
        Platform.runLater(() -> vbox.getChildren().add(hBox));
        System.out.println(message);
    }

    public void setData(Client client, String userName) {
        this.client = client;
        this.userName = userName;
    }
}
