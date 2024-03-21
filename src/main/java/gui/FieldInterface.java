package gui;

import javafx.fxml.FXML;

/*
Интерфейс, который расширяет ColorInterface, наследуя его константы,
и определяет  методы для установки стилей элементов полей
для ввода данных в зависимости от состояния валидации данных.
*/

public interface FieldInterface extends ColorInterface {

    String errorFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + RedColor + " -fx-border-radius: 3";
    String normalFieldStyle = "-fx-background-color:  #ffffff; -fx-border-color:" + FieldBorderColor + " -fx-border-radius: 3";

    @FXML void setUsernameErrorStyle();

    @FXML void setPasswordErrorStyle();

    @FXML void setUsernameNormalStyle();

    @FXML void setPasswordNormalStyle();
}
