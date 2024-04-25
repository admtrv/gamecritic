package aggregation;

import session.*;
import utils.*;
import users.*;
import logger_decorator.*;

import java.sql.SQLException;

public class AggregateFunds {
    public static void aggregateIncome(int reviewLength) {
        Critic critic = (Critic) CurrentUser.getInstance().getUser();

        Logger logger = new UserDataLogger(new TimeLogger(new FileLogger()), critic.getUsername(), critic.getPassword());

        double income = reviewLength * 0.1;
        double newBalance = critic.getBalance() + income;

        try {
            if (DataBaseUtil.updateBalance(critic.getId(), newBalance)) {
                critic.setBalance(newBalance);

                logger.log("Updated user balance", LoggerLevel.INFO);
                System.out.println("Сritics balance updated successfully!");
            } else {

                logger.log("Problem updating user balance", LoggerLevel.DEBUG);
                System.err.println("Failed to update critic's balance!");
            }
        } catch (Exception e) {

            logger.log("Problem updating user balance", LoggerLevel.DEBUG);
            System.err.println("Failed to update critic's balance!");
            e.printStackTrace();
        }
    }

    public static boolean aggregateTransfer() {
        Critic critic = (Critic) CurrentUser.getInstance().getUser();

        Logger logger = new UserDataLogger(new TimeLogger(new FileLogger()), critic.getUsername(), critic.getPassword());

        try {
            boolean updateSuccessful = DataBaseUtil.updateBalance(critic.getId(), 0);

            if (updateSuccessful) {
                critic.setBalance(0);
                CurrentUser.getInstance().saveCurrentUser();

                logger.log("Updated user balance", LoggerLevel.INFO);
                System.out.println("Сritics balance updated successfully!");
                return true;
            } else {

                logger.log("Problem updating user balance", LoggerLevel.DEBUG);
                System.err.println("Failed to update critic's balance.");
            }
        } catch (SQLException e) {

            logger.log("Problem updating user balance", LoggerLevel.DEBUG);
            System.err.println("Failed to update critic's balance.");
            e.printStackTrace();
        }
        return false;
    }

}
