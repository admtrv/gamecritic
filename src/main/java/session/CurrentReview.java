package session;

import reviews.*;

/**
 * Singleton class for managing the currently selected review across the application.
 * This class ensures that single instance of review is maintained globally, allowing for
 * consistent access and updates to review state across different parts of the application.
 */
public class CurrentReview {
    private Review review;
    private static CurrentReview instance;
    private CurrentReview() { }

    /**
     * Gets the single instance of current review, creating it if it does not yet exist.
     * @return singleton instance of current review
     */
    public static CurrentReview getInstance() {
        if (instance == null) {
            instance = new CurrentReview();
        }
        return instance;
    }
    public Review getReview() {
        return review;
    }
    public void setReview(Review review) {
        this.review = review;
    }
    public void resetReview() {
        this.review = null;
    }
}
