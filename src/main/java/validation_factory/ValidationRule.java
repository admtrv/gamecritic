package validation_factory;

public interface ValidationRule {
    boolean validate(String input);
    String getErrorMessage();
}
