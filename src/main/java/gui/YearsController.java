package gui;

import javafx.geometry.Insets;
import session.*;
import database.*;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public class YearsController implements StyleInterface{
    @FXML private VBox yearsContainer;
    List<String> years;
    public void initialize() {
        try {
            years = DataBaseUtil.getYears();
            displayYears(yearsContainer,years);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayYears(VBox yearsContainer, List<String> years) {
        yearsContainer.setSpacing(15);
        for (String year : years) {
            Button yearButton = new Button("Game of the Year " + year);
            yearButton.setStyle("-fx-background-color: " + BoxBackgroundColor + " -fx-background-radius: 10");
            yearButton.setPadding(new Insets(10));
            yearButton.setFont(new Font("ProximaNova-ExtraBold", 14));
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
    public void switchToGamesScene() throws IOException {

    }
    public void switchToYearsScene() throws IOException {
    }
    public void switchToHomeScene() throws IOException {
        SceneController.getInstance().switchScene("home.fxml");
    }

    public void switchToProfileScene() throws IOException {
        SceneController.getInstance().switchScene("profile.fxml");
    }
}
