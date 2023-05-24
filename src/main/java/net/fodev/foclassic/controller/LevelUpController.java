package net.fodev.foclassic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import net.fodev.foclassic.model.dialog.DialogAnswerNode;
import net.fodev.foclassic.model.dialog.DialogFactory;
import net.fodev.foclassic.model.dialog.DialogQuestionNode;
import net.fodev.foclassic.model.fochar.*;

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
        showUnusedSkillPointsValue(foCharacter.getUnusedSkillPoints());
    }

    private void initDialogs() {
        currentDialog = DialogFactory.createRoot();
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
        listViewDialogAnswer.setOnMouseClicked(mouseEvent -> {
            System.out.println("Selected Dialog Answer: #" + (1 + listViewDialogAnswer.getSelectionModel().getSelectedIndex())
                    + " = " + listViewDialogAnswer.getSelectionModel().getSelectedItem());
            if (listViewDialogAnswer.getSelectionModel().getSelectedItem() instanceof DialogAnswerNode) {
                DialogAnswerNode selectedItem = (DialogAnswerNode)listViewDialogAnswer.getSelectionModel().getSelectedItem();
                currentDialog = selectedItem.getQuestion();
                updateDialogAnswerListView();
                updateDialogQuestionView();
            };
        });

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
        });
    }

    private void updatePerkListView() {
        List items = listViewPerks.getItems();
        items.clear();
        items.add("-------------   TRAITS   -----------");
        foCharacter.getTraits().stream().filter(Trait::isTagged).forEach(items::add);
        items.add("-------------   PERKS    -----------");
        items.add("--------   SUPPORT PERKS    --------");
    }

    private void showUnusedSkillPointsValue(int value) {
        showDoubleDigitNumber(imageViewTagPointLeft, imageViewTagPointRight, value);
    }
}
