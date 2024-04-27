package gui;

import game.*;
import gui_interfaces.*;
import reviews.*;
import session.*;
import users.*;
import utils.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller class responsible for displaying detailed view of review.
 */
public class ReviewViewController implements StyleInterface {
    @FXML private Label scoreLabel;
    @FXML private Label gameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label reviewTextLabel;

    private Game game = CurrentGame.getInstance().getGame();
    private Review review = CurrentReview.getInstance().getReview();

    /**
     * Initializes review view by setting up the labels with review data.
     * It finds out user who wrote review from the database and formats gui based on type of user.
     */
    @FXML
    public void initialize() {
        // Score label
        scoreLabel.setText(Integer.toString(review.getScore()));
        scoreLabel.setStyle(getScoreColor(review.getScore())  + "-fx-background-radius: 22;");

        // Username label
        User reviewUser = null;
        try {
            reviewUser = DataBaseUtil.getUser(review.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        usernameLabel.setText(reviewUser.getUsername());

        // Title label
        if (reviewUser instanceof Critic) {
            gameLabel.setText(game.getTitle() + " Critic Review");
        } else {
            gameLabel.setText(game.getTitle() + " User Review");
        }

        // Review label
        if (review instanceof DetailedReview) {
            DetailedReview detailedReview = (DetailedReview) review;
            StringBuilder reviewText = new StringBuilder();

            if (detailedReview.getPluses() != null) {
                reviewText.append("Pluses:\n").append(detailedReview.getPluses()).append("\n\n");
            }

            if (detailedReview.getMinuses() != null) {
                reviewText.append("Minuses:\n").append(detailedReview.getMinuses()).append("\n\n");
            }

            reviewText.append(detailedReview.getReviewText());
            reviewTextLabel.setText(reviewText + "\n\n");
        } else {
            reviewTextLabel.setText(review.getReviewText() + "\n\n");
        }
    }

    public void switchToBackScene() throws IOException {
        CurrentReview.getInstance().resetReview();
        SceneController.getInstance().switchScene("game_view.fxml");
    }
}
