package gui;

import game.*;
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

public class GamesController implements StyleInterface{

    @FXML
    private GridPane gamesGrid;
    private List<Game> allGames;

    public void initialize(){
        try {
            allGames = DataBaseUtil.getGames();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayGames();
    }

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
                SceneController.getInstance().switchScene("game_details.fxml");
            } catch (IOException e) {
                System.err.println("Error switching scene!");
                e.printStackTrace();
            }
        });

        return gameBox;
    }

    public void switchToYearsScene() throws IOException {
        SceneController.getInstance().switchScene("years.fxml");
    }
    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }
}
