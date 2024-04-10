package gui;

import game.*;
import reviews.*;
import session.*;
import users.*;
import utils.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.sql.SQLException;

public class GameDetailsController implements StyleInterface {

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
    @FXML private Button reviewButton;
    @FXML private ImageView awardView;
    Game game = CurrentGame.getInstance().getGame();
    User user = CurrentUser.getInstance().getUser();
    Review review;
    @FXML
    public void initialize() {
        imageView.setImage(new Image(getClass().getResourceAsStream(game.getImagePath())));
        titleLabel.setText(game.getTitle());
        genreLabel.setText(game.getGenre());
        developerLabel.setText(game.getDeveloper());
        platformsLabel.setText(game.getPlatforms());
        releaseDateLabel.setText(game.getReleaseDate());
        criticsCountLabel.setText("Based on " + game.getCriticsCount() + " Critic Reviews");
        usersCountLabel.setText("Based on " + game.getUsersCount() + " User Reviews");
        descriptionLabel.setText(game.getDescription());

        criticsScoreLabel.setText(String.format("%.1f",game.getCriticsScore()));
        criticsScoreLabel.setStyle(getScoreColor(game.getCriticsScore())  + "-fx-background-radius: 12;");

        usersScoreLabel.setText(String.format("%.1f",game.getUsersScore()));
        usersScoreLabel.setStyle(getScoreColor(game.getUsersScore())  + "-fx-background-radius: 12;");

        awardView.setVisible(game.isAward());

        try {
            review = DataBaseUtil.getReview(user.getId(), game.getId());

            if (review != null) {
                CurrentReview.getInstance().setReview(review);
                reviewButton.setText("Edit My Review");
            } else {
                reviewButton.setText("Add My Review");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        SceneController.getInstance().switchScene("years.fxml");
    }
    public void switchToReviewScene() throws IOException {
        if (user instanceof Critic){
            SceneController.getInstance().switchScene("detailed_review.fxml");
        }
        else{
            SceneController.getInstance().switchScene("review.fxml");
        }

    }
    public void switchToProfileScene() throws IOException {
        CurrentGame.getInstance().resetGame();
        CurrentReview.getInstance().resetReview();
        SceneController.getInstance().switchScene("profile.fxml");
    }

    public void switchToHomeScene() throws IOException {
        CurrentGame.getInstance().resetGame();
        CurrentReview.getInstance().resetReview();
        SceneController.getInstance().switchScene("home.fxml");
    }
}
