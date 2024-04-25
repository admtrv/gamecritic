package logger_decorator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger implements Logger {
    private String filename;

    public FileLogger() {
        this.filename = "logger.log";
    }

    @Override
    public void log(String message, LoggerLevel level) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.filename, true)))) {
            out.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file!");
        }
    }
}
