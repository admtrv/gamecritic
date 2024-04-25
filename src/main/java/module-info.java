module com.example.gamecritic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens gui to javafx.fxml;
    exports gui;
    exports gui_interfaces;
    opens gui_interfaces to javafx.fxml;
}