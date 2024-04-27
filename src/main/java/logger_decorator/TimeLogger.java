package logger_decorator;

import utils.*;

/**
 * Decorator for loggers that adds a timestamp before each log entry.
 */
public class TimeLogger extends LoggerDecorator {

    /**
     * Construct that wraps around an existing logger.
     * @param logger logger to wrap
     */
    public TimeLogger(Logger logger) {
        super(logger);
    }

    /**
     * Logs message at the specified logging level with a timestamp.
     * @param message log message to be written to the file
     * @param level logging level to use
     */
    @Override
    public void log(String message, LoggerLevel level) {
        // Create timestamp
        String timestamp = DateUtil.loggerDate();
        // Format  message with the level and timestamp
        String logEntry = timestamp + " [" + level + "] " + message;

        // Delegate to wrapped logger
        wrappedLogger.log(logEntry, level);
    }
}
