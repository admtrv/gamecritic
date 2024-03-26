package validation;

public class ReviewValidationRule implements ValidationRule {
    private static final int MIN_REVIEW_LENGTH = 50;

    @Override
    public boolean validate(String reviewText) {
        if (reviewText == null) return false;
        return reviewText.length() >= MIN_REVIEW_LENGTH;
    }

    @Override
    public String getErrorMessage() {
        return "Review must be at least " + MIN_REVIEW_LENGTH + " characters long!";
    }
}
