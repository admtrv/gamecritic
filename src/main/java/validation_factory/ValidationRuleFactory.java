package validation_factory;

/**
 * Factory class that produces instances of {@link ValidationRule} based on given identifiers.
 * This class follows Factory pattern to encapsulate process of validation rule objects,
 * which simplifies the creation process.
 */
public class ValidationRuleFactory {

    /**
     * Retrieves {@link ValidationRule} instance based on the specified rule name.
     * This method acts as the central point of creation for {@link ValidationRule} objects.
     * @param ruleName name of validation rule to retrieve
     * @return instance of class that implements {@link ValidationRule}, specific to type
     *          of validation required
     * @throws RuntimeException if rule name is not recognized, indicating that the
     *         requested validation rule hasnt been implemented
     */
    public static ValidationRule getRule(String ruleName) {
        return switch (ruleName) {
            case "username" -> new UsernameValidationRule();
            case "password" -> new PasswordValidationRule();
            case "reviews" -> new ReviewValidationRule();
            case null, default -> throw new RuntimeException("Unknown validation factory rule: " + ruleName);
        };
    }
}
