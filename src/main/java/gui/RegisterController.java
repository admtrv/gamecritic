package gui;

import database.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.io.IOException;

public class RegisterController{
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;

    @FXML
    private void initialize() {
        Platform.runLater(() -> usernameField.requestFocus());
        roleComboBox.getItems().addAll("User", "Critic", "Administrator"); // Cписок ролей
    }

    @FXML
    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String userType = roleComboBox.getValue();

        try {
            DataBaseUtil.addUser(username, password, userType);
            System.out.println("User registered successfully!");
            switchToLoginScene();
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            // Отображение ошибки пользователю
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToLoginScene() throws IOException {
        SceneController.getInstance().switchScene("login.fxml");
    }

}
