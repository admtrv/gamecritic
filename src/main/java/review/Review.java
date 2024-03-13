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
}
