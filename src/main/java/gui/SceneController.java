package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// Singleton class
public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static SceneController instance;

    private SceneController() { }

    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchScene(String fxml) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
