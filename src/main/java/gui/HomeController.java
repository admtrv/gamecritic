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
import javafx.scene.Cursor;
public class HomeController implements StyleInterface {
    private List<Game> latestGames;
    private List<Game> topGames;
    @FXML private HBox LatestGamesContainer;
    @FXML private HBox TopGamesContainer;
    public void initialize() throws IOException {
        try {
            latestGames = DataBaseUtil.getGames(6, "release_date");
            topGames = DataBaseUtil.getGames(6, "average_score");
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
            gameBox.setStyle("-fx-background-color: " + BoxBackgroundColor + " -fx-background-radius: 10");

            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(game.getImagePath())));
            imageView.setFitHeight(160); // Высота : Ширина = 15 : 10
            imageView.setFitWidth(110);

            Label titleLabel = new Label(game.getTitle());
            titleLabel.setFont(new Font("ProximaNova-ExtraBold", 14));
            titleLabel.setWrapText(true);

            Label scoreLabel = new Label(String.format("%.1f",game.getAverageScore()));
            scoreLabel.setFont(new Font("ProximaNova-ExtraBold", 12));
            scoreLabel.setStyle(getScoreColor(game.getAverageScore()) + "-fx-background-radius: 8;");
            scoreLabel.setAlignment(Pos.CENTER);
            scoreLabel.setMinWidth(30);
            scoreLabel.setMinHeight(30);

            Label developerLabel = new Label(game.getDeveloper());
            developerLabel.setFont(new Font("Proxima Nova Regular", 14));

            gameBox.getChildren().addAll(imageView, titleLabel, scoreLabel, developerLabel);
            HBox.setMargin(gameBox, new Insets(0, 7, 0, 0));
            gameBox.setCursor(Cursor.HAND);
            gameBox.setOnMouseClicked(e -> {
                try {
                    CurrentGame.getInstance().setGame(game);
                    SceneController.getInstance().switchScene("game_details.fxml");
                } catch (IOException f) {
                    System.err.println("Error switching scene!");
                    f.printStackTrace();
                }
            });
            gamesContainer.getChildren().add(gameBox);
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
