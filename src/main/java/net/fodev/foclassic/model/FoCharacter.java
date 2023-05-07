package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FoCharacter {
    @Getter @Setter private String name;
    @Getter @Setter private int age;
    @Getter @Setter private String sex;
    @Getter @Setter private List<Special> specials;
    @Getter @Setter private int hitPoints;
    @Getter @Setter private float poisoned;
    @Getter @Setter private float radiated;
    @Getter @Setter private boolean eyeDamage;
    @Getter @Setter private boolean crippledRightArm;
    @Getter @Setter private boolean crippledLeftArm;
    @Getter @Setter private boolean crippledRightLeg;
    @Getter @Setter private boolean crippledLeftLeg;
    @Getter @Setter private int armorClass;
    @Getter @Setter private int actionPoints;
    @Getter @Setter private int carryWeight;
    @Getter @Setter private int meleeDamage;
    @Getter @Setter private int damageResistance;
    @Getter @Setter private int poisonResistance;
    @Getter @Setter private int radiationResistance;
    @Getter @Setter private int sequence;
    @Getter @Setter private int healingRate;
    @Getter @Setter private int criticalChance;
    @Getter @Setter private int unusedSpecialPoints;
    @Getter @Setter private List<Skill> skills;
    @Getter @Setter private int unusedSkillPoints;
    @Getter @Setter private int unusedTagPoints;
    @Getter @Setter private List<Trait> traits;
    @Getter @Setter private int unusedTraitPoints;
    @Getter @Setter private List<Perk> perks;
    @Getter @Setter private int unusedPerkPoints;

    public FoCharacter(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        specials = new ArrayList<>();
        specials.add(new Special("ST", 5, "Strength", ""));
        specials.add(new Special("PE", 5, "Perception", ""));
        specials.add(new Special("EN", 5, "Endurance", ""));
        specials.add(new Special("CH", 5, "Charisma", ""));
        specials.add(new Special("IN", 5, "Intellect", ""));
        specials.add(new Special("AG", 5, "Agility", ""));
        specials.add(new Special("LK", 5, "Luck", ""));
        skills = new ArrayList<>();
        traits = new ArrayList<>();
        perks = new ArrayList<>();
    }

    public boolean isTaggedSkill(int index) {
        if (index >= 0 && index < skills.size()) {
            return skills.get(index).isTagged();
        } else {
            return false;
        }
    }

    public void setSkillValue(int index, int value) {
        if (index >= 0 && index < skills.size()) {
            skills.get(index).setValue(value);
        }
    }

    public int getSkillValue(int index) {
        if (index >= 0 && index < skills.size()) {
            return skills.get(index).getValue();
        } else {
            return -1;
        }
    }

    public String getSkillName(int index) {
        if (index >= 0 && index < skills.size()) {
            return skills.get(index).getName();
        } else {
            return "";
        }
    }

    public void tagSkill(int index) {
        if (index >= 0 && index < skills.size()) {
            if (!skills.get(index).isTagged()) {
                Skill skill = skills.get(index);
                skill.setTagged(true);
                skill.increaseValue(20);
                unusedTagPoints--;
                System.out.println("FoCharacter::tagSkill(index): Skill change: " + skill.getName() + " = " + skill.getValue());
            } else {
                System.out.println("Warning: Trying to tag skill that is already tagged.");
            }
        } else {
            System.out.println("Warning: Index out of bounds when trying to tag skill.");
        }
    }

    public void untagSkill(int index) {
        if (index >= 0 && index < skills.size()) {
            if (skills.get(index).isTagged()) {
                Skill skill = skills.get(index);
                skill.setTagged(false);
                skill.decreaseValue(20);
                unusedTagPoints++;
                System.out.println("FoCharacter::untagSkill(index): Skill change: " + skill.getName() + " = " + skill.getValue());
            } else {
                System.out.println("Warning: Trying to untag skill that is not tagged.");
            }
        } else {
            System.out.println("Warning: Index out of bounds when trying to tag skill.");
        }
    }

    public int getStrength() {
        return specials.get(0).getValue();
    }

    public void setStrength(int value) {
        if (value > 0 && value <= 10) {
            specials.get(0).setValue(value);
        } else {
            System.out.println("Warning: Trying to set Strength out of bounds.");
        }
    }

    public int getPerception() {
        return specials.get(1).getValue();
    }

    public void setPerception(int value) {
        if (value > 0 && value <= 10) {
            specials.get(1).setValue(value);
        } else {
            System.out.println("Warning: Trying to set Perception out of bounds.");
        }
    }

    public int getEndurance() {
        return specials.get(2).getValue();
    }

    public void setEndurance(int value) {
        if (value > 0 && value <= 10) {
            specials.get(2).setValue(value);
        } else {
            System.out.println("Warning: Trying to set Endurance out of bounds.");
        }
    }

    public int getCharisma() {
        return specials.get(3).getValue();
    }

    public void setCharisma(int value) {
        if (value > 0 && value <= 10) {
            specials.get(3).setValue(value);
        } else {
            System.out.println("Warning: Trying to set Charisma out of bounds.");
        }
    }

    public int getIntellect() {
        return specials.get(4).getValue();
    }

    public void setIntellect(int value) {
        if (value > 0 && value <= 10) {
            specials.get(4).setValue(value);
        } else {
            System.out.println("Warning: Trying to set Intellect out of bounds.");
        }
    }

    public int getAgility() {
        return specials.get(5).getValue();
    }

    public void setAgility(int value) {
        if (value > 0 && value <= 10) {
            specials.get(5).setValue(value);
        } else {
            System.out.println("Warning: Trying to set Agility out of bounds.");
        }
    }

    public int getLuck() {
        return specials.get(6).getValue();
    }

    public void setLuck(int value) {
        if (value > 0 && value <= 10) {
            specials.get(6).setValue(value);
        } else {
            System.out.println("Warning: Trying to set Luck out of bounds.");
        }
    }
}