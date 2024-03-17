package gui;

import users.*;
import database.*;
import session.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.io.IOException;

public class LoginController{
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML
    private void initialize() {
        Platform.runLater(() -> usernameField.requestFocus());
    }

    @FXML
    private void signIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            User user = DataBaseUtil.findUser(username, password);
            if (user != null) {
                System.out.println("User found successfully!");
                CurrentUser.getInstance().logIn(user);
                CurrentUser.getInstance().saveCurrentUser();
                switchToHomeScene();
            } else {
                System.out.println("There is no user with that username!");
            }
        } catch (SQLException e) {
            System.err.println("Error finding user!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToRegisterScene() throws IOException {
        SceneController.getInstance().switchScene("register.fxml");
    }

    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }

}
