package validation;

// Паттерн Factory
public class ValidationRuleFactory {

    public static ValidationRule getRule(String ruleName) {
        if ("username".equals(ruleName)) {
            return new UsernameValidationRule();
        } else if ("password".equals(ruleName)) {
            return new PasswordValidationRule();
        } else if ("review".equals(ruleName)){
            return new ReviewValidationRule();
        } else {
            throw new RuntimeException("Unknown validation rule: " + ruleName);
        }
    }
}
