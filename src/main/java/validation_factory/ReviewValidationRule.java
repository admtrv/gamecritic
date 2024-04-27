package validation_factory;

/**
 * Concrete implementation of {@link ValidationRule} for validating review texts.
 * This class ensures that a review text meets the minimum length requirement.
 */
public class ReviewValidationRule implements ValidationRule {
    private static final int MIN_REVIEW_LENGTH = 50;

    /**
     * Validates review text to ensure it meets the minimum length requirement.
     *
     * @param reviewText review text to validate
     * @return true if review text is at least minimum length, false otherwise
     */
    @Override
    public boolean validate(String reviewText) {
        if (reviewText == null)
            return false;

        return reviewText.length() >= MIN_REVIEW_LENGTH;
    }

    /**
     * Provides message describing validation rule.
     * @return error message indicating the minimum required length of a review text
     */
    @Override
    public String getErrorMessage() {
        return "Review must be at least " + MIN_REVIEW_LENGTH + " characters long.";
    }
}
