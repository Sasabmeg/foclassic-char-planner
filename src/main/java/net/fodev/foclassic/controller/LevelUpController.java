package net.fodev.foclassic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.fodev.foclassic.CharPlannerApp;
import net.fodev.foclassic.model.dialog.DialogAnswerNode;
import net.fodev.foclassic.model.dialog.DialogFactory;
import net.fodev.foclassic.model.dialog.DialogQuestionNode;
import net.fodev.foclassic.model.fochar.*;
import net.fodev.foclassic.view.DialogFormatCell;

import java.io.IOException;
import java.util.List;

public class LevelUpController extends CharacterController {

    private FoCharacter oldFoCharacter;
    private DialogQuestionNode currentDialog;

    @FXML private ListView listViewPerks;
    @FXML private ListView listViewDialogAnswer;
    @FXML private ImageView imageViewTagPointLeft;
    @FXML private ImageView imageViewTagPointRight;
    @FXML private TextArea textAreaConsoleOut;
    @FXML private TextArea textAreaDialogQuestion;
    @FXML private Label labelLevel;
    @FXML private Label labelExperience;
    @FXML private Label labelNextLevel;

    public void setFoCharacter(FoCharacter foCharacter) {
        System.out.println("LevelUpController::setFoCharacter(FoCharacter foCharacter)");
        this.foCharacter = foCharacter;
    }

    @FXML
    @Override
    protected void initialize() {
        super.initialize();
        System.out.println("Level up or mutate character.");
        oldFoCharacter = FoCharacterFactory.copy(foCharacter);
        initPerkListView();
        initDialogs();
        initDialogQuestion();
        initDialogAnswer();
        initLabels();
        updateLevelAndExperience();
        showUnusedSkillPointsValue();
    }

    private void initLabels() {
        labelLevel.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Level", "The general competency of the player character. A measure of your experience and abilities.", "images/skilldex/LEVEL.png");
        });
        labelExperience.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Experience", "A reward for completing specific tasks, or defeating enemies in combat. More experience points are required to attain higher levels.", "images/skilldex/EXPER.png");
        });
        labelNextLevel.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Next Level", "The amount of experience points needed to move up to the next level.", "images/skilldex/LEVELNXT.png");
        });
    }

    private void updateLevelAndExperience() {
        labelLevel.setText("Level: " + foCharacter.getLevel());
        labelExperience.setText("Experience: " + String.format("%,d", foCharacter.getExperience()));
        labelNextLevel.setText("Next level: " + String.format("%,d", foCharacter.getExpForNextLevel()));
    }

    private void initDialogs() {
        currentDialog = DialogFactory.createMainDialog(foCharacter);
    }

    private void initDialogQuestion() {
        textAreaDialogQuestion.setFocusTraversable(false);
        textAreaDialogQuestion.setEditable(false);
        updateDialogQuestionView();
    }

    private void updateDialogQuestionView() {
        textAreaDialogQuestion.setText(currentDialog.getMessage());
    }

    private void initDialogAnswer() {
        updateDialogAnswerListView();
        listViewDialogAnswer.setFocusTraversable(false);
        listViewDialogAnswer.setCellFactory((Callback<ListView<DialogAnswerNode>, ListCell<DialogAnswerNode>>) dialogAnswerNodeListView -> new DialogFormatCell());
        listViewDialogAnswer.setOnMouseClicked(mouseEvent -> {
            System.out.println("Selected Dialog Answer: #" + (1 + listViewDialogAnswer.getSelectionModel().getSelectedIndex())
                    + " = " + listViewDialogAnswer.getSelectionModel().getSelectedItem());
            if (listViewDialogAnswer.getSelectionModel().getSelectedItem() instanceof DialogAnswerNode) {
                DialogAnswerNode selectedItem = (DialogAnswerNode)listViewDialogAnswer.getSelectionModel().getSelectedItem();
                if (selectedItem.areDemandsMet()) {
                    selectedItem.applyResults();
                    updateDescriptionTextFromDialog(selectedItem);
                    currentDialog = selectedItem.getQuestion();
                    updateDialogAnswerListView();
                    updateDialogQuestionView();
                    updateLevelAndExperience();
                    showUnusedSkillPointsValue();
                    updatePerkListView();
                } else {
                    updateDescriptionTextFromDialog(selectedItem);
                    if (selectedItem.getDemandErrors().length() > 2) {
                        System.out.println(selectedItem.getDemandErrors());
                    } else {
                        System.out.println("Demands for '" + selectedItem + "' not met!");
                    }
                    listViewDialogAnswer.getSelectionModel().clearSelection();
                }
            };
        });
    }

    private void updateDescriptionTextFromDialog(DialogAnswerNode selectedItem) {
        SupportPerk sp = PerkFactory.getSupportPerk(selectedItem.getMessage());
        if (sp != null) {
            updateDescriptionText(sp.getName(), sp.getDescription(), sp.getImage());
        } else {
            if (selectedItem.getMessage().equals("Gain one level.") || selectedItem.getMessage().equals("Gain three levels.")) {
                updateDescriptionText("Level", "The general competency of the player character. A measure of your experience and abilities.", "images/skilldex/LEVEL.png");
            }
            if (selectedItem.getMessage().equals("Mutate.")) {
                updateDescriptionText("Mutate", "The radiation of the wasteland has changed you! One of your traits has mutated into something else...", "images/skilldex/MUTATE.png");
            }
        }
    }

    private void updateDialogAnswerListView() {
        List items = listViewDialogAnswer.getItems();
        items.clear();
        currentDialog.getAnswers().forEach(a -> items.add(a));
    }

    private void initPerkListView() {
        updatePerkListView();
        listViewPerks.setFocusTraversable(false);
        listViewPerks.setOnMouseClicked(mouseEvent -> {
            System.out.println("selected item = " + listViewPerks.getSelectionModel().getSelectedItem());
            if (listViewPerks.getSelectionModel().getSelectedItem() instanceof Trait) {
                Trait selectedItem = (Trait)listViewPerks.getSelectionModel().getSelectedItem();
                updateDescriptionText(selectedItem.getName(), selectedItem.getDescription(), selectedItem.getImage());
            };
            if (listViewPerks.getSelectionModel().getSelectedItem() instanceof Perk) {
                Perk selectedItem = (Perk)listViewPerks.getSelectionModel().getSelectedItem();
                updateDescriptionText(selectedItem.getName(), selectedItem.getDescription(), selectedItem.getImage());
            };
            if (listViewPerks.getSelectionModel().getSelectedItem() instanceof SupportPerk) {
                SupportPerk selectedItem = (SupportPerk)listViewPerks.getSelectionModel().getSelectedItem();
                updateDescriptionText(selectedItem.getName(), selectedItem.getDescription(), selectedItem.getImage());
            };
            if (listViewPerks.getSelectionModel().getSelectedItem() instanceof String) {
                if (((String) listViewPerks.getSelectionModel().getSelectedItem()).contains("PERKS")
                        && !((String) listViewPerks.getSelectionModel().getSelectedItem()).contains("SUPPORT")
                        && foCharacter.hasMissingPerk()) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(CharPlannerApp.class.getResource("fxml/gainPerk.fxml"));
                        GainPerkController gainPerkController = new GainPerkController();
                        gainPerkController.setFoCharacter(foCharacter);
                        fxmlLoader.setController(gainPerkController);
                        Parent parent = fxmlLoader.load();
                        Stage gainPerkStage = new Stage();
                        Scene gainPerkScene = new Scene(parent, 573, 230);
                        Scene parentScene = listViewPerks.getScene();
                        double pX = parentScene.getWindow().getX();
                        double pY = parentScene.getWindow().getY();
                        double pW = parentScene.getWindow().getWidth();
                        double pH = parentScene.getWindow().getHeight();
                        gainPerkStage.setX(pX + pW / 2 - gainPerkScene.getWidth() / 2);
                        gainPerkStage.setY(pY + pH / 4 - gainPerkScene.getHeight() / 2);
                        gainPerkStage.setScene(gainPerkScene);
                        gainPerkStage.setResizable(false);
                        gainPerkStage.initStyle(StageStyle.UNDECORATED);
                        gainPerkStage.initOwner(listViewPerks.getScene().getWindow());
                        gainPerkStage.initModality(Modality.WINDOW_MODAL);
                        gainPerkStage.showAndWait();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void updatePerkListView() {
        List items = listViewPerks.getItems();
        items.clear();
        items.add("-------------   TRAITS   -----------");
        foCharacter.getTraits().stream().filter(Trait::isTagged).forEach(items::add);
        items.add("-------------   PERKS    -----------");
        foCharacter.getCombatPerks().stream().forEach(items::add);
        items.add("--------   SUPPORT PERKS    --------");
        foCharacter.getSupportPerks().forEach(items::add);
    }

    private void showUnusedSkillPointsValue() {
        showDoubleDigitNumber(imageViewTagPointLeft, imageViewTagPointRight, foCharacter.getUnusedSkillPoints());
    }
}
