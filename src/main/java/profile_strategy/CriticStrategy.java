package profile_strategy;

import session.*;
import users.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

/**
 * This strategy customizes interface to show controls and information relevant to critic ,
 * such as balance display and the option to transfer funds, while hiding administrative controls.
 */
public class CriticStrategy implements ProfileStrategyInterface {
    /**
     * Configures gui components specific to critic by displaying elements relevant to their functionalities.
     * @param balanceValueLabel      label to display balance information, formatted to show currency
     * @param additionalTextLabel    label for displaying additional text related to user role, set to "Current Balance"
     * @param additionalLine         line that separate sections
     * @param generateAwardsButton   button for triggering awards generation for administrator, hidden
     * @param transferFundsButton    button for initiating funds transfer for critic
     * @param addNewGameButton       button for adding new games for administrator, hidden
     */
    @Override
    public void setInterface(Label balanceValueLabel, Label additionalTextLabel, Line additionalLine, Button generateAwardsButton, Button transferFundsButton, Button addNewGameButton) {
        balanceValueLabel.setVisible(true);
        balanceValueLabel.setText(String.format("%.2f", ((Critic)CurrentUser.getInstance().getUser()).getBalance()) + " $");

        additionalTextLabel.setVisible(true);
        additionalTextLabel.setText("Current Balance");
        additionalLine.setVisible(true);

        generateAwardsButton.setVisible(false);

        transferFundsButton.setVisible(true);

        addNewGameButton.setVisible(false);
    }
}
