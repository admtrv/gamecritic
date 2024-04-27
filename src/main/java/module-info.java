/**
 * Documentation for "gamecritic" application.
 */
module gamecritic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    exports aggregation;
    exports game;
    exports gui;
    exports gui_interfaces;
    exports logger_decorator;
    exports profile_strategy;
    exports reviews;
    exports session;
    exports users;
    exports utils;
    exports validation_factory;

    opens gui to javafx.fxml;
    opens gui_interfaces to javafx.fxml;
}