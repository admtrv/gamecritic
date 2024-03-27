package validation;

public class PasswordValidationRule implements ValidationRule {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_DIGITS_COUNT = 2;
    @Override
    public boolean validate(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH)
            return false;

        long count = password.chars().filter(Character::isDigit).count();
        return count >= MIN_DIGITS_COUNT;
    }

    @Override
    public String getErrorMessage() {
        return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters and contain at least "+ MIN_DIGITS_COUNT + " digits!";
    }
}