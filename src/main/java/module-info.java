module net.fodev.foclassic {
    requires javafx.controls;
    requires javafx.fxml;

    opens net.fodev.foclassic to javafx.fxml;
    exports net.fodev.foclassic;
}
