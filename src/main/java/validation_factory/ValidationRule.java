package validation_factory;

/**
 * Represents generic rule for validating string inputs across the application.
 * This interface provides base for implementing validation rules, ensuring
 * that all concrete rules offer standardized approach to validation.
 */
public interface ValidationRule {
    /**
     * Validates a given input string based on specific terms.
     * @param input string input to validate
     * @return true if input meets the rules criteria, false otherwise
     */
    boolean validate(String input);

    /**
     * Provides error message associated with rule.
     * @return error message describing why input fails validation
     */
    String getErrorMessage();
}
