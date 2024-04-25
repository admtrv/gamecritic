package gui;

import game.*;
import gui_interfaces.*;
import logger_decorator.FileLogger;
import logger_decorator.Logger;
import logger_decorator.LoggerLevel;
import logger_decorator.TimeLogger;
import reviews.*;
import session.*;
import users.*;
import utils.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
import java.util.List;

public class GameViewController implements StyleInterface {

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
    @FXML private ScrollPane scrollPane;
    @FXML private VBox reviewsContainer;
    @FXML private VBox detailedReviewsContainer;
    private List<Review> reviews;
    private List<DetailedReview> detailedReviews;
    Game game = CurrentGame.getInstance().getGame();
    User user = CurrentUser.getInstance().getUser();
    Review review;

    private static Logger logger = new TimeLogger(new FileLogger());

    @FXML
    public void initialize() {
        dispalayDetails();

        try {
            reviews = DataBaseUtil.getReviews(game.getId());
            detailedReviews = DataBaseUtil.getDetailedReviews(game.getId());

            displayReviews(reviewsContainer, reviews);
            displayReviews(detailedReviewsContainer, detailedReviews);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void dispalayDetails(){
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

        updateMyReviewStatus();
    }

    private void updateMyReviewStatus() {
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
                    logger.log("Opened web link", LoggerLevel.INFO);
                    desktop.browse(new URI(game.getStoreLink()));
                }
            } catch (IOException | URISyntaxException e) {
                System.err.println("Error browsing link!");
                logger.log("Problem to open web link", LoggerLevel.DEBUG);
                e.printStackTrace();
            }
        }
    }

    private void displayReviews(VBox reviewsContainer, List<? extends Review> reviews) {
        reviewsContainer.getChildren().clear();

        for (Review review : reviews) {
            // Container for the entire review
            VBox reviewBox = new VBox(10);
            reviewBox.setPadding(new Insets(10));
            reviewBox.setMargin(reviewBox, new Insets(0, 0, 10, 0));
            reviewBox.setAlignment(Pos.CENTER_LEFT);
            reviewBox.setStyle("-fx-background-color: " + BoxBackgroundColor + " -fx-background-radius: 10");

            // Horizontal container for the top part of the review
            HBox headerBox = new HBox(10);
            headerBox.setAlignment(Pos.CENTER_LEFT);

            // Score
            Label scoreLabel = new Label(String.valueOf((int) review.getScore()));
            scoreLabel.setFont(new Font("ProximaNova-ExtraBold", 14));
            scoreLabel.setStyle(getScoreColor(review.getScore()) + "-fx-background-radius: 8;");
            scoreLabel.setAlignment(Pos.CENTER);
            scoreLabel.setMinWidth(30);
            scoreLabel.setMinHeight(30);

            // Username
            User user = null;
            try {
                user = DataBaseUtil.getUser(review.getUserId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Label usernameLabel = new Label(user.getUsername());
            usernameLabel.setMinWidth(285);
            usernameLabel.setFont(new Font("ProximaNova-ExtraBold", 16));

            // Reviews date
            Label dateLabel = new Label(review.getPublishDate().toString());
            dateLabel.setFont(new Font("ProximaNova-Regular", 12));
            HBox.setHgrow(dateLabel, Priority.ALWAYS);
            dateLabel.setMinWidth(50);
            dateLabel.setAlignment(Pos.CENTER_RIGHT);

            // Placing elements in the headerBox
            headerBox.getChildren().addAll(scoreLabel, usernameLabel, dateLabel);

            // Текст отзыва в центре
            Label reviewTextLabel = new Label(review.getReviewText());
            reviewTextLabel.setFont(new Font("ProximaNova-Regular", 14));
            reviewTextLabel.setWrapText(true);
            reviewTextLabel.setMinWidth(300);
            reviewTextLabel.setMinHeight(60);
            reviewTextLabel.setAlignment(Pos.TOP_LEFT);

            // Button "Read More" down
            Button readMoreButton = new Button("Read More");
            readMoreButton.setStyle("-fx-background-color: #3b3b3b; -fx-background-radius: 3; -fx-text-fill: #ffffff");
            readMoreButton.setFont(new Font("ProximaNova-Regular", 14));
            readMoreButton.setCursor(Cursor.HAND);
            readMoreButton.setOnAction(event -> {
                CurrentReview.getInstance().setReview(review);
                try {
                    SceneController.getInstance().switchScene("review_view.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            reviewBox.getChildren().addAll(headerBox, reviewTextLabel, readMoreButton);// Add everything to the main container
            reviewsContainer.getChildren().add(reviewBox); // Add a review container to VBox
        }
    }

    public void scroll() {
        double targetY = detailedReviewsContainer.getBoundsInParent().getMinY();

        scrollPane.setVvalue(targetY);
    }

    public void switchToGamesScene() throws IOException {
        CurrentGame.getInstance().resetGame();
        CurrentReview.getInstance().resetReview();
        SceneController.getInstance().switchScene("games.fxml");
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
