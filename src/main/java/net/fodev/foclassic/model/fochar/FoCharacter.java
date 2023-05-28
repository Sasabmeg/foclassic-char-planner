package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FoCharacter {
    @Getter @Setter private String name;
    @Getter @Setter private int age;
    @Getter @Setter private String sex;
    @Getter @Setter private int level;
    @Getter @Setter private int experience;
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
    @Getter @Setter private List<CombatPerk> combatPerks;
    @Getter @Setter private List<SupportPerk> supportPerks;
    @Getter @Setter private int unusedPerkPoints;

    public FoCharacter(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        specials = new ArrayList<>();
        skills = new ArrayList<>();
        traits = new ArrayList<>();
        combatPerks = new ArrayList<>();
        supportPerks = new ArrayList<>();
        unusedTraitPoints = 2;
        experience = 0;
        level = 1;
    }

    public boolean canTagTrait() {
        return unusedTraitPoints > 0;
    }

    public String getTraitName(int index) {
        if (index >= 0 && index < traits.size()) {
            return traits.get(index).getName();
        } else {
            return "";
        }
    }

    public void tagTrait(int index) {
        if (index >= 0 && index < traits.size()) {
            if (!traits.get(index).isTagged() && canTagTrait()) {
                traits.get(index).setTagged(true);
                unusedTraitPoints--;
            } else {
                System.out.println("Warning: Trying to tag trait that is already tagged.");
            }
        } else {
            System.out.println("Warning: Index out of bounds when trying to tag trait.");
        }
    }

    public void untagTrait(int index) {
        if (index >= 0 && index < 16) {
            if (traits.get(index).isTagged()) {
                traits.get(index).setTagged(false);
                unusedTraitPoints++;
            } else {
                System.out.println("Warning: Trying to untag trait that is not tagged.");
            }
        } else {
            System.out.println("Warning: Index out of bounds when trying to untag trait.");
        }
    }

    public void removeTrait(String traitName) {
        traits.removeIf(trait -> trait.getName().equals(traitName));
    }

    public boolean isTaggedSkill(int index) {
        if (index >= 0 && index < skills.size()) {
            return skills.get(index).isTagged();
        } else {
            return false;
        }
    }

    public boolean isTaggedTrait(int index) {
        if (index >= 0 && index < traits.size()) {
            return traits.get(index).isTagged();
        } else {
            return false;
        }
    }


    public void setSkillValue(int index, int value) {
        if (index >= 0 && index < skills.size()) {
            skills.get(index).setValue(value);
        }
    }

    public void setSkillValueByName(String name, int value) {
        skills.stream()
                .filter(s -> name.equals(s.getName()))
                .findFirst().get().setValue(value);
    }

    public int getSkillValue(int index) {
        if (index >= 0 && index < skills.size()) {
            return skills.get(index).getValue();
        } else {
            return -1;
        }
    }

    public int getSkillValueByName(String name) {
        return skills.stream()
                .filter(s -> name.equals(s.getName()))
                .findFirst().get().getValue();
    }

    public String getSkillName(int index) {
        if (index >= 0 && index < skills.size()) {
            return skills.get(index).getName();
        } else {
            return "";
        }
    }

    public Skill getSkill(int index) {
        if (index >= 0 && index < skills.size()) {
            return skills.get(index);
        } else {
            return null;
        }
    }

    public Trait getTrait(int index) {
        if (index >= 0 && index < traits.size()) {
            return traits.get(index);
        } else {
            return null;
        }
    }

    public Special getSpecial(int index) {
        if (index >= 0 && index < specials.size()) {
            return specials.get(index);
        } else {
            return null;
        }
    }

    public void tagSkill(int index) {
        if (index >= 0 && index < skills.size()) {
            if (!skills.get(index).isTagged()) {
                Skill skill = skills.get(index);
                skill.setTagged(true);
                skill.increaseValue(20);
                unusedTagPoints--;
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

    public boolean hasTrait(String traitName) {
        return traits.stream().filter(t -> traitName.equals(t.getName())).findFirst().orElseGet(() -> TraitFactory.get(TraitFactory.NA)).isTagged();
    }

    public int getExpForNextLevel() {
        return FoCharacter.getExpForNextLevel(level);
    }

    public static int getExpForNextLevel(int currentLevel) {
        int xpNext = 0;
        xpNext = 500 * currentLevel * (currentLevel + 1);
        return xpNext;
    }

    public void gainExperience(int value) {
        int next = getExpForNextLevel();
        if (next > value + getExperience()) {
            experience += value;
        } else {
            int xpRemains = experience + value - getExpForNextLevel();
            experience = getExpForNextLevel();
            gainLevel();
            if (xpRemains > 0) {
                gainExperience(xpRemains);
            }
        }

    }

    public void gainLevel() {
        level++;
        System.out.println("You have reached level: " + level);
        unusedSkillPoints += getSkillPointsPerLevel();
    }

    public int getSkillPointsPerLevel() {
        return  5 + getIntellect() * 2;
    }

    public boolean hasSupportPerk(SupportPerk supportPerk) {
        return supportPerks.stream().anyMatch(s -> s.equals(supportPerk));
    }

    public void addSupportPerk(SupportPerk supportPerk) {
        supportPerks.add(supportPerk);
    }
}