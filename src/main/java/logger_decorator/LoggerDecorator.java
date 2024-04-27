package logger_decorator;

/**
 * Decorator that allows for adding new functionalities to loggers
 * without modifying their structure. This class serves as base
 * for creating specific logger decorators.
 */
public abstract class LoggerDecorator implements Logger {
    protected Logger wrappedLogger;

    /**
     * Constructs that wraps a given logger.
     * @param logger logger to wrap
     */
    public LoggerDecorator(Logger logger) {
        this.wrappedLogger = logger;
    }
}
