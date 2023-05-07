module net.fodev.foclassic {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens net.fodev.foclassic to javafx.fxml;
    exports net.fodev.foclassic;
    exports net.fodev.foclassic.controller;
    opens net.fodev.foclassic.controller to javafx.fxml;
}
