package logger_decorator;

public class UserDataLogger extends LoggerDecorator {
    private String username;
    private String password;

    public UserDataLogger(Logger logger, String username, String password) {
        super(logger);
        this.username = username;
        this.password = password;
    }

    @Override
    public void log(String message, LoggerLevel level) {
        String formattedMessage =  message + ": Username[" + username + "] Password[" + password + "] ";
        wrappedLogger.log(formattedMessage, level);
    }
}
