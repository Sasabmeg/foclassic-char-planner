package net.fodev.foclassic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import net.fodev.foclassic.App;
import net.fodev.foclassic.model.fochar.Skill;
import net.fodev.foclassic.model.fochar.Special;
import net.fodev.foclassic.model.fochar.Trait;
import net.fodev.foclassic.model.fochar.TraitFactory;

import java.io.IOException;

public class RegisterController extends CharacterController {

    @FXML private ImageView imageViewTagPoint;
    @FXML private ImageView imageViewUnusedSpecialPointLeft;
    @FXML private ImageView imageViewUnusedSpecialPointRight;

    @FXML
    @Override
    protected void initialize() {
        super.initialize();
        System.out.println("Register new character.");
        handleTraitLabelClickEvents();
        handleSkillButtonClickEvents();
        handleTraitButtonClickEvents();
        handleSpecialButtonClickEvent();
        handleOtherButtonClickEvents();
        setNumericValues();
    }

    //  Buttons: Done, Back
    private void handleOtherButtonClickEvents() {
        buttonDone.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/levelUp.fxml"));
            LevelUpController levelUpController = new LevelUpController();
            levelUpController.setFoCharacter(foCharacter);
            fxmlLoader.setController(levelUpController);
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            App.setRoot(parent);
        });
        buttonBack.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/primary.fxml"));
            try {
                App.setRoot("fxml/primary");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setNumericValues() {
        showTagPointsValue(foCharacter.getUnusedTagPoints());
        showUnusedSpecialPointsValue(foCharacter.getUnusedSpecialPoints());
    }

    private void showTagPointsValue(int value) {
        imageViewTagPoint.setImage(bigNum);
        Rectangle2D imagePart = new Rectangle2D(value * BIG_NUM_WIDTH, 2, BIG_NUM_WIDTH, BIG_NUM_HEIGHT);
        imageViewTagPoint.setViewport(imagePart);
    }

    private void showUnusedSpecialPointsValue(int value) {
        showDoubleDigitNumber(imageViewUnusedSpecialPointLeft, imageViewUnusedSpecialPointRight, value);
    }

    private boolean canTagSkill() {
        return foCharacter.getUnusedTagPoints() > 0;
    }

    private void handleTraitButtonClickEvents() {
        backgroundPane.getChildren()
                .filtered(node -> node instanceof Button)
                .filtered(button -> button.getId().startsWith("buttonTrait"))
                .forEach(button -> {
                    ((Button)button).setOnAction(event -> {
                        int index = Integer.parseInt(button.getId().substring("buttonTrait".length()));
                        if (index != 8 && index != 16 ) {
                            Trait trait = foCharacter.getTrait(index - 1);
                            updateDescriptionText(trait.getName(), trait.getDescription(), trait.getImage());
                            if (foCharacter.canTagTrait() || foCharacter.isTaggedTrait(index - 1)) {
                                switch(trait.getName()) {
                                    case TraitFactory.BRUISER:
                                        if (!foCharacter.isTaggedTrait(index - 1)) {
                                            if (foCharacter.getStrength() <= 6) {
                                                foCharacter.tagTrait(index - 1);
                                                foCharacter.setStrength(foCharacter.getStrength() + 4);
                                            } else {
                                                System.out.println("Cannot tag Bruiser because ST would overflow max stat of 10.");
                                            }
                                        } else {
                                            if (foCharacter.getStrength() >= 5) {
                                                foCharacter.untagTrait(index - 1);
                                                foCharacter.setStrength(foCharacter.getStrength() - 4);
                                            } else {
                                                System.out.println("Cannot untag Bruiser because ST would underflow min stat of 1.");
                                            }
                                        }
                                        break;
                                    case TraitFactory.BONEHEAD:
                                        if (!foCharacter.isTaggedTrait(index - 1)) {
                                            if (foCharacter.getIntellect() > 1) {
                                                foCharacter.tagTrait(index - 1);
                                                foCharacter.setIntellect(foCharacter.getIntellect() - 1);
                                            } else {
                                                System.out.println("Cannot tag Bonehead because IN would underflow min stat of 1.");
                                            }
                                        } else {
                                            if (foCharacter.getIntellect() < 10) {
                                                foCharacter.untagTrait(index - 1);
                                                foCharacter.setIntellect(foCharacter.getIntellect() + 1);
                                            } else {
                                                System.out.println("Cannot untag Bonehead because IN would overflow max stat of 10.");
                                            }
                                        }
                                        break;
                                    default:
                                        if (!foCharacter.isTaggedTrait(index - 1)) {
                                            foCharacter.tagTrait(index - 1);
                                        } else {
                                            foCharacter.untagTrait(index - 1);
                                        }
                                }
                                refreshTraitLabelColor(index);
                                FoCharacterRuleset.updateTraits(foCharacter);
                                updateStatLabelValues();
                                updateSpecialPointsValues();
                                updateStatLabelValues();
                            } else {
                                System.out.println("Cannot tag trait: No more free trait points left.");
                            }
                        } else {
                            System.out.println("This trait is disabled in this version.");
                        }
                    });
                });
    }

    private void refreshTraitLabelColor(int index) {
        backgroundPane.getChildren()
                .filtered(node -> node instanceof Label)
                .filtered(l -> l.getId() != null)
                .filtered(l -> l.getId().equals("textTrait" + index))
                .forEach(l -> {
                    Label label = (Label)l;
                    if (foCharacter.isTaggedTrait(index - 1)) {
                        label.setStyle("-fx-text-fill: #CBCBCB;");
                    } else {
                        label.setStyle("-fx-text-fill: #38FB00;");
                    }
                });
    }


    private void handleSkillButtonClickEvents() {
        backgroundPane.getChildren()
                .filtered((node -> node instanceof Button))
                .filtered(button -> button.getId().startsWith("buttonTagSkill"))
                .forEach(button -> {
                    ((Button)button).setOnAction(event -> {
                        int index = Integer.parseInt(button.getId().substring("buttonTagSkill".length()));
                        Skill skill = foCharacter.getSkill(index - 1);
                        updateDescriptionText(skill.getName(), skill.getDescription(), skill.getImage());
                        if (canTagSkill() || foCharacter.isTaggedSkill(index - 1)) {
                            if (!foCharacter.isTaggedSkill(index - 1)) {
                                foCharacter.tagSkill(index - 1);
                                showTagPointsValue(foCharacter.getUnusedTagPoints());
                            } else {
                                foCharacter.untagSkill(index - 1);
                                showTagPointsValue(foCharacter.getUnusedTagPoints());
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
                                        } else {
                                            ((Label)l).setStyle("-fx-text-fill: #38FB00;");
                                            if (l.getId().equals("labelSkillRight" + index)) {
                                                ((Label) l).setText("" + foCharacter.getSkillValue(index - 1) + "%");
                                            }
                                        }
                                    });
                        } else {
                            System.out.println("Cannot tag skill: No more tag points left.");
                        }
                    } );
                });
    }

    private void handleTraitLabelClickEvents() {
        backgroundPane.getChildren()
                .filtered(node -> node instanceof Label)
                .filtered(node -> node != null && node.getId() != null)
                .forEach(node -> {
                    if (node.getId().contains("textTrait")) {
                        int index = Integer.parseInt(node.getId().substring("textTrait".length()));
                        if (index != 8 && index != 16) {
                            (node).setOnMouseClicked(mouseEvent -> {
                                Trait trait = foCharacter.getTrait(index - 1);
                                updateDescriptionText(trait.getName(), trait.getDescription(), trait.getImage());
                            });
                        }
                    }
                });
    }

    private void handleSpecialButtonClickEvent() {
        backgroundPane.getChildren()
                .filtered((node -> node instanceof Button))
                .filtered(node -> node != null && node.getId() != null)
                .filtered(node -> node.getId().startsWith("buttonSpecial"))
                .forEach(node -> {
                    Button button = (Button)node;
                    int index;
                    if (button.getId().startsWith("buttonSpecialPlus")) {
                        index = Integer.parseInt(node.getId().substring("buttonSpecialPlus".length()));
                        button.setOnMouseClicked(mouseEvent -> {
                            Special special = foCharacter.getSpecial(index - 1);
                            updateDescriptionText(special.getName(), special.getDescription(), special.getImage());
                            if (special.getValue() < 10) {
                                if (foCharacter.getUnusedSpecialPoints() > 0) {
                                    special.increase(1);
                                    foCharacter.setUnusedSpecialPoints(foCharacter.getUnusedSpecialPoints() - 1);
                                    FoCharacterRuleset.updateSpecial(foCharacter, index -1, 1);
                                    showUnusedSpecialPointsValue(foCharacter.getUnusedSpecialPoints());
                                    showSpecialPointsValue(index -1 , special.getValue());
                                    updateSKillLabelValues();
                                    updateStatLabelValues();
                                    backgroundPane.getChildren()
                                            .filtered(l -> l instanceof Label)
                                            .filtered(l -> l.getId() != null)
                                            .filtered(l -> l.getId().equals("textStatsInfo" + index))
                                            .forEach(l -> {
                                                Label label = (Label)l;
                                                label.setText(foCharacter.getSpecial(index - 1).getValueRank());
                                            });
                                } else {
                                    System.out.println("No more unused special points left.");
                                }
                            } else {
                                System.out.println("Cannot increase special values above 10.");
                            }
                        });
                    } else {
                        index = Integer.parseInt(node.getId().substring("buttonSpecialMinus".length()));
                        button.setOnMouseClicked(mouseEvent -> {
                            Special special = foCharacter.getSpecial(index - 1);
                            updateDescriptionText(special.getName(), special.getDescription(), special.getImage());
                            if (special.getValue() > 1) {
                                special.decrease(1);
                                foCharacter.setUnusedSpecialPoints(foCharacter.getUnusedSpecialPoints() + 1);
                                FoCharacterRuleset.updateSpecial(foCharacter, index -1, -1);
                                showUnusedSpecialPointsValue(foCharacter.getUnusedSpecialPoints());
                                showSpecialPointsValue(index -1 , special.getValue());
                                updateSKillLabelValues();
                                updateStatLabelValues();
                                backgroundPane.getChildren()
                                        .filtered(l -> l instanceof Label)
                                        .filtered(l -> l.getId() != null)
                                        .filtered(l -> l.getId().equals("textStatsInfo" + index))
                                        .forEach(l -> {
                                            Label label = (Label)l;
                                            label.setText(foCharacter.getSpecial(index - 1).getValueRank());
                                        });
                            } else {
                                System.out.println("Cannot increase special values below 1.");
                            }
                        });
                    }
                });
    }
}
