package gui_interfaces;

import javafx.fxml.FXML;

/*
An interface that extends StyleInterface by inheriting
its constants, and defines methods to set the styles
of field elements depending on the state of data validation_factory.
*/

public interface FieldInterface extends StyleInterface {

    @FXML void setUsernameErrorStyle();

    @FXML void setPasswordErrorStyle();

    @FXML void setUsernameNormalStyle();

    @FXML void setPasswordNormalStyle();
}
