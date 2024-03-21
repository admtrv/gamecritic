package gui;

import java.io.IOException;

public class GameDetailsController {

    public void switchToGamesScene() throws IOException {
    }
    public void switchToYearsScene() throws IOException {
    }
    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }

    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }
}
