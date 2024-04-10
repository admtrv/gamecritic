package utils;

import javafx.scene.text.Font;

public class FontUtil {

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

    private static void loadFont(String fontPath) {
        Font.loadFont(FontUtil.class.getResourceAsStream(fontPath), 1);
    }
}
