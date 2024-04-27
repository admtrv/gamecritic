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
        if ("username".equals(ruleName)) {
            return new UsernameValidationRule();
        } else if ("password".equals(ruleName)) {
            return new PasswordValidationRule();
        } else if ("reviews".equals(ruleName)){
            return new ReviewValidationRule();
        } else {
            throw new RuntimeException("Unknown validation factory rule: " + ruleName);
        }
    }
}
