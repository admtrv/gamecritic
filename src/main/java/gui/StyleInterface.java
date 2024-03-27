package gui;

/*
Интерфейс, который определяет набор константных строковых значений и методов.
Эти константы представляют собой коды цветов, которые могут быть
использованы в разных частях приложения для единообразия стилей.
*/

public interface StyleInterface {
    String GreenColor = "#00ce7a;"; // Зеленый
    String YellowColor = "#ffbd3f;"; // Желтый
    String RedColor = "#ff6874;"; // Красный
    String FieldBorderColor = "#3d3d3d;"; // Цвет бортиков любого поля
    String BoxBackgroundColor = "#f2f2f2;"; // Легкий серый, чтобы создать карточку игры, отделив ее от заднего плана
    String errorFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + RedColor + " -fx-border-radius: 3";
    String normalFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + FieldBorderColor + " -fx-border-radius: 3";
    String initialScoreStyle = "-fx-background-color: #ffffff;-fx-border-color: #3d3d3d;";
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
