package gui;

import session.*;
import utils.*;
import logger_decorator.*;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class AppController extends Application {

    private static Logger logger = new TimeLogger(new FileLogger());
    @Override
    public void start(Stage primaryStage) {
        FontUtil.loadProjectFonts();
        SceneController sceneController = SceneController.getInstance();
        sceneController.setStage(primaryStage);
        Image WindowLogo = new Image(getClass().getResourceAsStream("/images/logos/logo_icon.png"));
        primaryStage.getIcons().add(WindowLogo);
        primaryStage.setTitle("gamecritic");
        primaryStage.setResizable(false);

        CurrentUser.getInstance().loadCurrentUser(); // Trying to load data of the current user
        try {
            if (CurrentUser.getInstance().getUser() != null) {
                logger.log("User loaded from serialization file", LoggerLevel.INFO);
                sceneController.switchScene("home.fxml"); // The user was already logged in, going to the home screen

            } else {
                logger.log("User not loaded from serialization file", LoggerLevel.INFO);
                sceneController.switchScene("login.fxml"); // The user wasn't logged in, showing login screen
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        logger.log("Launched app", LoggerLevel.INFO);
        launch(args);
    }
}