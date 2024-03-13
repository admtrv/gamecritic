package gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class AppController extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.setStage(primaryStage);

        Image WindowLogo = new Image(getClass().getResourceAsStream("/images/logo_icon.png"));
        primaryStage.getIcons().add(WindowLogo);
        primaryStage.setTitle("gamecritic");

        try {
            sceneController.switchScene("register.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}