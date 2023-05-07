package net.fodev.foclassic.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import net.fodev.foclassic.App;
import net.fodev.foclassic.model.FoCharacter;
import net.fodev.foclassic.model.FoCharacterFactory;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("fxml/secondary");
    }

    @FXML
    private void switchToRegister() throws IOException {
        System.out.println("PrimaryController::loadFXML(\"" + "fxml/register" + "\")");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/register.fxml"));
        System.out.println("PrimaryController::fxmlLoader.load()");
        RegisterController registerController = new RegisterController();
        registerController.setFoCharacter(FoCharacterFactory.createNewCharacter("Raging Newbie", 15, "Femme Fatale"));
        fxmlLoader.setController(registerController);
        Parent parent = fxmlLoader.load();
        System.out.println(fxmlLoader.getController().toString());
        App.setRoot(parent);
    }
}
