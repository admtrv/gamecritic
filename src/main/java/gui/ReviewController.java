package gui;

import aggregation.*;
import review.*;
import session.*;
import users.*;
import game.*;
import validation.*;
import database.*;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
public class ReviewController implements StyleInterface {
    @FXML private Label scoreLabel;
    @FXML private TextArea reviewTextArea;
    @FXML private Slider scoreSlider;
    @FXML private Label titleLabel;
    @FXML private Label charactersLabel;
    Game game = CurrentGame.getInstance().getGame();
    User user = CurrentUser.getInstance().getUser();
    Review review = CurrentReview.getInstance().getReview();
    private int CHARACTERS_MAX = 1000; // For user

    @FXML
    public void initialize() {
        if (review != null) {
            scoreLabel.setText(Integer.toString(review.getScore()));
            scoreLabel.setStyle(getScoreColor(review.getScore())  + "-fx-background-radius: 22;");
            scoreSlider.setValue(review.getScore());
            reviewTextArea.setText(review.getReviewText());
        } else {
            scoreLabel.setText("");
            scoreLabel.setStyle(initialScoreStyle + "-fx-border-radius: 22;");
            scoreSlider.setValue(0);
            reviewTextArea.clear();
        }

        reviewTextArea.setStyle(normalFieldStyle);
        titleLabel.setText("Write a review for " + game.getTitle());

        if(user instanceof Critic)
            CHARACTERS_MAX = 5000; // For critic

        if (review != null) {
            int initialCharactersRemaining = CHARACTERS_MAX - reviewTextArea.getText().length();
            charactersLabel.setText(initialCharactersRemaining + " Characters remaining");
        } else {
            charactersLabel.setText(CHARACTERS_MAX + " Characters remaining");
        }

        // Observer for characters limit
        reviewTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > CHARACTERS_MAX) {
                reviewTextArea.setText(newValue.substring(0, CHARACTERS_MAX));
                charactersLabel.setText("0 Characters remaining");
            } else {
                int charactersRemaining = CHARACTERS_MAX - newValue.length();
                charactersLabel.setText(charactersRemaining + " Characters remaining");
            }
        });

        // Observer for score
        scoreSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scoreLabel.setText(Integer.toString(newValue.intValue()));
            scoreLabel.setStyle(getScoreColor(newValue.intValue())  + "-fx-background-radius: 22;");
        });
    }

    public void postReview() {
        if (scoreLabel.getText().isEmpty()){
            System.err.println("Error: unselected score!");
            return;
        }

        String reviewText = reviewTextArea.getText();
        int score = (int) scoreSlider.getValue();

        // Getting validation rules
        ValidationRule reviewRule = ValidationRuleFactory.getRule("review");
        if (!reviewRule.validate(reviewText)) {
            reviewTextArea.setStyle(errorFieldStyle);
            System.out.println(reviewRule.getErrorMessage());
            return;
        }

        if (!reviewRule.validate(reviewText)) {
            System.out.println(reviewRule.getErrorMessage());
            return;
        }

        try {
            DataBaseUtil.addReview(game.getId(), user.getId(), score, reviewText, LocalDate.now().toString());
            AggregateScore.updateScore(score);

            if (user instanceof Critic && review == null){
                double income = reviewText.length() * 0.1;
                double newBalance = ((Critic) user).getBalance() + income;

                if (DataBaseUtil.updateBalance(user.getId(), newBalance ))
                {
                    System.out.println("Сritics balance updated successfully!");
                    ((Critic) user).setBalance(newBalance);
                }
            }

            System.out.println("Review posted successfully!");
            switchToGameDetailsScene();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToGameDetailsScene() throws IOException {
        SceneController.getInstance().switchScene("game_details.fxml");
    }
}
