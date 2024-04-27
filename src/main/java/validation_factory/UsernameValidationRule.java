package validation_factory;

/**
 * Concrete implementation of {@link ValidationRule} for validating usernames.
 * This class ensures that a username meets the minimum length requirement.
 */
public class UsernameValidationRule implements ValidationRule {
    private static final int MIN_USERNAME_LENGTH = 3;

    /**
     * Validates username to ensure it meets the minimum length requirement.
     * @param username username to validate
     * @return true if username is at least the minimum length, false otherwise
     */
    @Override
    public boolean validate(String username) {
        return username != null && username.length() >= MIN_USERNAME_LENGTH;
    }

    /**
     * Provides message describing validation rule.
     * @return error message indicating the minimum required length of a username
     */
    @Override
    public String getErrorMessage() {
        return "Username must be at least " + MIN_USERNAME_LENGTH + " characters long.";
    }
}