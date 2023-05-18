package net.fodev.foclassic.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.fodev.foclassic.App;
import net.fodev.foclassic.model.FoCharacter;
import net.fodev.foclassic.model.Skill;
import net.fodev.foclassic.model.Special;

import java.net.URL;

public class CharacterController {
    protected static final int BIG_NUM_WIDTH = 14;
    protected static final int BIG_NUM_HEIGHT = 20;
    protected FoCharacter foCharacter;
    protected Image bigNum;
    @FXML protected Pane backgroundPane;
    @FXML protected TextField textName;
    @FXML protected TextField textAge;
    @FXML protected TextField textSex;
    @FXML protected Label labelInfoCaption;
    @FXML protected Label labelInfoDescription;
    @FXML protected ImageView imageViewInfoImage;
    @FXML protected Label labelHitPoints;
    @FXML protected Label labelPoisoned;
    @FXML protected Label labelRadiated;
    @FXML protected Label labelEyeDamage;
    @FXML protected Label labelCrippledRightArm;
    @FXML protected Label labelCrippledLeftArm;
    @FXML protected Label labelCrippledRightLeg;
    @FXML protected Label labelCrippledLeftLeg;
    @FXML protected Label labelArmorClassName;
    @FXML protected Label labelActionPointsName;
    @FXML protected Label labelCarryWeightName;
    @FXML protected Label labelMeleeDamageName;
    @FXML protected Label labelDamageResName;
    @FXML protected Label labelPoisonResName;
    @FXML protected Label labelRadiationResName;
    @FXML protected Label labelSequenceName;
    @FXML protected Label labelHealingRateName;
    @FXML protected Label labelCriticalChanceName;
    @FXML protected Label labelArmorClassValue;
    @FXML protected Label labelActionPointsValue;
    @FXML protected Label labelCarryWeightValue;
    @FXML protected Label labelMeleeDamageValue;
    @FXML protected Label labelDamageResValue;
    @FXML protected Label labelPoisonResValue;
    @FXML protected Label labelRadiationResValue;
    @FXML protected Label labelSequenceValue;
    @FXML protected Label labelHealingRateValue;
    @FXML protected Label labelCriticalChanceValue;
    @FXML protected Button buttonDone;
    @FXML protected Button buttonBack;

    @FXML
    protected void initialize() {
        System.out.println("CharacterController::initialize()");
        initImages();
        handleBaseLabelClickEvents();
        updateSKillLabelValues();
        updateSpecialPointsValues();
        updateStatLabelValues();
    }

    protected void updateSpecialPointsValues() {
        showSpecialPointsValue(0, foCharacter.getStrength());
        showSpecialPointsValue(1, foCharacter.getPerception());
        showSpecialPointsValue(2, foCharacter.getEndurance());
        showSpecialPointsValue(3, foCharacter.getCharisma());
        showSpecialPointsValue(4, foCharacter.getIntellect());
        showSpecialPointsValue(5, foCharacter.getAgility());
        showSpecialPointsValue(6, foCharacter.getLuck());
    }

    protected void showSpecialPointsValue(int index, int value) {
        backgroundPane.getChildren()
                .filtered(node -> node instanceof ImageView)
                .filtered(iv -> iv != null && iv.getId() != null)
                .forEach(node -> {
                    ImageView imageView = (ImageView) node;
                    if (imageView.getId().equals("imageViewSpecialLeft" + (index + 1))) {
                        imageView.setImage(bigNum);
                        int newValue = value / 10;
                        Rectangle2D imagePart = new Rectangle2D(newValue * BIG_NUM_WIDTH, 2, BIG_NUM_WIDTH, BIG_NUM_HEIGHT);
                        imageView.setViewport(imagePart);
                    }
                    if (imageView.getId().equals("imageViewSpecialRight" + (index + 1))) {
                        imageView.setImage(bigNum);
                        int newValue = value % 10;
                        Rectangle2D imagePart = new Rectangle2D(newValue * BIG_NUM_WIDTH, 2, BIG_NUM_WIDTH, BIG_NUM_HEIGHT);
                        imageView.setViewport(imagePart);
                    }
                });
    }

    public void setFoCharacter(FoCharacter foCharacter) {
        System.out.println("CharacterController::setFoCharacter(FoCharacter foCharacter)");
        this.foCharacter = foCharacter;
    }

    protected void initImages() {
        URL url = App.class.getResource("images/bignum.png");
        bigNum = new Image(url.toString());
    }

    protected void updateSKillLabelValues() {
        backgroundPane.getChildren()
                .filtered(node -> node instanceof Label)
                .filtered(l -> l.getId() != null)
                .filtered(l -> l.getId().contains("labelSkillLeft") || l.getId().contains("labelSkillRight"))
                .forEach(l -> {
                    int index = 0;
                    if (l.getId().contains("labelSkillLeft")) {
                        index = Integer.parseInt(l.getId().substring("labelSkillLeft".length()));
                        ((Label) l).setText(foCharacter.getSkillName(index - 1));
                    } else {
                        index = Integer.parseInt(l.getId().substring("labelSkillRight".length()));
                        ((Label) l).setText("" + foCharacter.getSkillValue(index - 1) + "%");
                    }
                    System.out.println("Index = " + index);
                    if (foCharacter.isTaggedSkill(index - 1)) {
                        l.setStyle("-fx-text-fill: #CBCBCB;");
                        System.out.println("Tagged skill: " + foCharacter.getSkillName(index - 1) + "\t" + l);
                    } else {
                        l.setStyle("-fx-text-fill: #38FB00;");
                        System.out.println("Untagged skill: " + foCharacter.getSkillName(index - 1) + "\t" + l);
                    }
                });
    }

    protected void updateStatLabelValues() {
        labelHitPoints.setText(String.format("Hit Points %d/%d", foCharacter.getHitPoints(), foCharacter.getHitPoints()));
        labelArmorClassValue.setText("" + foCharacter.getArmorClass());
        labelActionPointsValue.setText("" + foCharacter.getActionPoints());
        labelCarryWeightValue.setText("" + foCharacter.getCarryWeight());
        labelMeleeDamageValue.setText("" + foCharacter.getMeleeDamage());
        labelDamageResValue.setText("" + foCharacter.getDamageResistance());
        labelPoisonResValue.setText("" + foCharacter.getPoisonResistance());
        labelRadiationResValue.setText("" + foCharacter.getRadiationResistance());
        labelSequenceValue.setText("" + foCharacter.getSequence());
        labelHealingRateValue.setText("" + foCharacter.getHealingRate());
        labelCriticalChanceValue.setText("" + foCharacter.getCriticalChance());
    }

    protected void updateDescriptionText(String name, String description, String image) {
        labelInfoCaption.setText(name);
        labelInfoDescription.setText(description);
        URL url = App.class.getResource(image);
        Image img = new Image(url.toString());
        imageViewInfoImage.setImage(img);

    }

    protected void handleBaseLabelClickEvents() {
        //  generic labels: special, skills, etc.
        backgroundPane.getChildren()
                .filtered(node -> node instanceof Label)
                .filtered(node -> node != null && node.getId() != null)
                .forEach(node -> {
                    if (node.getId().contains("textStatsInfo")) {
                        int index = Integer.parseInt(node.getId().substring("textStatsInfo".length()));
                        (node).setOnMouseClicked(mouseEvent -> {
                            Special special = foCharacter.getSpecial(index - 1);
                            updateDescriptionText(special.getName(), special.getDescription(), special.getImage());
                        });
                    }
                    if (node.getId().contains("labelSkillLeft")) {
                        int index = Integer.parseInt(node.getId().substring("labelSkillLeft".length()));
                        (node).setOnMouseClicked(mouseEvent -> {
                            Skill skill = foCharacter.getSkill(index - 1);
                            updateDescriptionText(skill.getName(), skill.getDescription(), skill.getImage());
                        });
                    }
                    if (node.getId().contains("labelSkillRight")) {
                        int index = Integer.parseInt(node.getId().substring("labelSkillRight".length()));
                        (node).setOnMouseClicked(mouseEvent -> {
                            Skill skill = foCharacter.getSkill(index - 1);
                            updateDescriptionText(skill.getName(), skill.getDescription(), skill.getImage());
                        });
                    }
                });
        //  specific/named labels: like Hit Points, Armor Class, etc.
        labelHitPoints.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Hit Points", "How much damage your character can take before falling on ground.", "images/skilldex/HITPOINT.png");
        });
        labelPoisoned.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Poisoned", "Your character has been poisoned.  Poison will do damage over a period of time, until cured or it passes from your system.", "images/skilldex/POISONED.png");
        });
        labelRadiated.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Radiated", "Your character is suffering from a significant amount of Radiation poisoning.  The more radiation damage, the more deadly the effect.", "images/skilldex/RADIATED.png");
        });
        labelEyeDamage.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Eye Damage", "This means your character has been seriously hit in one or both of your eyes.  This affects your Perception.", "images/skilldex/EYEDAMAG.png");
        });
        labelCrippledRightArm.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Crippled Right Arm", "The right arm has been severely hurt, and cannot function well.  If one arm has been crippled, you cannot use two-handed weapons.  If both arms have been crippled, you cannot attack with weapons.", "images/skilldex/ARMRIGHT.png");
        });
        labelCrippledLeftArm.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Crippled Left Arm", "Your character's left arm has been severely hurt, and cannot function well.  If one arm has been crippled, you cannot use two-handed weapons.  If both arms have been crippled, you cannot attack with weapons.", "images/skilldex/ARMLEFT.png");
        });
        labelCrippledRightLeg.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Crippled Right Leg", "Your character has a crippled right leg.", "images/skilldex/LEGRIGHT.png");
        });
        labelCrippledLeftLeg.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Crippled Left Leg", "Your character has a crippled left leg.", "images/skilldex/LEGLEFT.png");
        });

        labelArmorClassName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Armor Class", "Modifies the chance to hit this particular character when the character is running.", "images/skilldex/ARMORCLS.png");
        });
        labelArmorClassValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Armor Class", "Modifies the chance to hit this particular character when the character is running.", "images/skilldex/ARMORCLS.png");
        });

        labelActionPointsName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Action Points", "The number of actions that the character can take during one combat turn.", "images/skilldex/ACTIONPT.png");
        });
        labelActionPointsValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Action Points", "The number of actions that the character can take during one combat turn.", "images/skilldex/ACTIONPT.png");
        });
        labelCarryWeightName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Carry Weight", "The maximum amount of equipment your character can carry, in pounds.", "images/skilldex/CARRYAMT.png");
        });
        labelCarryWeightValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Carry Weight", "The maximum amount of equipment your character can carry, in pounds.", "images/skilldex/CARRYAMT.png");
        });
        labelMeleeDamageName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Melee Damage", "The amount of bonus damage your character does in hand-to-hand combat.", "images/skilldex/MELEEDAM.png");
        });
        labelMeleeDamageValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Melee Damage", "The amount of bonus damage your character does in hand-to-hand combat.", "images/skilldex/MELEEDAM.png");
        });
        labelDamageResName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Damage resistance", "Any physical damage taken is reduced by this amount. Damage Resistance can be increased by wearing armor.", "images/skilldex/DAMRESIS.png");
        });
        labelDamageResValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Damage resistance", "Any physical damage taken is reduced by this amount. Damage Resistance can be increased by wearing armor.", "images/skilldex/DAMRESIS.png");
        });
        labelPoisonResName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Poison resistance", "Reduces poison damage by this amount.", "images/skilldex/POISNRES.png");
        });
        labelPoisonResValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Poison resistance", "Reduces poison damage by this amount.", "images/skilldex/POISNRES.png");
        });
        labelRadiationResName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Radiation resistance", "The amount of radiation you are exposed to is reduced by this percentage. Radiation Resistance can be modified by the type of armor worn, and anti-radiation chems.", "images/skilldex/RADRESIS.png");
        });
        labelRadiationResValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Radiation resistance", "The amount of radiation you are exposed to is reduced by this percentage. Radiation Resistance can be modified by the type of armor worn, and anti-radiation chems.", "images/skilldex/RADRESIS.png");
        });
        labelSequenceName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Sequence", "Determines how soon in a combat turn your character can react.", "images/skilldex/SEQUENCE.png");
        });
        labelSequenceValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Sequence", "Determines how soon in a combat turn your character can react.", "images/skilldex/SEQUENCE.png");
        });
        labelHealingRateName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Healing Rate", "Percentage of hit points your character heals every real minute.", "images/skilldex/HEALRATE.png");
        });
        labelHealingRateValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Healing Rate", "Percentage of hit points your character heals every real minute.", "images/skilldex/HEALRATE.png");
        });
        labelCriticalChanceName.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Critical Chance", "The chance to cause a critical hit in combat is increased by this amount.", "images/skilldex/CRITCHNC.png");
        });
        labelCriticalChanceValue.setOnMouseClicked(mouseEvent -> {
            updateDescriptionText("Critical Chance", "The chance to cause a critical hit in combat is increased by this amount.", "images/skilldex/CRITCHNC.png");
        });
    }
}
