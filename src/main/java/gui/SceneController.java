package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Singleton class that manages scene transitions within the application.
 * This class centralizes control of changing content, ensuring that
 * only one instance of scene controller is active at any time.
 */
public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static SceneController instance;

    private SceneController() { }

    /**
     * Gets the single instance of the scene controller, creating it if it does not yet exist.
     * @return singleton instance of scene controller
     */
    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    /**
     * Switches scene of the primary stage. Loads an FXML file, sets
     * it as the root of the scene, and displays it on the primary stage.
     * @param fxml The name of the FXML file that sets as the new scene
     * @throws IOException if there is an error in switching scenes
     */
    public void switchScene(String fxml) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
