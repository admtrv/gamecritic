package gui;

import aggregation.AggregateFunds;
import session.CurrentGame;
import session.CurrentReview;
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

        profileStrategy.setInterface(balanceValueLabel, additionalTextLabel, additionalLine, generateAwardsButton, transferFundsButton);
    }

    public void updateUsername() {
        String newUsername = usernameField.getText();
        setUsernameNormalStyle();
        ValidationRule usernameRule = ValidationRuleFactory.getRule("username");

        if (!usernameRule.validate(newUsername)) { // Username failed validation_factory
            setUsernameErrorStyle();
            System.out.println(usernameRule.getErrorMessage());
            return;
        }

        try {
            if (DataBaseUtil.updateUsername(user.getId(), newUsername)) {
                user.setUsername(newUsername); // Updating user information in the current session
                CurrentUser.getInstance().logIn(user); // Updating user in singleton
                CurrentUser.getInstance().saveCurrentUser(); // Updating data in the serialization file
                System.out.println("Username successfully updated!");
            } else {
                setUsernameErrorStyle(); // Database operation failed
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
                user.setPassword(newPassword); // Update password in the current session
                CurrentUser.getInstance().logIn(user); // Updating user in singleton
                CurrentUser.getInstance().saveCurrentUser(); // Updating data in the serialization file
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
        CurrentUser.getInstance().logOut(); // Clearing information about the current user
        CurrentUser.getInstance().clearCurrentUser(); // Deleting a serialization file
        SceneController.getInstance().switchScene("login.fxml"); // Switching to the login screen
        System.out.println("User signed out!");
    }

    @FXML
    private void handleGenerateAwards() {
        try {
            ((Administrator)user).uploadGameAwardsThisYear();
            System.out.println("Awards successfully generated!");
        } catch (Exception e) {
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
                } else {
                    AlertUtil.showAlert("Transfer Failed", "Sorry, something went wrong there. Try again!", Alert.AlertType.ERROR);
                }
            } else {
                AlertUtil.showAlert("Transfer Failed", "Insufficient funds for transfer!", Alert.AlertType.ERROR);
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
