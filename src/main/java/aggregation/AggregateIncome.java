package aggregation;

import utils.*;
import users.*;

public class AggregateIncome {
    public static void updateCriticBalance(Critic critic, double income) {
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
