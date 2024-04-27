package logger_decorator;

/**
 * Defines the basics  for logging messages with different severity levels.
 */
public interface Logger {
    void log(String message, LoggerLevel level);
}
