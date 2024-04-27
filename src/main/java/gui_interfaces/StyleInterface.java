package gui_interfaces;

/**
* Interface that defines set of constant string values and methods.
* These constants represent color codes that can be used in different
* parts of the application for consistency of styles.
*/

public interface StyleInterface {
    // Green color used for high scores
    String GreenColor = "#00ce7a;";

    // Green color used for high scores
    String YellowColor = "#ffbd3f;";

    // Red color used for low scores
    String RedColor = "#ff6874;";

    // Default border color for fields
    String FieldBorderColor = "#3d3d3d;";

    // Background color for boxes and cards
    String BoxBackgroundColor = "#f2f2f2;";

    // Style for fields when an error occurs
    String errorFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + RedColor + " -fx-border-radius: 3";

    // Normal style for fields
    String normalFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + FieldBorderColor + " -fx-border-radius: 3";

    // Initial style for score, when no score selected
    String initialScoreStyle = "-fx-background-color: #ffffff; -fx-border-color: #3d3d3d;";

    /**
     * Determines background color of a score label based on the score.
     * @param score score, on which selection is made
     * @return string representing style for the score label
     */
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
