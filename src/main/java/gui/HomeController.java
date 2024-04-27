package gui;

import game.*;
import gui_interfaces.*;
import session.*;
import utils.*;

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

/**
 * Controller for managing display of games on the home screen of the application.
 * This class handles the layout and population of the latest and top games in their containers.
 * Each game is displayed in a styled card format that includes an image, title, score, and developer information.
 */
public class HomeController implements StyleInterface, ToolBarInterface {
    private List<Game> latestGames;
    private List<Game> topGames;

    @FXML private HBox LatestGamesContainer;
    @FXML private HBox TopGamesContainer;

    /**
     * Initializes the controller by getting and displaying latest and top-rated games.
     * Games got based on their release dates and scores respectively.
     */
    @FXML
    public void initialize(){
        try {
            latestGames = DataBaseUtil.getGames(15, "release_date");
            topGames = DataBaseUtil.getGames(15, "average_score");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayGames(LatestGamesContainer, latestGames);
        displayGames(TopGamesContainer, topGames);
    }

    /**
     * Fills the specified container with game cards created from game list.
     * Each game card is interactive and provides quick access to detailed game views when clicked.
     *
     * @param gamesContainer a container to fill with game cards
     * @param games list of games to display in the container
     */
    @FXML
    private void displayGames(HBox gamesContainer, List<Game> games) {
        gamesContainer.getChildren().clear();

        for (Game game : games) {
            VBox gameBox = new VBox(10);
            gameBox.setPadding(new Insets(10));
            gameBox.setStyle("-fx-background-color: " + BoxBackgroundColor + " -fx-background-radius: 10");

            // Image
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(game.getImagePath())));
            imageView.setFitHeight(160); // Height : Width = 15 : 10
            imageView.setFitWidth(110);

            // Title
            Label titleLabel = new Label(game.getTitle());
            titleLabel.setFont(new Font("ProximaNova-ExtraBold", 14));
            titleLabel.setWrapText(true);

            // Score
            Label scoreLabel = new Label(String.format("%.1f",game.getAverageScore()));
            scoreLabel.setFont(new Font("ProximaNova-ExtraBold", 12));
            scoreLabel.setStyle(getScoreColor(game.getAverageScore()) + "-fx-background-radius: 8;");
            scoreLabel.setAlignment(Pos.CENTER);
            scoreLabel.setMinWidth(30);
            scoreLabel.setMinHeight(30);

            // Developer
            Label developerLabel = new Label(game.getDeveloper());
            developerLabel.setFont(new Font("Proxima Nova Regular", 14));

            // Adding everything to container
            gameBox.getChildren().addAll(imageView, titleLabel, scoreLabel, developerLabel);
            gamesContainer.setMargin(gameBox, new Insets(0, 7, 0, 0));

            // Adding action on click
            gameBox.setCursor(Cursor.HAND);
            gameBox.setOnMouseClicked(event -> {
                try {
                    CurrentGame.getInstance().setGame(game);
                    SceneController.getInstance().switchScene("game_view.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            gamesContainer.getChildren().add(gameBox);
        }
    }

    @Override
    public void switchToHomeScene() throws IOException {

    }
    @Override
    public void switchToGamesScene() throws IOException {
        SceneController.getInstance().switchScene("games.fxml");
    }
    @Override
    public void switchToYearsScene() throws IOException {
        SceneController.getInstance().switchScene("years.fxml");
    }
    @Override
    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }


}
