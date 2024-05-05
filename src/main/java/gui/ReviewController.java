package gui;

import aggregation.*;
import gui_interfaces.*;
import logger_decorator.*;
import reviews.*;
import session.*;
import users.*;
import game.*;
import utils.*;
import validation_factory.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import javafx.fxml.FXML;

/**
 * Controller that manages user interaction for reviewing games. It handles display
 * and validation for game reviews, and interacts with the database to save results.
 */
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
    private Game game = CurrentGame.getInstance().getGame();
    private User user = CurrentUser.getInstance().getUser();
    private Review review = CurrentReview.getInstance().getReview();
    private int CHARACTERS_MAX = 1000; // For user
    private int ADDITIONAL_CHARACTERS_MAX = 500;

    /**
     * Initializes controller by setting up gui components based on review status and user type.
     * If user is a {@link Critic}, he can write an extended review with additional fields
     * to highlight pluses and minuses of the game. It also sets text fields, character limits,
     * and listeners for dynamic gui changes such as score adjustments.
     */
    @FXML
    private void initialize() {
        if (review != null) {
            scoreLabel.setText(Integer.toString(review.getScore()));
            scoreLabel.setStyle(getScoreColor(review.getScore())  + "-fx-background-radius: 22;");
            scoreSlider.setValue(review.getScore());

            reviewTextArea.setText(review.getReviewText());
            // Check if review is an instance of DetailedReview before casting
            if (review instanceof DetailedReview) {
                plusesTextArea.setText(((DetailedReview)review).getPluses());
                minusesTextArea.setText(((DetailedReview)review).getMinuses());
            } else {
                plusesTextArea.setText("");
                minusesTextArea.setText("");
            }
        } else {
            scoreLabel.setText("");
            scoreLabel.setStyle(initialScoreStyle + "-fx-border-radius: 22;");
            scoreSlider.setValue(0);

            reviewTextArea.clear();
            plusesTextArea.setText("");
            minusesTextArea.setText("");
        }

        if(user instanceof Critic) {
            CHARACTERS_MAX = 5000; // For critic

            plusesTextArea.setVisible(true);
            minusesTextArea.setVisible(true);

        } else {
            plusesTextArea.setVisible(false);
            minusesTextArea.setVisible(false);
        }

        reviewTextArea.setStyle(normalFieldStyle);

        titleLabel.setText("Write a review for " + game.getTitle());

        // Observer for Text Area
        setupTextArea(reviewTextArea, charactersLabel, CHARACTERS_MAX);
        if (user instanceof Critic) {
            setupTextArea(plusesTextArea, plusesCharactersLabel, ADDITIONAL_CHARACTERS_MAX);
            setupTextArea(minusesTextArea, minusesCharactersLabel, ADDITIONAL_CHARACTERS_MAX);
        }

        // Observer for score
        scoreSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scoreLabel.setText(Integer.toString(newValue.intValue()));
            scoreLabel.setStyle(getScoreColor(newValue.intValue())  + "-fx-background-radius: 22;");
        });
    }

    /**
     * Configures text areas for review content including displaying remaining characters.
     */
    @FXML
    private void setupTextArea(TextArea textArea, Label charactersLabel, int maxCharacters) {
        String text = textArea.getText();

        if (text == null)
            text = "";

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

    /**
     * Posts the review to the database after validating input.
     * Works with both new reviews and updates to existing reviews.
     * If the user is a {@link Critic}, he get paid for writing first review.
     */
    @FXML
    private void postReview() {
        Logger logger = new TimeLogger(new FileLogger());

        if (scoreLabel.getText().isEmpty()){

            AlertUtil.showAlert("Unselected Score", "Sorry, you didn't evaluate the game. Please try again.", Alert.AlertType.ERROR);
            logger.log("Invalid score for game: [" + game.getTitle() + "]", LoggerLevel.ERROR);
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
            logger.log("Invalid text for game: [" + game.getTitle() + "]", LoggerLevel.ERROR);
            System.err.println(reviewRule.getErrorMessage());

            return;
        }

        if (Objects.equals(pluses, "")) {
            pluses = null;
        }

        if (Objects.equals(minuses, "")) {
            minuses = null;
        }

        try {
            if (user instanceof Critic) {
                // Adding a review with pluses and minuses for the critic
                DataBaseUtil.addReview(game.getId(), user.getId(), score, reviewText, LocalDate.now().toString(), pluses, minuses);

                // If it is first review for this game, adding income
                if (review == null && user instanceof Critic) {
                    AggregateFunds.aggregateIncome(reviewText.length());
                }
            } else {
                // Adding a usual review for the user
                DataBaseUtil.addReview(game.getId(), user.getId(), score, reviewText, LocalDate.now().toString(), "", "");
            }

            AggregateScore.updateScore(score);

            AlertUtil.showAlert("Posting Successful", "Your review posted successfully!", Alert.AlertType.INFORMATION);
            logger.log("Posted review for game: [" + game.getTitle() + "]", LoggerLevel.INFO);
            System.out.println("Review posted successfully!");

            switchToGameDetailsScene();
        } catch (SQLException | IOException e) {

            AlertUtil.showAlert("Error posting", "Sorry, there is an error while posting review!", Alert.AlertType.INFORMATION);
            logger.log("Problem posting review for game: [" + game.getTitle() + "]", LoggerLevel.INFO);
            System.err.println("Error posting review!");
            e.printStackTrace();
        }
    }

    public void switchToGameDetailsScene() throws IOException {
        SceneController.getInstance().switchScene("game_view.fxml");
    }
}
