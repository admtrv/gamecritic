package validation_factory;

public class UsernameValidationRule implements ValidationRule {
    private static final int MIN_USERNAME_LENGTH = 3;
    @Override
    public boolean validate(String username) {
        return username != null && username.length() >= MIN_USERNAME_LENGTH;
    }

    @Override
    public String getErrorMessage() {
        return "Username must be at least " + MIN_USERNAME_LENGTH + " characters long!";
    }
}