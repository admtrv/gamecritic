package logger_decorator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Decorator that provides logging messages to a file. This class is responsible
 * for writing log entries to a specified file in the local file system.
 */
public class FileLogger implements Logger {
    private String filename;

    /**
     * Constructs with the default filename set to "logger.log".
     */
    public FileLogger() {
        this.filename = "logger.log";
    }

    /**
     * Logs a message along with its severity level to the file. If the file does not
     * exist, it will be created.
     * @param message log message to be written to the file
     * @param level logging level to use
     */
    @Override
    public void log(String message, LoggerLevel level) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.filename, true)))) {
            out.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file!");
        }
    }
}
