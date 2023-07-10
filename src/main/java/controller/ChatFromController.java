package controller;

import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatFromController implements Initializable {

    @FXML
    private AnchorPane emojiBar;

    @FXML
    public GridPane emojiPane;

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
    private final String[] emojis = {
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE0C", // 😌
            "\uD83D\uDE0D", // 😍
            "\uD83D\uDE0E", // 😎
            "\uD83D\uDE0F", // 😏
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE11", // 😑
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE13", // 😓
            "\uD83D\uDE0B", // 😋
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE07"  // 😇
    };
    private Client client;
    private String userName;

    @FXML
    void btnEmojiOnAction(ActionEvent event) {
        emojiBar.setVisible(!emojiBar.isVisible());
    }

    @FXML
    void btnImageOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {

                //Set selected image for sender's Chat Room
                ImageView imageView = new ImageView(new Image(new FileInputStream(file)));
                imageView.setFitHeight(200);
                imageView.setFitWidth(200);

                //Create Hbox for add properties
                HBox hBox = new HBox();
                hBox.setStyle("-fx-alignment: center-right; -fx-fill-height: true; -fx-min-height: 50px; -fx-pref-width: 520px; -fx-max-width: 520px; -fx-padding: 10px; ");

                //Ask user need to send image to the Chat Room
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you need to send this " + file.getName() + " image to chat room ?", ButtonType.OK, ButtonType.NO);
                alert.setTitle("Send Image");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    System.out.println("send image to Chat Room");

                    //Create byte array and send it to client
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    client.sendImage(bytes);
                    hBox.getChildren().add(imageView);
                    vbox.getChildren().add(hBox);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        hBox.setStyle("-fx-alignment: center-left; -fx-fill-height: true; -fx-min-height: 50px; -fx-pref-width: 520px; -fx-max-width: 520px; -fx-padding: 10px");

        // Create Label and add it to the HBox
        Label label = new Label(message);
        label.setStyle(" -fx-alignment: center-left; -fx-background-color:  #45bea8; -fx-background-radius:15px; -fx-font-size: 18px; -fx-text-fill: #ffffff; -fx-wrap-text: true; -fx-content-display: left; -fx-max-width: 350px; -fx-padding: 10px;");
        hBox.getChildren().add(label);
        Platform.runLater(() -> vbox.getChildren().add(hBox));
        System.out.println(message);
    }

    public void setData(Client client, String userName) {
        this.client = client;
        this.userName = userName;
    }

    public void writeImage(String sender, byte[] bytes) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left; -fx-fill-height: true; -fx-min-height: 50px; -fx-pref-width: 520px; -fx-max-width: 520px; -fx-padding: 10px");

        Label label = new Label(sender);
        label.setStyle(" -fx-alignment: center-left; -fx-background-color:  #45bea8; -fx-background-radius:15px; -fx-font-size: 18px; -fx-text-fill: #ffffff; -fx-wrap-text: true; -fx-content-display: left; -fx-max-width: 350px; -fx-padding: 10px;");
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            hBox.getChildren().addAll(label, imageView);
            vbox.getChildren().add(hBox);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emojiBar.setVisible(false);
        createEmojiBar();
    }

    private void createEmojiBar() {
        int btnIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 5; column++) {
                if (btnIndex < emojis.length) {
                    JFXButton button = new JFXButton(emojis[btnIndex]);
                    button.setStyle("-fx-background-radius:20px;-fx-text-alignment: center; -fx-background-color: #b7fffc;");
                    button.setAlignment(Pos.CENTER);
                    emojiPane.add(button, column, row);
                    button.setOnAction(event -> {
                        txtField.appendText(button.getText());
                    });
                    btnIndex++;
                }
            }
        }
    }
}
