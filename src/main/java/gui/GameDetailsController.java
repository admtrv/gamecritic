package gui;

import java.io.IOException;

import game.*;
import session.*;
import date.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

public class GameDetailsController implements ColorInterface {

    @FXML private ImageView imageView;
    @FXML private Label titleLabel;
    @FXML private Label genreLabel;
    @FXML private Label developerLabel;
    @FXML private Label platformsLabel;
    @FXML private Label releaseDateLabel;
    @FXML private Label criticsCountLabel;
    @FXML private Label usersCountLabel;
    @FXML private Label criticsScoreLabel;
    @FXML private Label usersScoreLabel;
    @FXML private Label descriptionLabel;
    Game game = CurrentGame.getInstance().getGame();
    @FXML
    public void initialize() {
        imageView.setImage(new Image(getClass().getResourceAsStream(game.getImagePath())));
        titleLabel.setText(game.getTitle());
        genreLabel.setText(game.getGenre());
        developerLabel.setText(game.getDeveloper());
        platformsLabel.setText(game.getPlatforms());
        releaseDateLabel.setText(DateConverter.convert(game.getReleaseDate()));
        criticsCountLabel.setText("Based on " + game.getCriticsCount() + " Critic Reviews");
        usersCountLabel.setText("Based on " + game.getUsersCount() + " User Reviews");
        descriptionLabel.setText(game.getDescription());

        criticsScoreLabel.setText(Double.toString(game.getCriticsScore()));
        criticsScoreLabel.setStyle(getScoreColor(game.getCriticsScore())  + "-fx-background-radius: 12;");

        usersScoreLabel.setText(Double.toString(game.getUsersScore()));
        usersScoreLabel.setStyle(getScoreColor(game.getUsersScore())  + "-fx-background-radius: 12;");
    }

    private String getScoreColor(double score) {
        if (score >= 8) {
            return "-fx-background-color:" + GreenColor;
        } else if (score >= 5) {
            return "-fx-background-color:" + YellowColor;
        } else {
            return "-fx-background-color:" + RedColor;
        }
    }

    @FXML
    private void openLink() {
        if (game != null && game.getStoreLink() != null && !game.getStoreLink().isEmpty()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(game.getStoreLink()));
                }
            } catch (IOException | URISyntaxException e) {
                System.err.println("Error browsing link!");
                e.printStackTrace();
            }
        }
    }

    public void switchToGamesScene() throws IOException {
    }
    public void switchToYearsScene() throws IOException {
    }
    public void switchToProfileScene() throws IOException {
        CurrentGame.getInstance().resetGame();
        SceneController.getInstance().switchScene("profile.fxml");
    }

    public void switchToHomeScene() throws IOException {
        CurrentGame.getInstance().resetGame();
        SceneController.getInstance().switchScene("home.fxml");
    }
}
