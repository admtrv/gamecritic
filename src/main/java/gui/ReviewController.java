package gui;

import review.*;
import session.*;
import users.*;
import game.*;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import java.io.IOException;
import javafx.fxml.FXML;
public class ReviewController implements ColorInterface{
    @FXML private Label scoreLabel;
    @FXML private TextArea reviewTextArea;
    @FXML private Slider scoreSlider;
    @FXML private Label titleLabel;
    @FXML private Label charactersLabel;
    Game game = CurrentGame.getInstance().getGame();
    User user = CurrentUser.getInstance().getUser();
    Review review = CurrentReview.getInstance().getReview();
    private int CHARACTERS_MAX = 1000; // Для User

    @FXML
    public void initialize() {
        if (review != null) {
            scoreLabel.setText(Integer.toString(review.getScore()));
            scoreLabel.setStyle(getScoreColor(review.getScore())  + "-fx-background-radius: 22;");

            scoreSlider.setValue(review.getScore());
            reviewTextArea.setText(review.getReviewText());
        } else {
            scoreLabel.setText("");
            scoreLabel.setStyle("-fx-background-color: #ffffff;-fx-border-color: #3d3d3d;-fx-border-radius: 22;");
            scoreSlider.setValue(0);
            reviewTextArea.clear();
        }

        titleLabel.setText("Write a review for " + game.getTitle());

        if(user instanceof Critic)
            CHARACTERS_MAX = 5000; // Для Critic

        if (review != null) {
            int initialCharactersRemaining = CHARACTERS_MAX - reviewTextArea.getText().length();
            charactersLabel.setText(initialCharactersRemaining + " Characters remaining");
        } else {
            charactersLabel.setText(CHARACTERS_MAX + " Characters remaining");
        }

        // Observer для лимита символов
        reviewTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > CHARACTERS_MAX) {
                reviewTextArea.setText(newValue.substring(0, CHARACTERS_MAX));
                charactersLabel.setText("0 Characters remaining");
            } else {
                int charactersRemaining = CHARACTERS_MAX - newValue.length();
                charactersLabel.setText(charactersRemaining + " Characters remaining");
            }
        });

        // Observer для оценки
        scoreSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scoreLabel.setText(Integer.toString(newValue.intValue()));
            scoreLabel.setStyle(getScoreColor(newValue.intValue())  + "-fx-background-radius: 22;");
        });
    }

    private String getScoreColor(double score) {
        if (score >= 8) {
            return "-fx-background-color:" + GreenColor;
        } else if (score >= 5) {
            return "-fx-background-color:" + YellowColor;
        } else {
            return "-fx-text-fill: white; -fx-background-color:" + RedColor;
        }
    }
    public void switchToGameDetailsScene() throws IOException {
        SceneController.getInstance().switchScene("game_details.fxml");
    }
}
