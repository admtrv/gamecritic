package gui;

import game.*;
import gui_interfaces.*;
import session.*;
import utils.*;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller handles dynamic creation of game cards based on data got from
 * database and manages layout configurations for displaying these cards
 * within a grid. Each game card displays detailed information about the
 * game including title, release date, description, and score.
 */
public class GamesController implements StyleInterface, ToolBarInterface {

    @FXML
    private GridPane gamesGrid;
    private List<Game> allGames;

    /**
     * Initializes games data and gui components when controller is loaded. This
     * method attempts to get all games from database and then displays them.
     */
    @FXML
    public void initialize(){
        try {
            allGames = DataBaseUtil.getGames();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayGames();
    }

    /**
     * Method organizes the layout of game cards within the grid.
     * Fills {@code gamesGrid} with game cards. Each game from list
     * {@code allGames} is represented by a card. Dynamically
     * adjusts number of columns based on number of games.
     */
    @FXML
    private void displayGames() {
        gamesGrid.getChildren().clear();
        gamesGrid.setHgap(30);
        gamesGrid.setVgap(30);

        int column = 0;
        int row = 0;

        for (Game game : allGames) {
            HBox gameCard = createGameCard(game);
            gamesGrid.add(gameCard, column, row);

            column+=1;
            if (column == 3) { // 3 columns in row
                column = 0;
                row+=1;
            }
        }
    }

    /**
     * Creates one game card, that containing details such as image, title,
     * release date, description, score, and developer.
     * @param game game data to fill card
     * @return styled card containing all components
     */
    @FXML
    private HBox createGameCard(Game game) {
        HBox gameBox = new HBox(10);
        gameBox.setPadding(new Insets(10));
        gameBox.setAlignment(Pos.CENTER_LEFT);
        gameBox.setStyle("-fx-background-color: " + BoxBackgroundColor + "; -fx-background-radius: 10;");
        gameBox.setMaxWidth(300);
        gameBox.setMaxHeight(200);

        // Image
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(game.getImagePath())));
        imageView.setFitWidth(90);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(true);

        // Text container
        VBox textContainer = new VBox(5);
        textContainer.setAlignment(Pos.TOP_LEFT);

        // Title
        Label titleLabel = new Label(game.getTitle());
        titleLabel.setFont(new Font("ProximaNova-ExtraBold", 16));
        titleLabel.setWrapText(true);

        // Date
        Label dateLabel = new Label(game.getReleaseDate().toString());
        dateLabel.setFont(new Font("ProximaNova-Regular", 12));

        // Description
        Label descriptionLabel = new Label(game.getDescription());
        descriptionLabel.setFont(new Font("ProximaNova-Regular", 14));
        descriptionLabel.setMaxWidth(200);
        descriptionLabel.setWrapText(true);

        // Score and studio container
        HBox horizontalContainer = new HBox(10);
        horizontalContainer.setAlignment(Pos.CENTER_LEFT);

        // Score
        Label scoreLabel = new Label(String.format("%.1f",game.getAverageScore()));
        scoreLabel.setFont(new Font("ProximaNova-ExtraBold", 12));
        scoreLabel.setStyle(getScoreColor(game.getAverageScore()) + "-fx-background-radius: 8;");
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setMinWidth(30);
        scoreLabel.setMinHeight(30);

        // Studio
        Label studioLabel = new Label(game.getDeveloper());
        studioLabel.setFont(new Font("ProximaNova-Regular", 12));

        // Adding everything to horizontal container
        horizontalContainer.getChildren().addAll(scoreLabel, studioLabel);

        // Adding everything to text container
        textContainer.getChildren().addAll(titleLabel, dateLabel, descriptionLabel, horizontalContainer);

        // Adding everything to game card
        gameBox.getChildren().addAll(imageView, textContainer);

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

        return gameBox;
    }

    @Override
    public void switchToGamesScene() throws IOException {

    }
    @Override
    public void switchToYearsScene() throws IOException {
        SceneController.getInstance().switchScene("years.fxml");
    }
    @Override
    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }
    @Override
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }
}
