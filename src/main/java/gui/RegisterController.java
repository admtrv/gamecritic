package gui;

import gui_interfaces.*;
import utils.*;
import validation_factory.*;
import logger_decorator.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.io.IOException;
public class RegisterController implements FieldInterface {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML
    private void initialize() {
        Platform.runLater(() -> usernameField.requestFocus());
        roleComboBox.getItems().addAll("User", "Critic"/*, "Administrator"*/); // List of roles
    }

    @FXML
    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String userType = roleComboBox.getValue();

        setUsernameNormalStyle();
        setPasswordNormalStyle();
        roleComboBox.setStyle(normalFieldStyle);

        // Getting validation factory rules
        ValidationRule usernameRule = ValidationRuleFactory.getRule("username");
        ValidationRule passwordRule = ValidationRuleFactory.getRule("password");

        Logger logger = new UserDataLogger(new TimeLogger(new FileLogger()), username, password);

        // Username validation factory
        if (!usernameRule.validate(username)) {
            setUsernameErrorStyle();

            AlertUtil.showAlert("Invalid Username", usernameRule.getErrorMessage(), Alert.AlertType.ERROR);
            logger.log("Invalid attempt for username", LoggerLevel.ERROR);
            System.err.println(usernameRule.getErrorMessage());

            return;
        }

        // Password validation factory
        if (!passwordRule.validate(password)) {
            setPasswordErrorStyle();

            AlertUtil.showAlert("Invalid Password", passwordRule.getErrorMessage(), Alert.AlertType.ERROR);
            logger.log("Invalid attempt for password", LoggerLevel.ERROR);
            System.err.println(passwordRule.getErrorMessage());

            return;
        }

        // Role validation
        if (userType == null) {
            roleComboBox.setStyle(errorFieldStyle);

            AlertUtil.showAlert("Role Selection Required", "Please select a role before registering.", Alert.AlertType.ERROR);
            logger.log("Invalid attempt for role", LoggerLevel.ERROR);
            System.err.println("Role must be selected!");
            return;
        }

        // Trying to register a user in the database
        try {
            DataBaseUtil.addUser(username, password, userType);

            AlertUtil.showAlert("Registration Successful", "Your account registered successfully!", Alert.AlertType.INFORMATION);
            logger.log("Registered user", LoggerLevel.INFO);
            System.out.println("User registered successfully!");

            switchToLoginScene();
        } catch (SQLException | IOException e) {
            setUsernameErrorStyle();

            AlertUtil.showAlert("Entry Failed", "Sorry, account with that nickname already exists. Please try again.", Alert.AlertType.ERROR);
            logger.log("Problem in registering user", LoggerLevel.DEBUG);
            System.err.println("Error updating data!");
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

    public void switchToLoginScene() throws IOException {
        SceneController.getInstance().switchScene("login.fxml");
    }
}
