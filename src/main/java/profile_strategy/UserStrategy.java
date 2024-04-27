package profile_strategy;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

/**
 * This strategy hides elements that are not relevant to regular user, ensuring that the user interface
 * remains clean and relevant to the functionalities available to standard user.
 */
public class UserStrategy implements ProfileStrategyInterface {

    /**
     * Configures gui components specific to standard user by hiding administrators and critics controls.
     * @param balanceValueLabel      label to display balance information, hidden
     * @param additionalTextLabel    label for displaying additional text related to user role, hidden
     * @param additionalLine         line that separate sections, hidden
     * @param generateAwardsButton   button for triggering awards generation for administrator, hidden
     * @param transferFundsButton    button for initiating funds transfer for critic, hidden
     * @param addNewGameButton       button for adding new games for administrator, hidden
     */
    @Override
    public void setInterface(Label balanceValueLabel, Label additionalTextLabel, Line additionalLine, Button generateAwardsButton, Button transferFundsButton, Button addNewGameButton) {
        balanceValueLabel.setVisible(false);

        additionalTextLabel.setVisible(false);
        additionalLine.setVisible(false);

        generateAwardsButton.setVisible(false);

        transferFundsButton.setVisible(false);

        addNewGameButton.setVisible(false);
    }
}
