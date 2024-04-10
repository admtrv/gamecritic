package validation_factory;

// Pattern Factory
public class ValidationRuleFactory {

    public static ValidationRule getRule(String ruleName) {
        if ("username".equals(ruleName)) {
            return new UsernameValidationRule();
        } else if ("password".equals(ruleName)) {
            return new PasswordValidationRule();
        } else if ("reviews".equals(ruleName)){
            return new ReviewValidationRule();
        } else {
            throw new RuntimeException("Unknown validation_factory rule: " + ruleName);
        }
    }
}
