package logger_decorator;

public abstract class LoggerDecorator implements Logger {
    protected Logger wrappedLogger;

    public LoggerDecorator(Logger logger) {
        this.wrappedLogger = logger;
    }
}
