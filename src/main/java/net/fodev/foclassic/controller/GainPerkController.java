package net.fodev.foclassic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import net.fodev.foclassic.CharPlannerApp;
import net.fodev.foclassic.model.dialog.DialogAnswerNode;
import net.fodev.foclassic.model.dialog.DialogFactory;
import net.fodev.foclassic.model.dialog.DialogQuestionNode;
import net.fodev.foclassic.model.fochar.CombatPerk;
import net.fodev.foclassic.model.fochar.FoCharacter;
import net.fodev.foclassic.model.fochar.PerkFactory;
import net.fodev.foclassic.model.fochar.SupportPerk;
import net.fodev.foclassic.view.DialogFormatCell;

import java.net.URL;
import java.util.List;

public class GainPerkController {

    @Getter @Setter private FoCharacter foCharacter;
    @Getter @Setter private DialogQuestionNode currentDialog;

    @FXML Label labelInfoCaption;
    @FXML Label labelInfoDescription;
    @FXML ImageView imageViewInfoImage;

    @FXML ListView listViewPerks;

    @FXML Button buttonDone;
    @FXML Button buttonBack;

    @FXML
    public void initialize() {
        handleButtonEvents();
        initDialogs();
    }

    private void initDialogs() {
        currentDialog = DialogFactory.createChooseCombatPerkDialog(foCharacter);
        listViewPerks.setFocusTraversable(false);
        listViewPerks.setCellFactory((Callback<ListView<DialogAnswerNode>, ListCell<DialogAnswerNode>>) dialogAnswerNodeListView -> new DialogFormatCell());
        listViewPerks.setOnMouseClicked(mouseEvent -> {
            System.out.println("Selected Dialog Answer: #" + (1 + listViewPerks.getSelectionModel().getSelectedIndex())
                    + " = " + listViewPerks.getSelectionModel().getSelectedItem());
            if (listViewPerks.getSelectionModel().getSelectedItem() instanceof DialogAnswerNode) {
                DialogAnswerNode selectedItem = (DialogAnswerNode)listViewPerks.getSelectionModel().getSelectedItem();
                if (selectedItem.areDemandsMet()) {
                    updateDescriptionTextFromDialog(selectedItem);
                } else {
                    updateDescriptionTextFromDialog(selectedItem);
                    if (selectedItem.getDemandErrors().length() > 2) {
                        System.out.println(selectedItem.getDemandErrors());
                    } else {
                        System.out.println("Demands for '" + selectedItem + "' not met!");
                    }
                    listViewPerks.getSelectionModel().clearSelection();
                }
            };
        });
        updatePerkListView();
    }

    private void handleButtonEvents() {
        buttonDone.setOnMouseClicked(mouseEvent -> {
            System.out.println("Done.");
            ((Stage)buttonBack.getScene().getWindow()).close();
        });
        buttonBack.setOnMouseClicked(mouseEvent -> {
            ((Stage)buttonBack.getScene().getWindow()).close();
        });
    }

    private void updatePerkListView() {
        List items = listViewPerks.getItems();
        items.clear();
        currentDialog.getAnswers().forEach(a -> items.add(a));
    }

    protected void updateDescriptionText(String name, String description, String image) {
        labelInfoCaption.setText(name);
        labelInfoDescription.setText(description);
        URL url = CharPlannerApp.class.getResource(image);
        Image img = new Image(url.toString());
        imageViewInfoImage.setImage(img);
    }

    private void updateDescriptionTextFromDialog(DialogAnswerNode selectedItem) {
        CombatPerk cp = PerkFactory.getCombatPerk(selectedItem.getMessage());
        if (cp != null) {
            updateDescriptionText(cp.getName(), cp.getDescription(), cp.getImage());
        } else {
            if (selectedItem.getMessage().equals("Gain one level.") || selectedItem.getMessage().equals("Gain three levels.")) {
                updateDescriptionText("Level", "The general competency of the player character. A measure of your experience and abilities.", "images/skilldex/LEVEL.png");
            }
            if (selectedItem.getMessage().equals("Mutate.")) {
                updateDescriptionText("Mutate", "The radiation of the wasteland has changed you! One of your traits has mutated into something else...", "images/skilldex/MUTATE.png");
            }
        }
    }

}
