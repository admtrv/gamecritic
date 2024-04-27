package utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Utility class for creating and displaying customized alerts in the application.
 */
public class AlertUtil {
    /**
     * Displays alert dialog window with a custom title, content, and type. It sets custom
     * icons for alert window and content based on type of alert. It also applies custom
     * stylesheet to modify appearance according to the application's theme.
     * @param title   title of alert dialog to be displayed at the top of the window
     * @param content message or content of alert to be displayed in the main area
     * @param type    type of alert, which determines appearance and alert category,
     *                levels include:
     *                <p>- INFORMATION: indicates informational messages that highlight
     *                the progress of the application
     *                <p> - ERROR: indicates error events that might still allow the
     *                application to continue running
     */
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
