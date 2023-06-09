package net.fodev.foclassic.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import net.fodev.foclassic.CharPlannerApp;
import net.fodev.foclassic.model.fochar.FoCharacterFactory;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        CharPlannerApp.setRoot("fxml/secondary");
    }

    @FXML
    private void switchToExit() throws IOException {
        Platform.exit();
    }

    @FXML
    private void switchToRegister() throws IOException {
        System.out.println("PrimaryController::loadFXML(\"" + "fxml/register" + "\")");
        FXMLLoader fxmlLoader = new FXMLLoader(CharPlannerApp.class.getResource("fxml/register.fxml"));
        System.out.println("PrimaryController::fxmlLoader.load()");
        RegisterController registerController = new RegisterController();
        registerController.setFoCharacter(FoCharacterFactory.createNewCharacter("Raging Newbie", 15, "Femme Fatale"));
        fxmlLoader.setController(registerController);
        Parent parent = fxmlLoader.load();
        CharPlannerApp.setRoot(parent);
    }
}
