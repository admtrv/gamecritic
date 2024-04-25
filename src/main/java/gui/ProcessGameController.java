package gui;

import logger_decorator.*;
import utils.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
public class ProcessGameController {
    @FXML private TextField titleField;
    @FXML private TextField developerField;
    @FXML private DatePicker releaseDatePicker;
    @FXML private TextField platformsField;
    @FXML private TextField genreField;
    @FXML private TextField storeLinkField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField imagePathField;
    @FXML private ImageView imageView;

    private static Logger logger = new TimeLogger(new FileLogger());
    @FXML
    private void initialize() {
        Platform.runLater(() -> imagePathField.requestFocus());
    }
    @FXML
    private void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image gameImage = new Image(imagePath);

            // Loading and displaying an image
            imagePathField.setText(imagePath);
            imageView.setImage(gameImage);
        }
    }


    @FXML
    private void saveGame() throws SQLException {
        String title = titleField.getText();
        String developer = developerField.getText();
        String releaseDate = releaseDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String platforms = platformsField.getText();
        String genre = genreField.getText();
        String storeLink = storeLinkField.getText();
        String description = descriptionArea.getText();
        String imagePath = imagePathField.getText();

        if (!imagePath.contains("/images/games/")) {

            AlertUtil.showAlert("Invalid Image Path", "Image must be located in project directory at '/images/games/*your image*'!", Alert.AlertType.ERROR);
            logger.log("Invalid attempt for image path", LoggerLevel.ERROR);
            System.err.println("Invalid Image Path!");
            return;
        }

        boolean isSaved = DataBaseUtil.addGame(title, developer, releaseDate, platforms, genre, storeLink, description, formatImagePath(imagePath));

        try {
            if (isSaved) {

                AlertUtil.showAlert("Upload Successful", "New game saved successfully!", Alert.AlertType.INFORMATION);
                logger.log("Loaded new game: [" + title + "]", LoggerLevel.INFO);
                System.out.println("Upload Successful!");

                switchToProfileScene();
            } else {

                AlertUtil.showAlert("Upload Failed", "Sorry, there was an error while saving new game. Please try again.", Alert.AlertType.ERROR);
                logger.log("Problem loading new game: [" + title + "]", LoggerLevel.DEBUG);
                System.err.println("Upload Failed!");
            }
        } catch (IOException e) {

            AlertUtil.showAlert("Upload Failed", "Sorry, there was an error while saving new game. Please try again.", Alert.AlertType.ERROR);
            logger.log("Problem loading new game: [" + title + "]", LoggerLevel.DEBUG);
            System.err.println("Upload Failed!");
            e.printStackTrace();
        }

    }

    private String formatImagePath(String imagePath) {
        // Extract the image name from the full path
        String imageName = new File(imagePath).getName();

        // Forming a new path
        return "/images/games/" + imageName;
    }

    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }

}
