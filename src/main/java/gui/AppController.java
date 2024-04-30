package gui;

import session.*;
import utils.*;
import logger_decorator.*;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Main application controller for initializing and managing primary stage. This class
 * set up application environment, including loading necessary resources, like fonts
 * and user data, setting the application stage, and starting gui by choosing between
 * scenes, based on status of previous user session
 */
public class AppController extends Application {

    private static Logger logger = new TimeLogger(new FileLogger());

    /**
     * Starts the primary stage of the application, sets up the environment, and decides which scene to display first.
     * @param primaryStage Primary stage for this application, on which scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.setStage(primaryStage);

        // Loading fonts before starting gui
        FontUtil.loadProjectFonts();

        // Setting stage settings
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logos/logo_icon.png")));
        primaryStage.setTitle("gamecritic");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {

            logger.log("Application closed", LoggerLevel.INFO);
            System.out.println("Application closed.");
        });

        // Trying to load data of the current user from previous session
        CurrentUser.getInstance().loadCurrentUser();
        try {
            if (CurrentUser.getInstance().getUser() != null) {
                sceneController.switchScene("home.fxml"); // The user was already logged in, going to the home screen

                logger.log("User loaded from serialization file", LoggerLevel.INFO);
                System.out.println("User loaded from serialization file.");
            } else {
                sceneController.switchScene("login.fxml"); // The user wasn't logged in, showing login screen

                logger.log("User not loaded from serialization file", LoggerLevel.INFO);
                System.out.println("User not loaded from serialization file.");
            }
        } catch (IOException e) {

            logger.log("Problem loading user from serialization file", LoggerLevel.DEBUG);
            System.err.println("User not loaded from serialization file!");
            e.printStackTrace();
        }

    }

    /**
     * Main entry point for the application.
     */
    public static void main(String[] args) {

        logger.log("Launched app", LoggerLevel.INFO);
        System.out.println("Launched app.");

        launch(args);
    }
}