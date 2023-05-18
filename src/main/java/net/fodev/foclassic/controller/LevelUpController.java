package net.fodev.foclassic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.fodev.foclassic.App;
import net.fodev.foclassic.model.*;

import java.util.List;

public class LevelUpController extends CharacterController {

    private FoCharacter oldFoCharacter;
    @FXML private ListView listViewPerks;

    public void setFoCharacter(FoCharacter foCharacter) {
        System.out.println("LevelUpController::setFoCharacter(FoCharacter foCharacter)");
        this.foCharacter = foCharacter;
    }

    @FXML
    @Override
    protected void initialize() {
        System.out.println("RegisterController::initialize()");
        super.initialize();
        oldFoCharacter = FoCharacterFactory.copy(foCharacter);
        initListView();
    }

    private void initListView() {
        App.getScene().focusOwnerProperty().addListener((prop, oldNode, newNode) -> {
            System.out.println("App.getScene().focusOwnerProperty() -> " + prop + " / " + oldNode + " / " + newNode);
        });
        updateListView();
        listViewPerks.focusedProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("LevelUpController::initListView()::listViewPerks.focusedProperty() " + oldVal + " / " + newVal);
        });
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

    private void updateListView() {
        List items = listViewPerks.getItems();
        items.clear();
        items.add("-------------   TRAITS   -----------");
        foCharacter.getTraits().stream().filter(Trait::isTagged).forEach(items::add);
        items.add("-------------   PERKS    -----------");
        items.add("--------   SUPPORT PERKS    --------");
    }
}
