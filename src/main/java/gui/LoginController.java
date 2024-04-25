package gui;

import gui_interfaces.*;
import logger_decorator.*;
import users.*;
import session.*;
import utils.*;

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

        Logger logger = new UserDataLogger(new TimeLogger(new FileLogger()), username, password);

        try {
            User user = DataBaseUtil.getUser(username);

            if (user != null) {
                if (user.getPassword().equals(password)) {

                    CurrentUser.getInstance().logIn(user);
                    CurrentUser.getInstance().saveCurrentUser();

                    logger.log("Found user", LoggerLevel.INFO);
                    System.out.println("User found successfully!");

                    switchToHomeScene();
                } else {
                    setPasswordErrorStyle();

                    AlertUtil.showAlert("Invalid Password", "Sorry, the password you entered is incorrect. Please try again.", Alert.AlertType.ERROR);
                    logger.log("Invalid attempt for password", LoggerLevel.ERROR);
                    System.err.println("Password does not match!");
                }
            } else {
                setUsernameErrorStyle();

                AlertUtil.showAlert("Invalid Username", "Sorry, we can't find an account with this username. Please try again.", Alert.AlertType.ERROR);
                logger.log("Invalid attempt for username", LoggerLevel.ERROR);
                System.err.println("There is no user with that username!");
            }
        } catch (SQLException e) {

            AlertUtil.showAlert("Entry Failed", "Sorry, there was an error while searching your account. Please try again.", Alert.AlertType.ERROR);
            logger.log("Problem in finding user", LoggerLevel.DEBUG);
            System.err.println("Error finding user!");
            e.printStackTrace();
        } catch (IOException e) {

            AlertUtil.showAlert("Entry Failed", "Sorry, there was an error while searching your account. Please try again.", Alert.AlertType.ERROR);
            logger.log("Problem in finding user", LoggerLevel.DEBUG);
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
