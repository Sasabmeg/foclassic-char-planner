package net.fodev.foclassic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class RegisterController {

    @FXML
    private Button buttonPlusStrength;
    @FXML
    private Button buttonMinusStrength;
    @FXML
    private Button buttonMinusPerception;

    @FXML
    private void initialize() {
        handleClickEvent();
    }

    private void handleClickEvent() {
        buttonPlusStrength.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("OnAction {}" + event);
            }
        });

        buttonPlusStrength.setOnAction(event -> System.out.println("OnAction {} " + event));
    }
}
