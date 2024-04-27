package logger_decorator;

/**
 * Defines the levels of logging used to differentiate the severity of an event.
 * <p>Levels include:
 * <p>- DEBUG: indicates events that cannot be executed further without error
 *      correction and debugging of the application
 * <p>- INFO: indicates informational messages that highlight the progress
 *      of the application
 * <p>- ERROR: indicates error events that might still allow the application
 *      to continue running
 */
public enum LoggerLevel {
    DEBUG,
    INFO,
    ERROR
}
