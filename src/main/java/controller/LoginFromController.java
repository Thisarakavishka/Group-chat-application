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

    private UserDTO user;

    //Dependency Injection
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnLaunchOnAction(ActionEvent event) {
        try {
            user = userBO.searchUser(txtField.getText());
            if (user != null) {
                if (user.getPassword().equals(password.getText())) {
                    try {
                        NotificationUtil.showNotification("Successfully", "Successfully login to Chat Room " + user.getUserName(), NotificationUtil.NotificationType.SUCCESS, Duration.seconds(5));
                        Client client = new Client(new Socket("localhost", 3002), user.getUserName());
                        userBO.updateUser(new UserDTO(user.getUserName(), user.getPassword(), 1));
                        client.readMessage();
                        txtField.setText("");
                    } catch (IOException e) {
                        NotificationUtil.showNotification("oops !", "Oops ! something happened to localHost", NotificationUtil.NotificationType.ERROR, Duration.seconds(5));
                    }
                } else {
                    NotificationUtil.showNotification("oops !", "There password is incorrect", NotificationUtil.NotificationType.INFORMATION, Duration.seconds(5));
                }
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            NotificationUtil.showNotification("Error", "There is no user " + txtField.getText(), NotificationUtil.NotificationType.ERROR, Duration.seconds(5));
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
