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
public class YearDetailsController implements StyleInterface {
    @FXML private Label mainLabel = new Label();
    @FXML private VBox gamesContainer;
    private List<Game> games;
    String year = CurrentYear.getInstance().getYear();
    @FXML
    public void initialize(){
        mainLabel.setText("Game of the Year " + year + " Awards");

        try {
           games = DataBaseUtil.getGames(year, "release_date");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        displayGames(gamesContainer, games);
    }

    @FXML
    private void displayGames(VBox gamesContainer, List<Game> games) {
        gamesContainer.setSpacing(15);
        gamesContainer.setAlignment(Pos.TOP_CENTER);
        gamesContainer.getChildren().clear();

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

    public void switchToGamesScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("games.fxml");
    }
    public void switchToYearsScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("years.fxml");
    }
    public void switchToProfileScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("profile.fxml");
    }

    public void switchToHomeScene() throws IOException {
        CurrentYear.getInstance().resetYear();
        SceneController.getInstance().switchScene("home.fxml");
    }
}
