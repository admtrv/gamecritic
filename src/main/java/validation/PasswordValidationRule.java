package validation;

public class PasswordValidationRule implements ValidationRule {
    @Override
    public boolean validate(String password) {
        if (password == null || password.length() < 6)
            return false;

        long count = password.chars().filter(Character::isDigit).count();
        return count >= 2;
    }

    @Override
    public String getErrorMessage() {
        return "Password must be at least 6 characters and contain at least 2 digits!";
    }
}