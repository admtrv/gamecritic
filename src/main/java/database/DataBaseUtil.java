package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import users.*;
import game.*;
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

    //Проверяет, существует ли пользователь с заданным именем пользователя и паролем
    public static User findUser(String username, String password) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String userType = resultSet.getString("userType");
                int id = resultSet.getInt("id");
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

    public static List<Game> getLastGames(int limit) throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games ORDER BY release_date DESC LIMIT ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, limit);

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

    public static List<Game> getTopGames(int limit) throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games ORDER BY average_score DESC LIMIT ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, limit);

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
}
