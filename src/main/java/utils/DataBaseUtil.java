package utils;

import reviews.*;
import session.*;
import users.*;
import game.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing all database interactions within the application.
 * This class contains methods to handle database operations for users, games, and reviews,
 * as well as specific utility functions for managing game awards and critics balances.
 */
public class DataBaseUtil {
    private static final String URL = "jdbc:sqlite:src/main/resources/database.db";

    /**
     * Setting up connection to the database.
     * @return connection object to the database
     * @throws SQLException if database error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Adds new user to the database.
     * @param username users username
     * @param password users password
     * @param userType type of user (User, Critic, Administrator)
     * @throws SQLException if database error occurs
     */
    public static void addUser(String username, String password, String userType) throws SQLException {
        String sql = "INSERT INTO users(username, password, user_type) VALUES(?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Verifying whether user with given username exists.
     * @param username username of user to verify
     * @return user object containing the users data
     * @throws SQLException if database error occurs
     */
    public static User getUser(String username) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String userType = resultSet.getString("user_type");
                int id = resultSet.getInt("id");
                double balance = resultSet.getDouble("balance");
                String password = resultSet.getString("password");

                switch (userType) {
                    case "User":
                        user = new User(id, username, password);
                        break;
                    case "Critic":
                        user = new Critic(id, username, password, balance);
                        break;
                    case "Administrator":
                        user = new Administrator(id, username, password);
                        break;
                }
            }
        }

        return user;
    }

    /**
     * Verifying whether user with given username exists.
     * @param id id of user to verify
     * @return user object containing the users data
     * @throws SQLException if database error occurs
     */
    public static User getUser(int id) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String userType = resultSet.getString("user_type");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                double balance = resultSet.getDouble("balance");

                switch (userType) {
                    case "User":
                        user = new User(id, username, password);
                        break;
                    case "Critic":
                        user = new Critic(id, username, password, balance);
                        break;
                    case "Administrator":
                        user = new Administrator(id, username, password);
                        break;
                }
            }
        }

        return user;
    }

    /**
     * Updates users username in the database.
     * @param userId id of user whose username is to be updated
     * @param newUsername new username to update
     * @return true if update was successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public static boolean updateUsername(int userId, String newUsername) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newUsername);
            preparedStatement.setInt(2, userId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Updates users password in the database.
     *
     * @param userId id of user whose password is to be updated
     * @param newPassword new password to update
     * @return true if update was successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public static boolean updatePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Updates balance in the database, if user is {@link Critic}
     * @param userId id of user whose balance is to be updated
     * @param newBalance new balance to update
     * @return true if update was successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public static boolean updateBalance(int userId, double newBalance) throws SQLException {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, userId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Adds new game to the database.
     * @param title title of game
     * @param developer developer of game
     * @param releaseDate release date of game
     * @param platforms platforms on which game is available
     * @param genre genre of game
     * @param storeLink link to store where game can be purchased
     * @param description description of game
     * @param imagePath path to cover image of game
     * @return true if game was successfully added, false otherwise
     * @throws SQLException if database error occurs
     */
    public static boolean addGame(String title, String developer, String releaseDate, String platforms, String genre, String storeLink, String description, String imagePath) throws SQLException {
        String sql = "INSERT INTO games (title, developer, release_date, platforms, genre, store_link, description, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, developer);
            preparedStatement.setString(3, releaseDate);
            preparedStatement.setString(4, platforms);
            preparedStatement.setString(5, genre);
            preparedStatement.setString(6, storeLink);
            preparedStatement.setString(7, description);
            preparedStatement.setString(8, imagePath);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Retrieves list of games from the database based on specified sorting attribute and limited by number.
     * @param number maximum number of games to retrieve
     * @param attribute  attribute by which games need to be sorted (e.g. release_date)
     * @return list of game objects
     * @throws SQLException if database error occurs
     */
    public static List<Game> getGames(int number, String attribute) throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games ORDER BY " + attribute + " DESC LIMIT ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, number);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(new Game(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("developer"),
                            resultSet.getString("release_date"),
                            resultSet.getString("platforms"),
                            resultSet.getString("genre"),
                            resultSet.getBoolean("award"),
                            resultSet.getString("store_link"),
                            resultSet.getString("description"),
                            resultSet.getString("image_path"),
                            resultSet.getInt("critics_count"),
                            resultSet.getInt("users_count"),
                            resultSet.getInt("critics_sum"),
                            resultSet.getInt("users_sum"),
                            resultSet.getDouble("critics_score"),
                            resultSet.getDouble("users_score"),
                            resultSet.getDouble("average_score")
                    ));
                }
            }
        }
        return games;
    }

    /**
     * Retrieves a list of games from the database for a specified year, ordered by specified attribute.
     * @param year year for which games are being queried
     * @param attribute attribute by which games need to be sorted (e.g. release_date)
     * @return list of game objects
     * @throws SQLException if database error occurs
     */
    public static List<Game> getGames(String year, String attribute) throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE strftime('%Y', release_date) = ? AND award = 1 ORDER BY " + attribute + " DESC";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                games.add(new Game(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("developer"),
                        resultSet.getString("release_date"),
                        resultSet.getString("platforms"),
                        resultSet.getString("genre"),
                        resultSet.getBoolean("award"),
                        resultSet.getString("store_link"),
                        resultSet.getString("description"),
                        resultSet.getString("image_path"),
                        resultSet.getInt("critics_count"),
                        resultSet.getInt("users_count"),
                        resultSet.getInt("critics_sum"),
                        resultSet.getInt("users_sum"),
                        resultSet.getDouble("critics_score"),
                        resultSet.getDouble("users_score"),
                        resultSet.getDouble("average_score")
                ));
            }
        }
        return games;
    }

    /**
     * Retrieves a list of all games from the database.
     * @return list of game objects
     * @throws SQLException if database error occurs
     */
    public static List<Game> getGames() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                games.add(new Game(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("developer"),
                        resultSet.getString("release_date"),
                        resultSet.getString("platforms"),
                        resultSet.getString("genre"),
                        resultSet.getBoolean("award"),
                        resultSet.getString("store_link"),
                        resultSet.getString("description"),
                        resultSet.getString("image_path"),
                        resultSet.getInt("critics_count"),
                        resultSet.getInt("users_count"),
                        resultSet.getInt("critics_sum"),
                        resultSet.getInt("users_sum"),
                        resultSet.getDouble("critics_score"),
                        resultSet.getDouble("users_score"),
                        resultSet.getDouble("average_score")
                ));
            }
        }
        return games;
    }

    /**
     * Updates game score in the database.
     * @param game game object containing updated scores
     * @throws SQLException if database error occurs
     */
    public static void updateGameScore(Game game) throws SQLException {
        String sql = "UPDATE games SET critics_count = ?, users_count = ?, critics_sum = ?, users_sum = ?, "
                + "critics_score = ?, users_score = ?, average_score = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, game.getCriticsCount());
            preparedStatement.setInt(2, game.getUsersCount());
            preparedStatement.setInt(3, game.getCriticsSum());
            preparedStatement.setInt(4, game.getUsersSum());
            preparedStatement.setDouble(5, game.getCriticsScore());
            preparedStatement.setDouble(6, game.getUsersScore());
            preparedStatement.setDouble(7, game.getAverageScore());
            preparedStatement.setInt(8, game.getId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Retrieves a review for specified game and user from the database.
     * @param userId user id for whom review is being fetched
     * @param gameId game id for which review is being fetched
     * @return review object, either basic {@link Review} or {@link DetailedReview} if user is {@link Critic}
     * @throws SQLException if database error occurs
     */
    public static Review getReview(int userId, int gameId) throws SQLException {
        Review review = null;
        String sql = "SELECT * FROM reviews WHERE user_id = ? AND game_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, gameId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                gameId = resultSet.getInt("game_id");
                userId = resultSet.getInt("user_id");
                int score = resultSet.getInt("score");
                String reviewText = resultSet.getString("review_text");
                String publishDate = resultSet.getString("publish_date");
                String pluses = resultSet.getString("pluses");
                String minuses = resultSet.getString("minuses");

                if (CurrentUser.getInstance().getUser() instanceof Critic) {
                    review = new DetailedReview(id,gameId,userId,score,reviewText,publishDate,pluses,minuses);
                }
                else{
                    review = new Review(id,gameId,userId,score,reviewText,publishDate);
                }
            }
        }
        return review;
    }

    /**
     * Adds or updates review in the database. It updates the review if there is existing entry for the same user and game.
     * @param gameId id of game being reviewed
     * @param userId id of user writing review
     * @param score score given in review
     * @param reviewText text of review
     * @param publishDate date when review is published
     * @param pluses positive aspects of game (only for {@link DetailedReview})
     * @param minuses negative aspects of game ({@link DetailedReview})
     * @throws SQLException if database error occurs
     */
    public static void addReview(int gameId, int userId, int score, String reviewText, String publishDate, String pluses, String minuses) throws SQLException {
        String sql = "INSERT INTO reviews (game_id, user_id, score, review_text, publish_date, pluses, minuses) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT(user_id, game_id) DO UPDATE SET " +
                "score = excluded.score, review_text = excluded.review_text, publish_date = excluded.publish_date, pluses = excluded.pluses, minuses = excluded.minuses";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, score);
            preparedStatement.setString(4, reviewText);
            preparedStatement.setString(5, publishDate);
            preparedStatement.setString(6, pluses);
            preparedStatement.setString(7, minuses);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Retrieves list of detailed reviews for specified game id.
     * @param gameId game id for which detailed reviews are to be fetched
     * @return list of detailed review objects
     * @throws SQLException if database error occurs
     */
    public static List<DetailedReview> getDetailedReviews(int gameId) throws SQLException {
        List<DetailedReview> detailedReviews = new ArrayList<>();

        String sql = "SELECT r.* FROM reviews r JOIN users u ON r.user_id = u.id WHERE u.user_type = 'Critic' AND r.game_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DetailedReview review = new DetailedReview(
                            resultSet.getInt("id"),
                            resultSet.getInt("game_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("score"),
                            resultSet.getString("review_text"),
                            resultSet.getString("publish_date"),
                            resultSet.getString("pluses"),
                            resultSet.getString("minuses")
                    );
                    detailedReviews.add(review);
                }
            }
        }
        return detailedReviews;
    }

    /**
     * Retrieves list of reviews for specified game id.
     * @param gameId game id for which reviews are to be fetched
     * @return list of review objects
     * @throws SQLException if database error occurs
     */
    public static List<Review> getReviews(int gameId) throws SQLException {
        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT r.* FROM reviews r JOIN users u ON r.user_id = u.id WHERE u.user_type <> 'Critic' AND r.game_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review(
                            resultSet.getInt("id"),
                            resultSet.getInt("game_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("score"),
                            resultSet.getString("review_text"),
                            resultSet.getString("publish_date")
                    );
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }


    /**
     * Awards best games of specified year based on their genre. Method updates games awards status
     * to true for those that rank highest in their genre based on average score for given year.
     * @param year year for which awards are to be calculated and assigned
     * @throws SQLException if database error occurs
     */
    public static void uploadGameAwardsByYear(String year) throws SQLException {
        // Getting the best games of the year and giving them awards immediately
        String sql = "WITH ranked_games AS (" +
                "SELECT *, RANK() OVER (PARTITION BY genre ORDER BY average_score DESC) AS rank " +
                "FROM games WHERE strftime('%Y', release_date) = ?" +
                ") " +
                "UPDATE games SET award = true WHERE id IN (" +
                "SELECT id FROM ranked_games WHERE rank = 1" +
                ");";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, year);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Retrieves list of years for which games have been awarded. This method
     * is used for displaying historical awards data.
     * @return list of years when games have received awards
     * @throws SQLException if database error occurs
     */
    public static List<String> getYears() throws SQLException {
        List<String> years = new ArrayList<>();
        String sql = "SELECT DISTINCT strftime('%Y', release_date) AS year FROM games WHERE award = true ORDER BY year DESC;";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String year = resultSet.getString("year");
                years.add(year);
            }
        }
        return years;
    }
}