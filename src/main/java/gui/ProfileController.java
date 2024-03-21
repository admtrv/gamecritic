package gui;

import database.*;
import javafx.scene.shape.Line;
import session.*;
import users.*;
import validation.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import session.CurrentUser;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileController implements FieldInterface{
    @FXML private TextField usernameField;
    @FXML private PasswordField CurrentPasswordField;
    @FXML private PasswordField NewPasswordField;
    @FXML private PasswordField ConfirmPasswordField;

    @FXML private Label balanceTextLabel;
    @FXML private Label balanceValueLabel;
    @FXML private Line additionalLine;

    User user = CurrentUser.getInstance().getUser();
    @FXML
    private void initialize() {
        usernameField.setText(user.getUsername());
        if (user instanceof Critic) {
            Critic critic = (Critic) user;
            balanceValueLabel.setVisible(true);
            balanceTextLabel.setVisible(true);
            additionalLine.setVisible(true);
            balanceValueLabel.setText(critic.getBalance() + " $");
        } else {
            balanceValueLabel.setVisible(false);
            balanceTextLabel.setVisible(false);
            additionalLine.setVisible(false);
        }
    }

    public void updateUsername() {
        String newUsername = usernameField.getText();
        setUsernameNormalStyle();
        ValidationRule usernameRule = ValidationRuleFactory.getRule("username");

        if (!usernameRule.validate(newUsername)) { // Имя пользователя не прошло валидацию
            setUsernameErrorStyle();
            System.out.println(usernameRule.getErrorMessage());
            return;
        }

        try {
            if (DataBaseUtil.updateUsername(user.getId(), newUsername)) {
                user.setUsername(newUsername); // Обновляем информацию о пользователе в текущей сессии
                CurrentUser.getInstance().logIn(user); // Обновляем пользователя в синглтоне
                CurrentUser.getInstance().saveCurrentUser(); // Обновляем данные в файле сериализации
                System.out.println("Username successfully updated!");
            } else {
                setUsernameErrorStyle(); // Операция в базе данных не удалась
                System.out.println("Failed to update username!");
            }
        } catch (SQLException e) {
            setUsernameErrorStyle(); // Исключение SQL
            System.err.println("SQL error updating username!");
            e.printStackTrace();
        }
    }

    public void updatePassword() {
        String currentPassword = CurrentPasswordField.getText();
        String newPassword = NewPasswordField.getText();
        String confirmPassword = ConfirmPasswordField.getText();
        setPasswordNormalStyle();
        ValidationRule passwordRule = ValidationRuleFactory.getRule("password");

        if (!passwordRule.validate(newPassword)) { // Новый пароль не соответствует требованиям
            setPasswordErrorStyle();
            System.out.println(passwordRule.getErrorMessage());
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            setPasswordErrorStyle();
            System.out.println("The new passwords do not match!");
            return;
        }

        if (!user.getPassword().equals(currentPassword)) {
            setPasswordErrorStyle();
            System.out.println("The current password do not match original password!");
            return;
        }

        try {
            if (DataBaseUtil.updatePassword(user.getId(), newPassword)) {
                user.setPassword(newPassword); // Обновляем пароль в текущей сессии
                CurrentUser.getInstance().logIn(user); // Обновляем пользователя в синглтоне
                CurrentUser.getInstance().saveCurrentUser(); // Обновляем данные в файле сериализации
                System.out.println("Password successfully updated!");
                CurrentPasswordField.clear();
                NewPasswordField.clear();
                ConfirmPasswordField.clear();
            } else {
                setPasswordErrorStyle();
                System.out.println("Failed to update password!");
            }
        } catch (SQLException e) {
            setPasswordErrorStyle();
            System.err.println("SQL error updating password!");
            e.printStackTrace();
        }
    }

    public void signOut() throws IOException {
        CurrentUser.getInstance().logOut(); // Очистка информации о текущем пользователе
        CurrentUser.getInstance().clearCurrentUser(); // Удаление файла сериализации
        SceneController.getInstance().switchScene("login.fxml"); // Переход на экран логина
        System.out.println("User signed out!");
    }

    @FXML
    public void setUsernameErrorStyle(){
        usernameField.setStyle(errorFieldStyle);
    }

    @FXML
    public void setPasswordErrorStyle(){
        CurrentPasswordField.setStyle(errorFieldStyle);
        NewPasswordField.setStyle(errorFieldStyle);
        ConfirmPasswordField.setStyle(errorFieldStyle);
    }

    @Override
    public void setUsernameNormalStyle() {
        usernameField.setStyle(normalFieldStyle);
    }

    @Override
    public void setPasswordNormalStyle() {
        CurrentPasswordField.setStyle(normalFieldStyle);
        NewPasswordField.setStyle(normalFieldStyle);
        ConfirmPasswordField.setStyle(normalFieldStyle);
    }

    public void switchToGamesScene() throws IOException {
    }
    public void switchToYearsScene() throws IOException {
    }
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }
}
