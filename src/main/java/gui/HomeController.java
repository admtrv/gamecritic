package gui;

import database.*;
import game.*;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
    private List<Game> lastGames;

    @FXML private HBox LatestGamesContainer;
    public void initialize() throws IOException {
        try {
            lastGames = DataBaseUtil.getLastGames(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        displayGames();
    }
    private void displayGames() {
        LatestGamesContainer.getChildren().clear();

        for (Game game : lastGames) {
            VBox gameBox = new VBox(5);
            gameBox.setPadding(new Insets(10));
            gameBox.setStyle("-fx-background-color: #fafafa; -fx-background-radius: 10");

            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(game.getImagePath())));
            imageView.setFitHeight(160); // Height : Width = 15 : 10
            imageView.setFitWidth(110);

            Label titleLabel = new Label(game.getTitle());
            titleLabel.setFont(new Font("Proxima Nova Regular", 14));

            Label scoreLabel = new Label(Double.toString(game.getUsersScore()));
            scoreLabel.setFont(new Font("ProximaNova-ExtraBold", 16));

            Label developerLabel = new Label(game.getDeveloper());
            developerLabel.setFont(new Font("Proxima Nova Regular", 14));

            gameBox.getChildren().addAll(imageView, titleLabel, scoreLabel, developerLabel);
            HBox.setMargin(gameBox, new Insets(0, 45, 0, 0));
            LatestGamesContainer.getChildren().add(gameBox);
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
