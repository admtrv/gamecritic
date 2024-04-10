package reviews;

public class DetailedReview extends Review{
    private String pluses;
    private String minuses;

    public DetailedReview(int id, int gameId, int userId, int score, String reviewText, String publishDate, String pluses, String minuses) {
        super(id, gameId, userId, score, reviewText, publishDate);
        this.pluses = pluses;
        this.minuses = minuses;
    }

    public String getPluses() {
        return pluses;
    }

    public void setPluses(String pluses) {
        this.pluses = pluses;
    }

    public String getMinuses() {
        return minuses;
    }

    public void setMinuses(String minuses) {
        this.minuses = minuses;
    }
}
