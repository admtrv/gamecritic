package database;

import review.*;
import users.*;
import game.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtil {
    private static final String URL = "jdbc:sqlite:src/main/resources/database.db";

    //Устанавливает соединение с базой данных
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    //Добавляет нового пользователя в базу данных
    public static void addUser(String username, String password, String userType) throws SQLException {
        String sql = "INSERT INTO users(username, password, userType) VALUES(?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType);
            preparedStatement.executeUpdate();
        }
    }

    //Проверяет, существует ли пользователь с заданным именем пользователя
    public static User getUser(String username) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String userType = resultSet.getString("userType");
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

    // Обновление имени пользователя
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

    // Обновление пароля пользователя
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

    // Overloading
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

    public static Review getReview(int userId, int gameId) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE user_id = ? AND game_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, gameId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Review(
                        resultSet.getInt("id"),
                        resultSet.getInt("game_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("score"),
                        resultSet.getString("review_text"),
                        resultSet.getString("publish_date")
                );
            }
        }
        return null;
    }

    public static void addReview(int gameId, int userId, int score, String reviewText, String publishDate) throws SQLException {
        String sql = "INSERT INTO reviews (game_id, user_id, score, review_text, publish_date) VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT(user_id, game_id) DO UPDATE SET score = excluded.score, review_text = excluded.review_text, publish_date = excluded.publish_date";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, score);
            preparedStatement.setString(4, reviewText);
            preparedStatement.setString(5, publishDate);
            preparedStatement.executeUpdate();
        }
    }

    // Метод для получения списка игр за определенный год, сгруппированных по жанрам
    public static void uploadGameAwardsByYear(String year) throws SQLException {
        // Получаем лучшие игры за год и сразу назначаем им награды
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
        }
    }

    public static List<String> getYears() throws SQLException {
        List<String> years = new ArrayList<>();
        String sql = "SELECT DISTINCT strftime('%Y', release_date) AS year FROM games WHERE award = true ORDER BY year DESC;";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String year = resultSet.getString("year");
                years.add(year);
            }
        }
        return years;
    }
}