package gui;

import aggregation.*;
import session.*;
import users.*;
import utils.*;
import validation_factory.*;
import profile_strategy.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import session.CurrentUser;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.shape.Line;
public class ProfileController implements FieldInterface{
    @FXML private TextField usernameField;
    @FXML private PasswordField CurrentPasswordField;
    @FXML private PasswordField NewPasswordField;
    @FXML private PasswordField ConfirmPasswordField;
    @FXML private Label additionalTextLabel;
    @FXML private Line additionalLine;
    @FXML private Label balanceValueLabel;
    @FXML private Button generateAwardsButton;
    @FXML private Button transferFundsButton;
    @FXML private Button addNewGameButton;
    private ProfileStrategyInterface profileStrategy;
    User user = CurrentUser.getInstance().getUser();
    @FXML
    private void initialize() {
        usernameField.setText(user.getUsername());

        if (user instanceof Critic) {
            profileStrategy = new CriticStrategy();
        } else if (user instanceof Administrator) {
            profileStrategy = new AdministratorStrategy();
        } else {
            profileStrategy = new UserStrategy();
        }

        profileStrategy.setInterface(balanceValueLabel, additionalTextLabel, additionalLine, generateAwardsButton, transferFundsButton, addNewGameButton);
    }

    public void updateUsername() {
        String newUsername = usernameField.getText();
        setUsernameNormalStyle();
        ValidationRule usernameRule = ValidationRuleFactory.getRule("username");

        if (!usernameRule.validate(newUsername)) { // Username failed validation_factory
            setUsernameErrorStyle();
            AlertUtil.showAlert("Invalid Username", usernameRule.getErrorMessage(), Alert.AlertType.ERROR);
            System.out.println(usernameRule.getErrorMessage());
            return;
        }

        try {
            if (DataBaseUtil.updateUsername(user.getId(), newUsername)) {
                user.setUsername(newUsername); // Updating user information in the current session
                CurrentUser.getInstance().logIn(user); // Updating user in singleton
                CurrentUser.getInstance().saveCurrentUser(); // Updating data in the serialization file
                AlertUtil.showAlert("Update Successful", "Username successfully updated!", Alert.AlertType.INFORMATION);
                System.out.println("Username successfully updated!");
            } else {
                setUsernameErrorStyle(); // Database operation failed
                AlertUtil.showAlert("Update Failed", "Sorry, there was an error while updating username. Please try again.", Alert.AlertType.ERROR);
                System.out.println("Failed to update username!");
            }
        } catch (SQLException e) {
            setUsernameErrorStyle(); // SQL Exception
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

        if (!passwordRule.validate(newPassword)) { // Password does not meet the requirements
            setPasswordErrorStyle();
            AlertUtil.showAlert("Invalid Password", passwordRule.getErrorMessage(), Alert.AlertType.ERROR);
            System.out.println(passwordRule.getErrorMessage());
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            setPasswordErrorStyle();
            AlertUtil.showAlert("Invalid Password", "Sorry, new passwords don't match!", Alert.AlertType.ERROR);
            System.out.println("The new passwords do not match!");
            return;
        }

        if (!user.getPassword().equals(currentPassword)) {
            setPasswordErrorStyle();
            AlertUtil.showAlert("Invalid Password", "Sorry, password don't match current password!", Alert.AlertType.ERROR);
            System.out.println("The current password do not match original password!");
            return;
        }

        try {
            if (DataBaseUtil.updatePassword(user.getId(), newPassword)) {
                user.setPassword(newPassword); // Update password in the current session
                CurrentUser.getInstance().logIn(user); // Updating user in singleton
                CurrentUser.getInstance().saveCurrentUser(); // Updating data in the serialization file
                AlertUtil.showAlert("Update Successful", "Password successfully updated!", Alert.AlertType.INFORMATION);
                System.out.println("Password successfully updated!");
                CurrentPasswordField.clear();
                NewPasswordField.clear();
                ConfirmPasswordField.clear();
            } else {
                setPasswordErrorStyle();
                AlertUtil.showAlert("Update Failed", "Sorry, there was an error while updating password. Please try again.", Alert.AlertType.ERROR);
                System.out.println("Failed to update password!");
            }
        } catch (SQLException e) {
            setPasswordErrorStyle();
            System.err.println("SQL error updating password!");
            e.printStackTrace();
        }
    }

    public void signOut() throws IOException {
        CurrentUser.getInstance().logOut(); // Clearing information about the current user
        CurrentUser.getInstance().clearCurrentUser(); // Deleting a serialization file
        SceneController.getInstance().switchScene("login.fxml"); // Switching to the login screen
        System.out.println("User signed out!");
    }

    @FXML
    private void handleGenerateAwards() {
        try {
            ((Administrator)user).uploadGameAwardsThisYear();
            AlertUtil.showAlert("Generation Successful", "Awards successfully generated!", Alert.AlertType.INFORMATION);
            System.out.println("Awards successfully generated!");
        } catch (Exception e) {
            AlertUtil.showAlert("Generation Failed", "Sorry, there was an error while generating awards. Please try again.", Alert.AlertType.ERROR);
            System.err.println("Failed to generate awards!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTransferFunds() {
        if (user instanceof Critic critic) {
            if (critic.getBalance() > 0) {
                boolean updateSuccessful = AggregateFunds.aggregateTransfer();

                if (updateSuccessful) {
                    balanceValueLabel.setText("0,00 $");
                    AlertUtil.showAlert("Transfer Successful", "Funds transferred to your bank account!", Alert.AlertType.INFORMATION);
                }

            } else {
                AlertUtil.showAlert("Transfer Failed", "Sorry, insufficient funds for transfer.", Alert.AlertType.ERROR);
            }
        }
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


    public void switchToProcessGameScene() throws IOException {
        SceneController.getInstance().switchScene("process_game.fxml");
    }
    public void switchToGamesScene() throws IOException {
        SceneController.getInstance().switchScene("games.fxml");
    }
    public void switchToYearsScene() throws IOException {
        SceneController.getInstance().switchScene("years.fxml");
    }
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }
}
