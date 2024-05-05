package reviews;

import utils.*;

/**
 * Represents review made by user on a game, acts as
 * data model for handling review information.
 */
public class Review {
    private int id;
    private int gameId;
    private int userId;
    private int score;
    private String reviewText;
    private String publishDate;

    /**
     * Constructs new review with specified details.
     * @param id identifier of the review
     * @param gameId identifier of game being reviewed
     * @param userId identifier of user who wrote the review
     * @param score score awarded by the reviewer
     * @param reviewText text content of the review
     * @param publishDate date when the review was published, formatted as string
     */
    public Review(int id, int gameId, int userId, int score, String reviewText, String publishDate) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.score = score;
        this.reviewText = reviewText;
        this.publishDate = DateUtil.convertDate(publishDate);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getPublishDate() {
        return publishDate;
    }

}
