package gui;

import session.*;
import font.*;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class AppController extends Application {

    @Override
    public void start(Stage primaryStage) {
        FontLoader.loadFonts();
        SceneController sceneController = SceneController.getInstance();
        sceneController.setStage(primaryStage);
        Image WindowLogo = new Image(getClass().getResourceAsStream("/images/logos/logo_icon.png"));
        primaryStage.getIcons().add(WindowLogo);
        primaryStage.setTitle("gamecritic");
        primaryStage.setResizable(false);

        CurrentUser.getInstance().loadCurrentUser(); // Trying to load data of the current user
        try {
            if (CurrentUser.getInstance().getUser() != null) {
                sceneController.switchScene("home.fxml"); // The user was already logged in, going to the home screen
            } else {
                sceneController.switchScene("login.fxml"); // The user wasn't logged in, showing login screen
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}