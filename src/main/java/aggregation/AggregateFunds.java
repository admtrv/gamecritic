package aggregation;

import session.*;
import utils.*;
import users.*;

import java.sql.SQLException;

public class AggregateFunds {
    public static void aggregateIncome(int reviewLength) {
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

    public static boolean aggregateTransfer() {
        Critic critic = (Critic) CurrentUser.getInstance().getUser();
        try {
            boolean updateSuccessful = DataBaseUtil.updateBalance(critic.getId(), 0);
            if (updateSuccessful) {
                critic.setBalance(0);
                CurrentUser.getInstance().saveCurrentUser();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
