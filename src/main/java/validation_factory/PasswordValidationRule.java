package validation_factory;

/**
 * Concrete implementation of {@link ValidationRule} for validating passwords.
 * This class ensures that password meets specific security criteria, including
 * minimum length and digit count requirements.
 */
public class PasswordValidationRule implements ValidationRule {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_DIGITS_COUNT = 2;

    /**
     * Validates password to ensure it meets minimum length and contains required number of digits.
     *
     * @param password password to validate
     * @return true if password meets minimum criteria, false otherwise
     */
    @Override
    public boolean validate(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH)
            return false;

        long count = password.chars().filter(Character::isDigit).count();
        return count >= MIN_DIGITS_COUNT;
    }

    /**
     * Provides message describing validation rule.
     * @return error message indicating the requirements for password to be considered valid
     */
    @Override
    public String getErrorMessage() {
        return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters and contain at least "+ MIN_DIGITS_COUNT + " digits.";
    }
}