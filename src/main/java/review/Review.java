package review;

public class Review {
    private int id;
    private int gameId;
    private int userId;
    private int score;
    private String reviewText;

    public Review(int id, int gameId, int userId, int score, String reviewText) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.score = score;
        this.reviewText = reviewText;
    }

    // Геттеры и сеттеры
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

}
