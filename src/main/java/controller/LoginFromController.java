package controller;

import bo.BOFactory;
import bo.bos.UserBO;
import client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.NotificationUtil;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class LoginFromController {

    public JFXTextField password;
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtField;

    @FXML
    private JFXButton btnLaunch;

    //Dependency Injection
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnLaunchOnAction(ActionEvent event) {
        try {
            UserDTO user = userBO.searchUser(txtField.getText());
            if (user != null) {
                if (user.getPassword().equals(password.getText()) && user.getStatus() == 0) {
                    try {
                        Client client = new Client(new Socket("localhost", 3002), txtField.getText());
                        client.readMessage();
                        txtField.setText("");
                        password.setText("");
                        boolean isUpdate = userBO.updateUser(new UserDTO(user.getUserName(), user.getPassword(), 1));
                        NotificationUtil.showNotification("Successfully", "Successfully login to Chat Room " + user.getUserName(), NotificationUtil.NotificationType.SUCCESS, Duration.seconds(5));

                    } catch (IOException e) {
                        NotificationUtil.showNotification("oops !", "Oops ! something happened to Host", NotificationUtil.NotificationType.ERROR, Duration.seconds(5));
                    }
                } else if (user.getStatus() == 1) {
                    NotificationUtil.showNotification("oops !", "User is in the Chat Room", NotificationUtil.NotificationType.INFORMATION, Duration.seconds(5));
                } else {
                    NotificationUtil.showNotification("Error", "Enter correct details", NotificationUtil.NotificationType.ERROR, Duration.seconds(5));
                }
            }
        } catch (SQLException | NullPointerException | ClassNotFoundException e) {

        }
    }

    @FXML
    void txtFieldOnAction(ActionEvent event) {
        password.requestFocus();
    }

    public void passwordOnAction(ActionEvent event) {
        btnLaunchOnAction(event);
    }
}
