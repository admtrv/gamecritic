package utils;

/**
 * Exception class to handle errors when loading fonts. Fonts are often
 * important to application's gui. Failure to load them can negatively
 * impact user experience or make the application non-functional.
 * Using this exception, you can recognize that the error occurred when
 * loading fonts: they damaged or paths written incorrectly.
 */
public class FontLoadingException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message detail message
     */
    public FontLoadingException(String message) {
        super(message);
    }

}
