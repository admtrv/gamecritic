package utils;

import javafx.scene.text.Font;
import java.io.InputStream;

/**
 * Utility class for loading custom fonts used throughout the application.
 * This class handles the initialization of fonts from font files, ensuring
 * that they are available for use in gui elements across the application.
 */
public class FontUtil {

    /**
     * Loads all project fonts. This method should be called at application
     * startup to ensure all custom fonts are available throughout the application.
     */
    public static void loadProjectFonts() {
        try {
            loadFont("/fonts/proximanova_black.ttf");
            loadFont("/fonts/proximanova_regular.ttf");
        } catch (FontLoadingException e) {
            System.err.println("Error loading fonts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads one font from specified path within application resources.
     * @param fontPath path to font file relative to the resource folder
     * @throws FontLoadingException if the font cannot be loaded
     */
    private static void loadFont(String fontPath) throws FontLoadingException {
        InputStream fontStream = FontUtil.class.getResourceAsStream(fontPath);
        if (fontStream == null) {
            throw new FontLoadingException("Font file not found: " + fontPath);
        }
        Font font = Font.loadFont(fontStream, 1);
        if (font == null) {
            throw new FontLoadingException("Failed to load font: " + fontPath);
        }
    }
}
