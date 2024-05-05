package gui;

import gui_interfaces.*;
import session.*;
import utils.*;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class that handles display of different years in which games have
 * received awards. It allows users to navigate to detailed views for games of each year.
 */
public class YearsController implements StyleInterface, ToolBarInterface {
    @FXML private VBox yearsContainer;
    private List<String> years;

    /**
     * Initializes view by fetching a list of years from
     * the database and displaying them as selectable buttons.
     */
    @FXML
    private void initialize() {
        try {
            years = DataBaseUtil.getYears();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayYears(yearsContainer,years);
    }

    /**
     * Fills provided container with buttons, each representing year, that
     * has games with awards. Clicking a button navigates to detailed view
     * of that year's awarded games.
     * @param yearsContainer container to hold year buttons
     * @param years list of years to create buttons for
     */
    @FXML
    private void displayYears(VBox yearsContainer, List<String> years) {
        yearsContainer.setSpacing(15);
        yearsContainer.setPadding(new Insets(0, 0, 40, 0));

        for (String year : years) {
            Button yearButton = new Button("Game of the Year " + year);
            yearButton.setStyle("-fx-background-color: " + BoxBackgroundColor + " -fx-background-radius: 10");
            yearButton.setFont(new Font("ProximaNova-Regular", 14));
            yearButton.setPadding(new Insets(10));
            yearButton.setAlignment(Pos.CENTER_LEFT);
            yearButton.setCursor(Cursor.HAND);
            yearButton.setOnAction(event -> {
                try {
                    CurrentYear.getInstance().setYear(year);
                    SceneController.getInstance().switchScene("year_details.fxml");
                } catch (IOException e) {
                    System.err.println("Error switching scene!");
                    e.printStackTrace();
                }
            });
            yearsContainer.getChildren().add(yearButton);
        }
    }

    @Override
    public void switchToGamesScene() throws IOException {
        SceneController.getInstance().switchScene("games.fxml");
    }
    @Override
    public void switchToYearsScene() throws IOException {

    }
    @Override
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }
    @Override
    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }
}
