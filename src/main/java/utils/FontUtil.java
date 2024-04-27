package utils;

import javafx.scene.text.Font;

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
        //loadFont("/fonts/proximanova_black.otf");
        loadFont("/fonts/proximanova_black.ttf");
        //loadFont("/fonts/proximanova_blackit.otf");
        //loadFont("/fonts/proximanova_bold.otf");
        //loadFont("/fonts/proximanova_boldit.otf");
        //loadFont("/fonts/proximanova_extrabold.otf");
        //loadFont("/fonts/proximanova_light.otf");
        loadFont("/fonts/proximanova_regular.ttf");
    }

    /**
     * Loads one font from specified path within application resources.
     * @param fontPath path to font file relative to the resource folder
     */
    private static void loadFont(String fontPath) {
        Font.loadFont(FontUtil.class.getResourceAsStream(fontPath), 1);
    }
}
