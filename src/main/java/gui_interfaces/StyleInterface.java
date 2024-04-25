package gui_interfaces;

/*
An interface that defines a set of constant string values and methods.
These constants represent color codes that can be used in different
parts of the application for consistency of styles.
*/

public interface StyleInterface {
    String GreenColor = "#00ce7a;"; // Green
    String YellowColor = "#ffbd3f;"; // Yellow
    String RedColor = "#ff6874;"; // Red
    String FieldBorderColor = "#3d3d3d;"; // Color of borders of any field
    String BoxBackgroundColor = "#f2f2f2;"; // Light gray to create a card, separating it from the background
    String errorFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + RedColor + " -fx-border-radius: 3";
    String normalFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + FieldBorderColor + " -fx-border-radius: 3";
    String initialScoreStyle = "-fx-background-color: #ffffff; -fx-border-color: #3d3d3d;";
    default String getScoreColor(double score) {
        if (score >= 8) {
            return "-fx-background-color:" + GreenColor;
        } else if (score >= 5) {
            return "-fx-background-color:" + YellowColor;
        } else {
            return "-fx-text-fill: #ffffff; -fx-background-color:" + RedColor;
        }
    }
}
