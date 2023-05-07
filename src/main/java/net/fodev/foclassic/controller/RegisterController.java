package net.fodev.foclassic.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import net.fodev.foclassic.model.FoCharacter;

public class RegisterController {
    private FoCharacter foCharacter;

    @FXML
    private Button buttonPlusStrength;
    @FXML
    private Button buttonMinusStrength;
    @FXML
    private Button buttonMinusPerception;

    @FXML
    private Pane backgroundPane;

    @FXML
    private void initialize() {
        System.out.println("RegisterController::initialize()");
        handleClickEvent();
        //handleLabelClickEvents();
        handleSkillButtonClickEvents();
    }

    public void setFoCharacter(FoCharacter foCharacter) {
        System.out.println("RegisterController::setFoCharacter(FoCharacter foCharacter)");
        this.foCharacter = foCharacter;
    }

    private boolean canTagSkill() {
        return foCharacter.getUnusedTagPoints() > 0;
    }


    private void handleSkillButtonClickEvents() {
        backgroundPane.getChildren()
                .filtered((node -> node instanceof Button))
                .filtered(button -> button.getId().startsWith("buttonTagSkill"))
                .forEach(button -> {
                    ((Button)button).setOnAction(event -> {
                        int index = Integer.parseInt(button.getId().substring("buttonTagSkill".length()));
                        System.out.println("Index = " + index);
                        if (canTagSkill() || foCharacter.isTaggedSkill(index - 1)) {
                            System.out.println("RegisterController::handleSKilButtonClickEvents(): Old skill value = " + foCharacter.getSkills().get(index - 1).getValue());
                            if (!foCharacter.isTaggedSkill(index - 1)) {
                                foCharacter.tagSkill(index - 1);
                            } else {
                                foCharacter.untagSkill(index - 1);
                            }
                            backgroundPane.getChildren()
                                    .filtered(node -> node instanceof Label)
                                    .filtered(l -> l.getId() != null)
                                    .filtered(l -> l.getId().equals("labelSkillLeft" + index) || l.getId().equals("labelSkillRight" + index))
                                    .forEach(l -> {
                                        if (foCharacter.isTaggedSkill(index - 1)) {
                                            ((Label)l).setStyle("-fx-text-fill: #CBCBCB;");
                                            if (l.getId().equals("labelSkillRight" + index)) {
                                                ((Label) l).setText("" + foCharacter.getSkillValue(index - 1) + "%");
                                            }
                                            System.out.println("Tagged skill: " + foCharacter.getSkillName(index - 1) + "\t" + l);
                                        } else {
                                            ((Label)l).setStyle("-fx-text-fill: #38FB00;");
                                            if (l.getId().equals("labelSkillRight" + index)) {
                                                ((Label) l).setText("" + foCharacter.getSkillValue(index - 1) + "%");
                                            }
                                            System.out.println("Untagged skill: " + foCharacter.getSkillName(index - 1) + "\t" + l);
                                        }
                                    });
                        } else {
                            System.out.println("Cannot tag skill: No more tag points left.");
                        }
                    } );
                });
    }

    private void handleLabelClickEvents() {
        backgroundPane.getChildren()
                .filtered(node -> node instanceof Label)
                .forEach(node -> {
                    if (node.getStyleClass().toString().contains("textTrait")) {
                        System.out.println("Trait found: " + node.getStyleClass());
                        ((Label)node).setOnMouseClicked(mouseEvent -> {
                            ((Label)node).setStyle("-fx-text-fill: #CBCBCB;");
                            System.out.println(mouseEvent);
                            System.out.println(((Label)mouseEvent.getSource()).getId());
                        });
                    }
                });
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
