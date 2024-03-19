package gui;

import database.*;
import game.*;
import session.*;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomeController {
    private List<Game> latestGames;
    private List<Game> topGames;
    @FXML private HBox LatestGamesContainer;
    @FXML private HBox TopGamesContainer;
    public void initialize() throws IOException {
        try {
            latestGames = DataBaseUtil.getLastGames(5);
            topGames = DataBaseUtil.getTopGames(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayGames(LatestGamesContainer, latestGames);
        displayGames(TopGamesContainer, topGames);
    }
    private void displayGames(HBox gamesContainer, List<Game> games) {
        gamesContainer.getChildren().clear();

        for (Game game : games) {
            VBox gameBox = new VBox(5);
            gameBox.setPadding(new Insets(10));
            gameBox.setStyle("-fx-background-color: #fafafa; -fx-background-radius: 10");

            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(game.getImagePath())));
            imageView.setFitHeight(160); // Высота : Ширина = 15 : 10
            imageView.setFitWidth(110);

            Label titleLabel = new Label(game.getTitle());
            titleLabel.setFont(new Font("ProximaNova-ExtraBold", 14));
            titleLabel.setWrapText(true);

            Label scoreLabel = new Label(Double.toString(game.getAverageScore()));
            scoreLabel.setFont(new Font("ProximaNova-ExtraBold", 14));
            scoreLabel.setStyle(getScoreColor(game.getAverageScore()));
            scoreLabel.setAlignment(Pos.CENTER);
            scoreLabel.setMinWidth(30);
            scoreLabel.setMinHeight(30);

            Label developerLabel = new Label(game.getDeveloper());
            developerLabel.setFont(new Font("Proxima Nova Regular", 14));

            gameBox.getChildren().addAll(imageView, titleLabel, scoreLabel, developerLabel);
            HBox.setMargin(gameBox, new Insets(0, 45, 0, 0));
            gameBox.setOnMouseClicked(e -> {
                try {
                    CurrentGame.getInstance().setGame(game);
                    SceneController.getInstance().switchScene("game_details.fxml");
                } catch (IOException f) {
                    f.printStackTrace();
                }
            });
            gamesContainer.getChildren().add(gameBox);
        }
    }

    private String getScoreColor(double score) {
        if (score >= 8) {
            return "-fx-background-color: #00ce7a; -fx-background-radius: 8; -fx-text-fill: black;"; // Зеленый
        } else if (score >= 5) {
            return "-fx-background-color: #ffbd3f; -fx-background-radius: 8; -fx-text-fill: black;"; // Желтый
        } else {
            return "-fx-background-color: #ff6874; -fx-background-radius: 8; -fx-text-fill: black;"; // Красный
        }
    }

    public void switchToGamesScene() throws IOException {
    }
    public void switchToYearsScene() throws IOException {
    }
    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }
}
