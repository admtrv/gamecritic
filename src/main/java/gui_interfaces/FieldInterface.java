package gui_interfaces;

import javafx.fxml.FXML;

/**
* Interface that extends {@link StyleInterface} by inheriting
* its constants, and defines methods to set the styles
* of field elements depending on the state of data validation.
*/

public interface FieldInterface extends StyleInterface {

    /**
     * Sets style of the username field to indicate an error after failing validation.
     */
    @FXML void setUsernameErrorStyle();

    /**
     * Sets style of the password field to indicate an error after failing validation.
     */
    @FXML void setPasswordErrorStyle();

    /**
     * Restores username field to its normal style after correcting a previous error or initially.
     */
    @FXML void setUsernameNormalStyle();

    /**
     * Restores password field to its normal style after correcting a previous error or initially.
     */
    @FXML void setPasswordNormalStyle();
}
