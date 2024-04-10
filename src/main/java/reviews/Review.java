package reviews;

public class Review {
    private int id;
    private int gameId;
    private int userId;
    private int score;
    private String reviewText;
    private String publishDate;

    public Review(int id, int gameId, int userId, int score, String reviewText, String publishDate) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.score = score;
        this.reviewText = reviewText;
        this.publishDate = publishDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
