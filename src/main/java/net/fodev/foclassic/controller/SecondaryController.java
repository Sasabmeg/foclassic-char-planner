package net.fodev.foclassic.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import net.fodev.foclassic.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("fxml/primary");
    }

}