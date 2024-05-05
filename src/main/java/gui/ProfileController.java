package gui;

import aggregation.*;
import gui_interfaces.*;
import logger_decorator.*;
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

/**
 * Controller class responsible for managing user profile. Includes handling
 * user information updates, password changes, signing out, and triggering specific actions
 * like generating awards or transferring funds, depending on user's role.
 */
public class ProfileController implements FieldInterface, ToolBarInterface {
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

    /**
     * Initializes profile page by setting up user-dependent gui elements.
     */
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

    /**
     * Updates username after validating it against system rules.
     */
    @FXML
    private void updateUsername() {
        String newUsername = usernameField.getText();
        setUsernameNormalStyle();

        ValidationRule usernameRule = ValidationRuleFactory.getRule("username");

        Logger logger = new UserDataLogger(new TimeLogger(new FileLogger()), newUsername, user.getPassword());

        // Username failed validation_factory
        if (!usernameRule.validate(newUsername)) {
            setUsernameErrorStyle();

            AlertUtil.showAlert("Invalid Username", usernameRule.getErrorMessage(), Alert.AlertType.ERROR);
            logger.log("Invalid attempt for username", LoggerLevel.ERROR);
            System.err.println(usernameRule.getErrorMessage());
            return;
        }

        try {
            if (DataBaseUtil.updateUsername(user.getId(), newUsername)) {
                user.setUsername(newUsername); // Updating user information in the current session
                CurrentUser.getInstance().logIn(user); // Updating user in singleton
                CurrentUser.getInstance().saveCurrentUser(); // Updating data in the serialization file

                AlertUtil.showAlert("Update Successful", "Username successfully updated!", Alert.AlertType.INFORMATION);
                logger.log("Username updated", LoggerLevel.INFO);
                System.out.println("Username successfully updated!");
            } else {
                setUsernameErrorStyle(); // Database operation failed

                logger.log("Invalid attempt for username", LoggerLevel.DEBUG);
                AlertUtil.showAlert("Update Failed", "Sorry, there was an error while updating username. Please try again.", Alert.AlertType.ERROR);
                System.err.println("Failed to update username!");
            }
        } catch (SQLException e) {
            setUsernameErrorStyle(); // SQL Exception

            logger.log("Invalid attempt for username", LoggerLevel.DEBUG);
            System.err.println("Failed to update username!");
            e.printStackTrace();
        }
    }

    /**
     * Updates password after validating it against system rules.
     */
    @FXML
    private void updatePassword() {
        String currentPassword = CurrentPasswordField.getText();
        String newPassword = NewPasswordField.getText();
        String confirmPassword = ConfirmPasswordField.getText();

        setPasswordNormalStyle();

        ValidationRule passwordRule = ValidationRuleFactory.getRule("password");

        Logger logger = new UserDataLogger(new TimeLogger(new FileLogger()), user.getUsername(), newPassword);

        // Password does not meet the requirements
        if (!passwordRule.validate(newPassword)) {
            setPasswordErrorStyle();

            AlertUtil.showAlert("Invalid Password", passwordRule.getErrorMessage(), Alert.AlertType.ERROR);
            logger.log("Invalid attempt for password", LoggerLevel.ERROR);
            System.err.println(passwordRule.getErrorMessage());
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            setPasswordErrorStyle();

            AlertUtil.showAlert("Invalid Password", "Sorry, new passwords don't match!", Alert.AlertType.ERROR);
            logger.log("Invalid attempt for password", LoggerLevel.ERROR);
            System.err.println("The new passwords do not match!");
            return;
        }

        if (!user.getPassword().equals(currentPassword)) {
            setPasswordErrorStyle();

            AlertUtil.showAlert("Invalid Password", "Sorry, password don't match current password!", Alert.AlertType.ERROR);
            logger.log("Invalid attempt for password", LoggerLevel.ERROR);
            System.err.println("The current password do not match original password!");
            return;
        }

        try {
            if (DataBaseUtil.updatePassword(user.getId(), newPassword)) {
                user.setPassword(newPassword); // Update password in the current session
                CurrentUser.getInstance().logIn(user); // Updating user in singleton
                CurrentUser.getInstance().saveCurrentUser(); // Updating data in the serialization file

                AlertUtil.showAlert("Update Successful", "Password successfully updated!", Alert.AlertType.INFORMATION);
                logger.log("Password updated", LoggerLevel.INFO);
                System.out.println("Password successfully updated!");

                CurrentPasswordField.clear();
                NewPasswordField.clear();
                ConfirmPasswordField.clear();
            } else {
                setPasswordErrorStyle();

                AlertUtil.showAlert("Update Failed", "Sorry, there was an error while updating password. Please try again.", Alert.AlertType.ERROR);
                logger.log("Problem in updating password", LoggerLevel.DEBUG);
                System.err.println("Failed to update password!");
            }
        } catch (SQLException e) {
            setPasswordErrorStyle();

            logger.log("Problem in updating password", LoggerLevel.DEBUG);
            System.err.println("Failed to update password!");
            e.printStackTrace();
        }
    }

    /**
     * Signs out current user and switches to the login screen.
     */
    @FXML
    private void signOut() throws IOException {
        Logger logger = new UserDataLogger(new TimeLogger(new FileLogger()), user.getUsername(), user.getPassword());

        CurrentUser.getInstance().logOut(); // Clearing information about the current user
        CurrentUser.getInstance().clearCurrentUser(); // Deleting a serialization file

        logger.log("Signed out user", LoggerLevel.INFO);
        System.out.println("User signed out!");

        SceneController.getInstance().switchScene("login.fxml"); // Switching to the login screen
    }

    /**
     * Handles generating awards if user is an {@link Administrator}.
     */
    @FXML
    private void handleGenerateAwards() {
        Logger logger = new TimeLogger(new FileLogger());
        try {
            ((Administrator)user).uploadGameAwardsThisYear();

            AlertUtil.showAlert("Generation Successful", "Awards successfully generated!", Alert.AlertType.INFORMATION);
            logger.log("Awards generated",LoggerLevel.INFO);
            System.out.println("Awards successfully generated!");
        } catch (Exception e) {

            AlertUtil.showAlert("Generation Failed", "Sorry, there was an error while generating awards. Please try again.", Alert.AlertType.ERROR);
            logger.log("Problem in generating awards",LoggerLevel.DEBUG);
            System.err.println("Failed to generate awards!");
            e.printStackTrace();
        }
    }

    /**
     * Handles fund transfers if the user is a {@link Critic}.
     */
    @FXML
    private void handleTransferFunds() {
        if (user instanceof Critic critic) {
            if (critic.getBalance() > 0) {
                boolean updateSuccessful = AggregateFunds.aggregateTransfer();

                if (updateSuccessful) {
                    balanceValueLabel.setText("0,00 $");

                    AlertUtil.showAlert("Transfer Successful", "Funds transferred to your bank account!", Alert.AlertType.INFORMATION);
                    System.out.println("Transfer Successful!");
                }
            } else {

                AlertUtil.showAlert("Transfer Failed", "Sorry, insufficient funds for transfer.", Alert.AlertType.ERROR);
                System.err.println("Transfer Failed!");
            }
        }
    }

    @Override
    public void setUsernameErrorStyle(){
        usernameField.setStyle(errorFieldStyle);
    }

    @Override
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

    @FXML
    public void switchToProcessGameScene() throws IOException {
        SceneController.getInstance().switchScene("process_game.fxml");
    }

    @Override
    public void switchToProfileScene() throws IOException {

    }
    @Override
    public void switchToGamesScene() throws IOException {
        SceneController.getInstance().switchScene("games.fxml");
    }
    @Override
    public void switchToYearsScene() throws IOException {
        SceneController.getInstance().switchScene("years.fxml");
    }
    @Override
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }
}
