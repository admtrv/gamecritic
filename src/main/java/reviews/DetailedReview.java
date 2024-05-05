package reviews;

/**
 * Represents detailed review that includes not only basic review elements but also specific
 * positives and negatives about the game. This class extends {@link Review} by adding two more
 * fields to hold pluses and minuses about game, providing more in-depth analysis.
 */
public class DetailedReview extends Review{
    private String pluses;
    private String minuses;

    /**
     * Constructs a new detailed review with specified details including positives and negatives.
     * @param id identifier of the review
     * @param gameId identifier of game being reviewed
     * @param userId identifier of user who wrote the review
     * @param score score awarded by the reviewer
     * @param reviewText text content of the review
     * @param publishDate date when the review was published, formatted as string
     * @param pluses positive aspects of game noted by critic
     * @param minuses negative aspects of game noted by critic
     */
    public DetailedReview(int id, int gameId, int userId, int score, String reviewText, String publishDate, String pluses, String minuses) {
        super(id, gameId, userId, score, reviewText, publishDate);
        this.pluses = pluses;
        this.minuses = minuses;
    }

    public String getPluses() {
        return pluses;
    }

    public String getMinuses() {
        return minuses;
    }

}
