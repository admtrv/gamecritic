package font;

import javafx.scene.text.Font;

public class FontLoader {

    public static void loadFonts() {
        loadFont("/fonts/proximanova_black.otf");
        loadFont("/fonts/proximanova_black.ttf");
        loadFont("/fonts/proximanova_blackit.otf");
        loadFont("/fonts/proximanova_bold.otf");
        loadFont("/fonts/proximanova_boldit.otf");
        loadFont("/fonts/proximanova_extrabold.otf");
        loadFont("/fonts/proximanova_light.otf");
        loadFont("/fonts/proximanova_regular.ttf");
    }

    private static void loadFont(String fontPath) {
        Font.loadFont(FontLoader.class.getResourceAsStream(fontPath), 1);
    }
}
