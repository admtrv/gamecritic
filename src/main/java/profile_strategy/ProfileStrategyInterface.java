package profile_strategy;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

/**
 * Defines the strategy interface for configuring gui components based on the users role.
 * This strategy pattern allows for dynamic changes in the user interface of a profile, adapting it to different user types.
 */
public interface ProfileStrategyInterface {
    /**
     * Sets the interface elements specific to  users profile strategy.
     * This method configures labels, lines, and buttons according to the needs of different user roles.
     * @param balanceValueLabel      label to display balance information
     * @param additionalTextLabel    label for displaying additional text related to user role
     * @param additionalLine         line that separate sections
     * @param generateAwardsButton   button for triggering awards generation for administrator
     * @param transferFundsButton    button for initiating funds transfer for critic
     * @param addNewGameButton       button for adding new games for administrator
     */
    void setInterface(Label balanceValueLabel, Label additionalTextLabel, Line additionalLine, Button generateAwardsButton, Button transferFundsButton, Button addNewGameButton);
}
