package gui;

import session.*;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class AppController extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.setStage(primaryStage);
        Image WindowLogo = new Image(getClass().getResourceAsStream("/images/logos/logo_icon.png"));
        primaryStage.getIcons().add(WindowLogo);
        primaryStage.setTitle("gamecritic");

        CurrentUser.getInstance().loadCurrentUser(); // Попытка загрузить данные текущего пользователя
        try {
            if (CurrentUser.getInstance().getUser() != null) {
                sceneController.switchScene("home.fxml"); // Пользователь уже был в системе, перейти на домашний экран
            } else {
                sceneController.switchScene("login.fxml"); // Пользователь не вошел, показать экран входа
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}