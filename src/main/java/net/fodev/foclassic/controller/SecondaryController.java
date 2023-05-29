package net.fodev.foclassic.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import net.fodev.foclassic.CharPlannerApp;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        CharPlannerApp.setRoot("fxml/primary");
    }

}