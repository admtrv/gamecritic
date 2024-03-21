package validation;

public class UsernameValidationRule implements ValidationRule {
    @Override
    public boolean validate(String username) {
        return username != null && username.length() >= 3;
    }

    @Override
    public String getErrorMessage() {
        return "Username must be at least 3 characters long!";
    }
}