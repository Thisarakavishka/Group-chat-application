package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ChatRoomController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXButton BtnSend;

    @FXML
    private JFXScrollPane scrollPane;

    @FXML
    private VBox vbox;

    @FXML
    void BtnSendOnAction(ActionEvent event) {

    }

    @FXML
    void txtFieldOnAction(ActionEvent event) {
        BtnSendOnAction(event);
    }

}
