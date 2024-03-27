package gui;

import users.*;
import database.*;
import session.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.io.IOException;

public class LoginController implements FieldInterface {
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

        setUsernameNormalStyle();
        setPasswordNormalStyle();

        try {
            User user = DataBaseUtil.getUser(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    System.out.println("User found successfully!");
                    CurrentUser.getInstance().logIn(user);
                    CurrentUser.getInstance().saveCurrentUser();
                    switchToHomeScene();
                } else {
                    setPasswordErrorStyle();
                    System.out.println("Password does not match!");
                }
            } else {
                System.out.println("There is no user with that username!");
                setUsernameErrorStyle();
            }
        } catch (SQLException e) {
            System.err.println("Error finding user!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error switching scene!");
            e.printStackTrace();
        }
    }

    @Override
    public void setUsernameErrorStyle() {
        usernameField.setStyle(errorFieldStyle);
    }

    @Override
    public void setPasswordErrorStyle() {
        passwordField.setStyle(errorFieldStyle);
    }

    @Override
    public void setUsernameNormalStyle() {
        usernameField.setStyle(normalFieldStyle);
    }

    @Override
    public void setPasswordNormalStyle() {
        passwordField.setStyle(normalFieldStyle);
    }

    public void switchToRegisterScene() throws IOException {
        SceneController.getInstance().switchScene("register.fxml");
    }

    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }

}
