package utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AlertUtil {
    public static void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(AlertUtil.class.getResourceAsStream("/images/logos/logo_icon.png")));

        alert.getDialogPane().getStylesheets().add(AlertUtil.class.getResource("/gui/alert.css").toExternalForm());

        Image AlertImage = null;
        if (type == Alert.AlertType.INFORMATION) {
            AlertImage = new Image(AlertUtil.class.getResourceAsStream("/images/icons/success_alert.png"));
        }
        else if (type == Alert.AlertType.ERROR) {
            AlertImage = new Image(AlertUtil.class.getResourceAsStream("/images/icons/error_alert.png"));
        }
        
        ImageView AlertImageView = new ImageView(AlertImage);
        AlertImageView.setFitHeight(35);
        AlertImageView.setFitWidth(35);
        alert.getDialogPane().setGraphic(AlertImageView);
        
        alert.showAndWait();
    }
}
