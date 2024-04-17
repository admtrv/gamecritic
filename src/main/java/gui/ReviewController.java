package gui;

import aggregation.*;
import javafx.scene.control.Alert;
import reviews.*;
import session.*;
import users.*;
import game.*;
import utils.*;
import validation_factory.*;

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
    @FXML private TextArea plusesTextArea;
    @FXML private Label plusesCharactersLabel;
    @FXML private TextArea minusesTextArea;
    @FXML private Label minusesCharactersLabel;
    Game game = CurrentGame.getInstance().getGame();
    User user = CurrentUser.getInstance().getUser();
    Review review = CurrentReview.getInstance().getReview();
    private int CHARACTERS_MAX = 1000; // For user
    private int ADDITIONAL_CHARACTERS_MAX = 500;

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

        if(user instanceof Critic) {
            CHARACTERS_MAX = 5000; // For critic
            plusesTextArea.setVisible(true);
            minusesTextArea.setVisible(true);
            DetailedReview detailedReview = (DetailedReview) review;
            plusesTextArea.setText(detailedReview.getPluses());
            minusesTextArea.setText(detailedReview.getMinuses());
        } else {
            plusesTextArea.setVisible(false);
            minusesTextArea.setVisible(false);
        }

        // Observer for Text Area
        setupTextArea(reviewTextArea, charactersLabel, CHARACTERS_MAX);
        if(user instanceof Critic) {
            setupTextArea(plusesTextArea, plusesCharactersLabel, ADDITIONAL_CHARACTERS_MAX);
            setupTextArea(minusesTextArea, minusesCharactersLabel, ADDITIONAL_CHARACTERS_MAX);
        }

        // Observer for score
        scoreSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scoreLabel.setText(Integer.toString(newValue.intValue()));
            scoreLabel.setStyle(getScoreColor(newValue.intValue())  + "-fx-background-radius: 22;");
        });
    }

    private void setupTextArea(TextArea textArea, Label charactersLabel, int maxCharacters) {
        String text = textArea.getText();
        if (text == null) text = "";

        if (review != null) {
            int initialCharactersRemaining = maxCharacters - text.length();
            charactersLabel.setText(initialCharactersRemaining + " Characters remaining");
        } else {
            charactersLabel.setText(maxCharacters + " Characters remaining");
        }

        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue != null ? newValue : "";
            if (newValue.length() > maxCharacters) {
                textArea.setText(newValue.substring(0, maxCharacters));
                charactersLabel.setText("0 Characters remaining");
            } else {
                int charactersRemaining = maxCharacters - newValue.length();
                charactersLabel.setText(charactersRemaining + " Characters remaining");
            }
        });
    }

    public void postReview() {
        if (scoreLabel.getText().isEmpty()){
            AlertUtil.showAlert("Unselected Score", "Sorry, you didn't evaluate the game. Please try again.", Alert.AlertType.ERROR);
            System.err.println("Error: unselected score!");
            return;
        }

        String reviewText = reviewTextArea.getText();
        int score = (int) scoreSlider.getValue();

        String pluses = plusesTextArea.getText();
        String minuses = minusesTextArea.getText();

        // Getting validation_factory rules
        ValidationRule reviewRule = ValidationRuleFactory.getRule("reviews");
        if (!reviewRule.validate(reviewText)) {
            reviewTextArea.setStyle(errorFieldStyle);
            AlertUtil.showAlert("Invalid Text", reviewRule.getErrorMessage(), Alert.AlertType.ERROR);
            System.out.println(reviewRule.getErrorMessage());
            return;
        }

        try {
            if (user instanceof Critic) {
                // Adding a review with pluses and minuses for the critic
                DataBaseUtil.addReview(game.getId(), user.getId(), score, reviewText, LocalDate.now().toString(), pluses, minuses);

                if (review == null && user instanceof Critic) {
                    AggregateFunds.aggregateIncome(reviewText.length());
                }
            } else {
                // Adding a review
                DataBaseUtil.addReview(game.getId(), user.getId(), score, reviewText, LocalDate.now().toString(), "", "");
            }

            AggregateScore.updateScore(score);
            AlertUtil.showAlert("Posting Successful", "Your review posted successfully!", Alert.AlertType.INFORMATION);
            System.out.println("Review posted successfully!");
            switchToGameDetailsScene();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToGameDetailsScene() throws IOException {
        SceneController.getInstance().switchScene("game_view.fxml");
    }
}
