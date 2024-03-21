package validation;

public interface ValidationRule {
    boolean validate(String input);
    String getErrorMessage();
}
