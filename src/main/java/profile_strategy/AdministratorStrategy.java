package profile_strategy;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class AdministratorStrategy implements ProfileStrategyInterface {
    @Override
    public void setInterface(Label balanceValueLabel, Label additionalTextLabel, Line additionalLine, Button generateAwardsButton, Button transferFundsButton) {
        additionalTextLabel.setVisible(true);
        additionalTextLabel.setText("Administrator panel");
        additionalLine.setVisible(true);
        generateAwardsButton.setVisible(true);
        balanceValueLabel.setVisible(false);
        transferFundsButton.setVisible(false);
    }
}
