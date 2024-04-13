package aggregation;

import session.*;
import utils.*;
import users.*;

public class AggregateIncome {
    public static void updateCriticBalance(int reviewLength) {
        Critic critic = (Critic) CurrentUser.getInstance().getUser();
        double income = reviewLength * 0.1;
        double newBalance = critic.getBalance() + income;
        try {
            if (DataBaseUtil.updateBalance(critic.getId(), newBalance)) {
                System.out.println("Сritics balance updated successfully!");
                critic.setBalance(newBalance);
            } else {
                System.err.println("Failed to update critic's balance.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
