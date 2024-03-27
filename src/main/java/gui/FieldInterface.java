package gui;

import javafx.fxml.FXML;

/*
Интерфейс, который расширяет StyleInterface, наследуя его константы,
и определяет  методы для установки стилей элементов полей
для ввода данных в зависимости от состояния валидации данных.
*/

public interface FieldInterface extends StyleInterface {

    @FXML void setUsernameErrorStyle();

    @FXML void setPasswordErrorStyle();

    @FXML void setUsernameNormalStyle();

    @FXML void setPasswordNormalStyle();
}
