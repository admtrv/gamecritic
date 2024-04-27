package logger_decorator;

/**
 * Decorator for loggers that appends user credentials to the log message.
 */
public class UserDataLogger extends LoggerDecorator {
    private String username;
    private String password;

    /**
     * Construct that wraps around an existing logger
     * and initializes it with user credentials.
     * @param logger logger to wrap
     * @param username username to log
     * @param password password to log
     */
    public UserDataLogger(Logger logger, String username, String password) {
        super(logger);
        this.username = username;
        this.password = password;
    }

    /**
     * Logs message at the specified logging level with appended user credentials.
     * @param message log message to be written to the file
     * @param level logging level to use
     */
    @Override
    public void log(String message, LoggerLevel level) {
        String formattedMessage =  message + ": Username[" + username + "] Password[" + password + "] ";
        wrappedLogger.log(formattedMessage, level);
    }
}
