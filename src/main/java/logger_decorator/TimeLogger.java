package logger_decorator;

import utils.*;

public class TimeLogger extends LoggerDecorator {
    public TimeLogger(Logger logger) {
        super(logger);
    }

    @Override
    public void log(String message, LoggerLevel level) {
        // Формирование временной метки
        String timestamp = DateUtil.loggerDate();
        // Добавление уровня логирования к сообщению
        String logEntry = timestamp + " [" + level + "] " + message;

        // Вызов метода log базового логгера с новым форматированным сообщением
        wrappedLogger.log(logEntry, level);
    }
}
