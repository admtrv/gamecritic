package session;

import reviews.*;

public class CurrentReview {
    private Review review;
    private static CurrentReview instance;
    private CurrentReview() { }
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
