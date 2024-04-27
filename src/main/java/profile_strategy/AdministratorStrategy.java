package profile_strategy;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

/**
 * This strategy customizes interface to show controls relevant to administrative functions.
 */
public class AdministratorStrategy implements ProfileStrategyInterface {

    /**
     * Configures gui components specific to administrator by displaying elements relevant
     * to their administrative functions.
     * @param balanceValueLabel      label to display balance information, hidden
     * @param additionalTextLabel    label for displaying additional text related to user role, set to "Administrator panel"
     * @param additionalLine         line that separate sections
     * @param generateAwardsButton   button for triggering awards generation for administrator
     * @param transferFundsButton    button for initiating funds transfer for critic, hidden
     * @param addNewGameButton       button for adding new games for administrator
     */
    @Override
    public void setInterface(Label balanceValueLabel, Label additionalTextLabel, Line additionalLine, Button generateAwardsButton, Button transferFundsButton, Button addNewGameButton) {
        additionalTextLabel.setVisible(true);
        additionalTextLabel.setText("Administrator panel");
        additionalLine.setVisible(true);

        generateAwardsButton.setVisible(true);

        balanceValueLabel.setVisible(false);

        transferFundsButton.setVisible(false);

        addNewGameButton.setVisible(true);
    }
}
