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
    @Getter @Setter private List<SkillBook> skillBooks;

    public FoCharacter(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        specials = new ArrayList<>();
        skills = new ArrayList<>();
        traits = new ArrayList<>();
        combatPerks = new ArrayList<>();
        supportPerks = new ArrayList<>();
        skillBooks = new ArrayList<>();
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
            if (!traits.get(index).isTagged()) {
                if (canTagTrait()) {
                    traits.get(index).setTagged(true);
                    unusedTraitPoints--;
                } else {
                    System.out.println("Warning: Trying to tag trait without trait points left.");
                }
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

    public boolean isTaggedSkill(String name) {
        if (getSkillByName(name) != null) {
            return getSkillByName(name).isTagged();
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

    public Skill getSkillByName(String name) {
        return skills.stream()
                .filter(s -> name.equals(s.getName()))
                .findFirst().orElse(null);
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

    public void setSpecialValue(int index, int value) {
        if (index >= 0 && index < specials.size()) {
            specials.get(index).setValue(value);
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

    public void removeSupportPerk(SupportPerk supportPerk) {
        supportPerks.removeIf(sp -> sp.getName().equals(supportPerk.getName()));
    }

    public boolean hasMissingPerk() {
        int actual = combatPerks.size();
        int possible = hasTrait(TraitFactory.SKILLED) ? (level > 24 ? 6 : level / 4) : (level > 24 ? 8 : level / 3);
        return possible > actual;
    }

    public boolean hasCombatPerk(CombatPerk combatPerk) {
        return combatPerks.stream().anyMatch(c -> c.equals(combatPerk));
    }

    public void addCombatPerk(CombatPerk combatPerk) {
        combatPerks.add(combatPerk);
    }

    public void removeCombatPerk(CombatPerk combatPerk) {
        combatPerks.removeIf(sp -> sp.getName().equals(combatPerk.getName()));
    }

    public void raiseSkill(int selectedSkillIndex) {
        Skill skill = getSkill(selectedSkillIndex);
        if (skill != null && skill.getSkillRaiseCost() <= unusedSkillPoints) {
            unusedSkillPoints -= skill.getSkillRaiseCost();
            skill.raiseSkillValue();
        } else {
            System.out.println("FoCharacter::raiseSKill() - not enough skill points to raise skill.");
        }
    }

    public void unraiseSkill(int selectedSkillIndex, int minimum) {
        Skill skill = getSkill(selectedSkillIndex);
        unraiseSkill(skill, minimum);
    }

    public void unraiseSkill(String skillName, int minimum) {
        Skill skill = getSkillByName(skillName);
        unraiseSkill(skill, minimum);
    }

    private void unraiseSkill(Skill skill, int minimum) {
        if (skill != null && skill.getValue() > minimum) {
            unusedSkillPoints += skill.getSkillUnraiseGain();
            skill.unraiseSkillValue();
        } else {
            System.out.println("FoCharacter::raiseSKill() - cannot lower skill below minimum or last level up save.");
        }
    }

    public int getSkillBookRead(String protoName) {
        if (protoName == null) return 0;
        SkillBook sb = skillBooks.stream().filter(n -> protoName.equals(n.getProto().getName())).findFirst().orElse(null);
        return (sb == null) ? 0 : sb.getUsed();
    }

    public int getSkillBookMaxUses(String protoName) {
        if (protoName == null) return 0;
        SkillBook sb = skillBooks.stream().filter(n -> protoName.equals(n.getProto().getName())).findFirst().orElse(null);
        return (sb == null) ? 0 : sb.getMaxUses();
    }

    public boolean canReadSkillBook(String protoName) {
        SkillBook skillBook = skillBooks.stream().filter(b -> b.getProto().getName().equals(protoName)).findFirst().orElse(null);
        if (skillBook != null) {
            return skillBook.getUsed() < skillBook.getMaxUses();
        } else {
            return false;
        }
    }

    public void readSkillBook(String protoName) {
        SkillBook skillBook = skillBooks.stream().filter(b -> b.getProto().getName().equals(protoName)).findFirst().orElse(null);
        if (skillBook != null) {
            if (skillBook.getUsed() < skillBook.getMaxUses()) {
                SkillProto skillProto = SkillFactory.getSkillProto(skillBook.getProto().getProto().getName());
                Skill skill = getSkillByName(skillProto.getName());
                switch (skill.getSkillRaiseCost()) {
                    case 1:
                        skill.increaseValue(skill.isTagged() ? 12 : 6);
                        skillBook.readBook();
                        break;
                    case 2:
                        skill.increaseValue(skill.isTagged() ? 6 : 3);
                        skillBook.readBook();
                        break;
                    case 3:
                        skill.increaseValue(skill.isTagged() ? 4 : 2);
                        skillBook.readBook();
                        break;
                    case 4:
                    case 5:
                    case 6:
                        skill.increaseValue(skill.isTagged() ? 2 : 1);
                        skillBook.readBook();
                        break;
                    default:
                        System.out.println("Debug: Read skill book failed, not able to compute raise skill value.");
                }
            } else {
                System.out.println("Debug: Already read maximum of skill books - " + name);
            }
        } else {
            System.out.println("Debug: Could not read skill book - " + name);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoCharacter)) return false;

        FoCharacter that = (FoCharacter) o;

        if (age != that.age) return false;
        if (level != that.level) return false;
        if (experience != that.experience) return false;
        if (hitPoints != that.hitPoints) return false;
        if (Float.compare(that.poisoned, poisoned) != 0) return false;
        if (Float.compare(that.radiated, radiated) != 0) return false;
        if (eyeDamage != that.eyeDamage) return false;
        if (crippledRightArm != that.crippledRightArm) return false;
        if (crippledLeftArm != that.crippledLeftArm) return false;
        if (crippledRightLeg != that.crippledRightLeg) return false;
        if (crippledLeftLeg != that.crippledLeftLeg) return false;
        if (armorClass != that.armorClass) return false;
        if (actionPoints != that.actionPoints) return false;
        if (carryWeight != that.carryWeight) return false;
        if (meleeDamage != that.meleeDamage) return false;
        if (damageResistance != that.damageResistance) return false;
        if (poisonResistance != that.poisonResistance) return false;
        if (radiationResistance != that.radiationResistance) return false;
        if (sequence != that.sequence) return false;
        if (healingRate != that.healingRate) return false;
        if (criticalChance != that.criticalChance) return false;
        if (unusedSpecialPoints != that.unusedSpecialPoints) return false;
        if (unusedSkillPoints != that.unusedSkillPoints) return false;
        if (unusedTagPoints != that.unusedTagPoints) return false;
        if (unusedTraitPoints != that.unusedTraitPoints) return false;
        if (unusedPerkPoints != that.unusedPerkPoints) return false;
        if (!name.equals(that.name)) return false;
        if (!sex.equals(that.sex)) return false;
        if (!specials.equals(that.specials)) return false;
        if (!skills.equals(that.skills)) return false;
        if (!traits.equals(that.traits)) return false;
        if (!combatPerks.equals(that.combatPerks)) return false;
        if (!supportPerks.equals(that.supportPerks)) return false;
        if (!skillBooks.equals(that.skillBooks)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + sex.hashCode();
        result = 31 * result + level;
        result = 31 * result + experience;
        result = 31 * result + specials.hashCode();
        result = 31 * result + hitPoints;
        result = 31 * result + (poisoned != +0.0f ? Float.floatToIntBits(poisoned) : 0);
        result = 31 * result + (radiated != +0.0f ? Float.floatToIntBits(radiated) : 0);
        result = 31 * result + (eyeDamage ? 1 : 0);
        result = 31 * result + (crippledRightArm ? 1 : 0);
        result = 31 * result + (crippledLeftArm ? 1 : 0);
        result = 31 * result + (crippledRightLeg ? 1 : 0);
        result = 31 * result + (crippledLeftLeg ? 1 : 0);
        result = 31 * result + armorClass;
        result = 31 * result + actionPoints;
        result = 31 * result + carryWeight;
        result = 31 * result + meleeDamage;
        result = 31 * result + damageResistance;
        result = 31 * result + poisonResistance;
        result = 31 * result + radiationResistance;
        result = 31 * result + sequence;
        result = 31 * result + healingRate;
        result = 31 * result + criticalChance;
        result = 31 * result + unusedSpecialPoints;
        result = 31 * result + skills.hashCode();
        result = 31 * result + unusedSkillPoints;
        result = 31 * result + unusedTagPoints;
        result = 31 * result + traits.hashCode();
        result = 31 * result + unusedTraitPoints;
        result = 31 * result + combatPerks.hashCode();
        result = 31 * result + supportPerks.hashCode();
        result = 31 * result + skillBooks.hashCode();
        result = 31 * result + unusedPerkPoints;
        return result;
    }
}