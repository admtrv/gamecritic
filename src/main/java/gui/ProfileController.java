package gui;

import database.*;
import session.*;
import users.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import session.CurrentUser;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileController {
    @FXML private TextField usernameField;
    @FXML private PasswordField CurrentPasswordField;
    @FXML private PasswordField NewPasswordField;
    @FXML private PasswordField ConfirmPasswordField;
    @FXML private Label balanceTextLabel;
    @FXML private Label balanceValueLabel;

    User user = CurrentUser.getInstance().getUser();
    @FXML
    private void initialize() {
        usernameField.setText(user.getUsername());
        if (user instanceof Critic) {
            Critic critic = (Critic) user;
            balanceValueLabel.setVisible(true);
            balanceTextLabel.setVisible(true);
            balanceValueLabel.setText(critic.getBalance() + " $");
        } else {
            balanceValueLabel.setVisible(false);
            balanceTextLabel.setVisible(false);
        }
    }
    public void switchToGamesScene() throws IOException {
    }
    public void switchToYearsScene() throws IOException {
    }
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }

    public void updateUsername() throws IOException {
        String newUsername = usernameField.getText();
        try {
            if (DataBaseUtil.updateUsername(user.getId(), newUsername)) {
                user.setUsername(newUsername); // Обновляем информацию в текущей сессии
                CurrentUser.getInstance().logIn(user); // Обновляем пользователя в синглтоне
                CurrentUser.getInstance().saveCurrentUser(); // Обновляем данные в файле сериализации
                System.out.println("Username successfully updated!");
            } else {
                System.out.println("Failed to update username!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword() throws IOException {
        String currentPassword = CurrentPasswordField.getText();
        String newPassword = NewPasswordField.getText();
        String confirmPassword = ConfirmPasswordField.getText();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("The new passwords do not match!");
            return;
        }

        if (!user.getPassword().equals(currentPassword)) {
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
                System.out.println("Failed to update password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void signOut() throws IOException {
        CurrentUser.getInstance().logOut(); // Очистка информации о текущем пользователе
        CurrentUser.getInstance().clearCurrentUser(); // Удаление файла сериализации
        SceneController.getInstance().switchScene("login.fxml"); // Переход на экран логина
        System.out.println("User signed out!");
    }

}
