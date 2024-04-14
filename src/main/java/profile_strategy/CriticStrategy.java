package profile_strategy;

import session.*;
import users.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class CriticStrategy implements ProfileStrategyInterface {
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
