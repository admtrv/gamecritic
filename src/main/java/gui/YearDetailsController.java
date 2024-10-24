package gui;

import game.*;
import gui_interfaces.*;
import session.*;
import utils.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class responsible for displaying the "Game of the Year" awards
 * for a selected year. It lists games that have received awards within
 * different genres and provides functionality to navigate to detailed
 * game views.
 */
public class YearDetailsController implements StyleInterface, ToolBarInterface {
    @FXML private Label mainLabel = new Label();
    @FXML private VBox gamesContainer;
    private List<Game> games;
    private String year = CurrentYear.getInstance().getYear();

    /**
     * Initializes view by setting main label to the selected year and
     * loading games that received awards in that year.
     */
    @FXML
    private void initialize(){
        mainLabel.setText("Game of the Year " + year + " Awards");

        try {
           games = DataBaseUtil.getGames(year, "release_date");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        displayGames(gamesContainer, games);
    }

    /**
     * Populates given container with visual representations of each game
     * that won an award. Each game is represented by card containing its
     * genre nomination, score, and title.
     * @param gamesContainer container to populate with game entries
     * @param games list of games to display
     */
    @FXML
    private void displayGames(VBox gamesContainer, List<Game> games) {
        gamesContainer.setSpacing(15);
        gamesContainer.setAlignment(Pos.TOP_CENTER);
        gamesContainer.getChildren().clear();
        gamesContainer.setPadding(new Insets(0, 0, 40, 0));

        for (Game game : games) {
            VBox gameBox = new VBox(10);
            VBox.setMargin(gameBox, new Insets(0, 0, 10, 0));
            gameBox.setPadding(new Insets(10));
            gameBox.setAlignment(Pos.CENTER_LEFT);
            gameBox.setCursor(Cursor.HAND);
            gameBox.setStyle("-fx-background-color: " + BoxBackgroundColor + " -fx-background-radius: 10");

            // Nomination
            Label nominationLabel = new Label(game.getGenre() + " of the Year");
            nominationLabel.setFont(new Font("ProximaNova-ExtraBold", 14));

            // Container for score and name of the game
            HBox gameInfoContainer = new HBox(10);
            gameInfoContainer.setAlignment(Pos.CENTER_LEFT);

            // Game score
            Label scoreLabel = new Label(String.valueOf((int) game.getAverageScore()));
            scoreLabel.setFont(new Font("ProximaNova-ExtraBold", 14));
            scoreLabel.setStyle(getScoreColor(game.getAverageScore()) + "-fx-background-radius: 8;");
            scoreLabel.setAlignment(Pos.CENTER);
            scoreLabel.setMinWidth(30);
            scoreLabel.setMinHeight(30);

            // Game title
            Label gameNameLabel = new Label(game.getTitle());
            gameNameLabel.setFont(new Font("ProximaNova-Regular", 12));

            // Collecting the score and the name of the game into a container
            gameInfoContainer.getChildren().addAll(scoreLabel, gameNameLabel);

            // Build everything in one VBox
            gameBox.getChildren().addAll(nominationLabel, gameInfoContainer);

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
    public void switchToGamesScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("games.fxml");
    }
    @Override
    public void switchToYearsScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("years.fxml");
    }
    @Override
    public void switchToProfileScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("profile.fxml");
    }
    @Override
    public void switchToHomeScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("home.fxml");
    }
}
