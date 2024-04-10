package session;

import reviews.*;

public class CurrentReview {
    private static CurrentReview instance = new CurrentReview();
    private Review review;
    private CurrentReview() {}
    public static CurrentReview getInstance() {
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
