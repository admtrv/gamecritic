package profile_strategy;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class UserStrategy implements ProfileStrategyInterface {
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
