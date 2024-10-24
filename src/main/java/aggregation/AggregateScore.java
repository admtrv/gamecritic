package aggregation;

import game.*;
import reviews.*;
import users.*;
import session.*;
import utils.*;
import logger_decorator.*;

/**
 * Provides utility class for recalculating and updating scores of the game
 * based on new or updated reviews from users. This class handles the
 * aggregation of scores and recalculates the overall averages, being one
 * of the most important classes in the application logic of vote counting.
 */
public class AggregateScore {

    /**
     * This method adjusts totals, recalculates averages, and updates game data in the database.
     * @param newScore score to update in the review
     */
    public static void updateScore(int newScore) {

        Logger logger = new TimeLogger(new FileLogger());

        Game game = CurrentGame.getInstance().getGame();
        User user = CurrentUser.getInstance().getUser();
        Review review = CurrentReview.getInstance().getReview();

        if (review != null) {
            // Updating an existing review
            int oldScore = review.getScore();
            int scoreDifference = newScore - oldScore;

            if (user instanceof Critic) {
                game.setCriticsSum(game.getCriticsSum() + scoreDifference);
            } else {
                game.setUsersSum(game.getUsersSum() + scoreDifference);
            }
        } else {
            // Adding a new review
            if (user instanceof Critic) {
                game.setCriticsSum(game.getCriticsSum() + newScore);
                game.setCriticsCount(game.getCriticsCount() + 1);
            } else {
                game.setUsersSum(game.getUsersSum() + newScore);
                game.setUsersCount(game.getUsersCount() + 1);
            }
        }

        // Calculating new scores
        game.setCriticsScore(game.getCriticsCount() > 0 ? (double) game.getCriticsSum() / game.getCriticsCount() : 0);
        game.setUsersScore(game.getUsersCount() > 0 ? (double) game.getUsersSum() / game.getUsersCount() : 0);
        game.setAverageScore((game.getCriticsSum() + game.getUsersSum()) / (double) (game.getCriticsCount() + game.getUsersCount()));

        // Refresh the game in the current session
        CurrentGame.getInstance().setGame(game);
        // Refresh the game in the database
        try {
            DataBaseUtil.updateGameScore(game);

            logger.log("Game score recalculated: [" + game.getTitle() + "]", LoggerLevel.INFO);
            System.out.println("Game score recalculated successfully!");
        } catch (Exception e) {

            logger.log("Problem recalculating game score: [" + game.getTitle() + "]", LoggerLevel.DEBUG);
            System.err.println("Error updating game!");
            e.printStackTrace();
        }
    }
}
