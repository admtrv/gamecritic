package gui_interfaces;

import javafx.fxml.FXML;
import java.io.IOException;

/**
 * Interface defining methods for standard toolbar buttons within the application's gui. These methods
 * facilitate scene transitions, representing core navigational functions accessible throughout the application.
 */
public interface ToolBarInterface {
    @FXML void switchToGamesScene() throws IOException;
    @FXML void switchToYearsScene() throws IOException;
    @FXML void switchToProfileScene() throws IOException;
    @FXML void switchToHomeScene() throws IOException;
}
