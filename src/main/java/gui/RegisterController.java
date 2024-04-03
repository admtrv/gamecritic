package gui;

import database.*;
import validation.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.io.IOException;

public class RegisterController implements FieldInterface{
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

        setUsernameNormalStyle();
        setPasswordNormalStyle();

        // Получение правил валидации
        ValidationRule usernameRule = ValidationRuleFactory.getRule("username");
        ValidationRule passwordRule = ValidationRuleFactory.getRule("password");

        boolean isValid = true; // Флаг, отслеживающий валидность данных

        // Валидация имени пользователя
        if (!usernameRule.validate(username)) {
            setUsernameErrorStyle();
            System.out.println(usernameRule.getErrorMessage());
            isValid = false; // Обновление флага валидности
        }

        // Валидация пароля
        if (!passwordRule.validate(password)) {
            setPasswordErrorStyle();
            System.out.println(passwordRule.getErrorMessage());
            isValid = false; // Обновление флага валидности
        }

        // Если данные не валидны, прерываем выполнение метода
        if (!isValid) {
            return;
        }

        // Попытка регистрации пользователя в базе данных
        try {
            DataBaseUtil.addUser(username, password, userType);
            System.out.println("User registered successfully!");
            switchToLoginScene();
        } catch (SQLException | IOException e) {
            setUsernameErrorStyle();
            setPasswordErrorStyle();
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
