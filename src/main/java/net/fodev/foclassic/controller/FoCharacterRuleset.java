package net.fodev.foclassic.controller;

import net.fodev.foclassic.model.fochar.FoCharacter;
import net.fodev.foclassic.model.fochar.SkillBookFactory;
import net.fodev.foclassic.model.fochar.SkillFactory;
import net.fodev.foclassic.model.fochar.TraitFactory;

public class FoCharacterRuleset {
    public static void updateSpecial(FoCharacter foCharacter, int index, int increment) {
        switch (index) {
            case 0:
                updateStrength(foCharacter, increment);
                break;
            case 1:
                updatePerception(foCharacter, increment);
                break;
            case 2:
                updateEndurance(foCharacter, increment);
                break;
            case 3:
                updateCharisma(foCharacter, increment);
                break;
            case 4:
                updateIntellect(foCharacter, increment);
                break;
            case 5:
                updateAgility(foCharacter, increment);
                break;
            case 6:
                updateLuck(foCharacter, increment);
                break;
        }
    }

    public static void updateStrength(FoCharacter foCharacter, int increment) {
        foCharacter.setSkillValueByName(SkillFactory.CLOSE_COMBAT, foCharacter.getSkillValueByName(SkillFactory.CLOSE_COMBAT) + 2 * increment);
        foCharacter.setHitPoints(foCharacter.getHitPoints() + increment);
        foCharacter.setMeleeDamage((1 + (foCharacter.getStrength() >= 7 ? foCharacter.getStrength() - 6 : 1)) * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 2 : 1)
                + (foCharacter.hasTrait(TraitFactory.HEAVY_HANDED) ? 5 : 0));
        foCharacter.setCarryWeight((int)(25 + (foCharacter.getStrength() * 25) /2.2) + 20);
    }

    public static void updatePerception(FoCharacter foCharacter, int increment) {
        foCharacter.setSkillValueByName(SkillFactory.FIRST_AID, foCharacter.getSkillValueByName(SkillFactory.FIRST_AID) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.DOCTOR, foCharacter.getSkillValueByName(SkillFactory.DOCTOR) + increment);
        foCharacter.setSkillValueByName(SkillFactory.LOCKPICK, foCharacter.getSkillValueByName(SkillFactory.LOCKPICK) + increment);
        foCharacter.setSkillValueByName(SkillFactory.TRAPS, foCharacter.getSkillValueByName(SkillFactory.TRAPS) + increment);
        foCharacter.setSequence(4 + foCharacter.getPerception() * 2);
    }

    public static void updateEndurance(FoCharacter foCharacter, int increment) {
        foCharacter.setSkillValueByName(SkillFactory.OUTDOORSMAN, foCharacter.getSkillValueByName(SkillFactory.OUTDOORSMAN) + 2 * increment);
        foCharacter.setHitPoints(foCharacter.getHitPoints() + 2 * increment);
        foCharacter.setPoisonResistance(foCharacter.getPoisonResistance() + 3 * increment);
        foCharacter.setRadiationResistance(foCharacter.getRadiationResistance() + 2 * increment);
        foCharacter.setHealingRate((7 + foCharacter.getEndurance() / 2) * (foCharacter.hasTrait(TraitFactory.FAST_METABOLISM) ? 2 : 1));
    }

    public static void updateCharisma(FoCharacter foCharacter, int increment) {
        foCharacter.setSkillValueByName(SkillFactory.SPEECH, foCharacter.getSkillValueByName(SkillFactory.SPEECH) + 5 * increment);
        foCharacter.setSkillValueByName(SkillFactory.BARTER, foCharacter.getSkillValueByName(SkillFactory.BARTER) + 4 * increment);
    }

    public static void updateIntellect(FoCharacter foCharacter, int increment) {
        foCharacter.setSkillValueByName(SkillFactory.FIRST_AID, foCharacter.getSkillValueByName(SkillFactory.FIRST_AID) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.DOCTOR, foCharacter.getSkillValueByName(SkillFactory.DOCTOR) + increment);
        foCharacter.setSkillValueByName(SkillFactory.SCIENCE, foCharacter.getSkillValueByName(SkillFactory.SCIENCE) + 4 * increment);
        foCharacter.setSkillValueByName(SkillFactory.REPAIR, foCharacter.getSkillValueByName(SkillFactory.REPAIR) + 3 * increment);
        foCharacter.setSkillValueByName(SkillFactory.OUTDOORSMAN, foCharacter.getSkillValueByName(SkillFactory.OUTDOORSMAN) + 2 * increment);
    }

    public static void updateAgility(FoCharacter foCharacter, int increment) {
        foCharacter.setSkillValueByName(SkillFactory.SMALL_GUNS, foCharacter.getSkillValueByName(SkillFactory.SMALL_GUNS) + 4 * increment);
        foCharacter.setSkillValueByName(SkillFactory.BIG_GUNS, foCharacter.getSkillValueByName(SkillFactory.BIG_GUNS) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.ENERGY_WEAPONS, foCharacter.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.CLOSE_COMBAT, foCharacter.getSkillValueByName(SkillFactory.CLOSE_COMBAT) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.THROWING, foCharacter.getSkillValueByName(SkillFactory.THROWING) + 4 * increment);
        foCharacter.setSkillValueByName(SkillFactory.SNEAK, foCharacter.getSkillValueByName(SkillFactory.SNEAK) + 3 * increment);
        foCharacter.setSkillValueByName(SkillFactory.LOCKPICK, foCharacter.getSkillValueByName(SkillFactory.LOCKPICK) + increment);
        foCharacter.setSkillValueByName(SkillFactory.STEAL, foCharacter.getSkillValueByName(SkillFactory.STEAL) + 3 * increment);
        foCharacter.setSkillValueByName(SkillFactory.TRAPS, foCharacter.getSkillValueByName(SkillFactory.TRAPS) + increment);
        foCharacter.setArmorClass(foCharacter.getArmorClass() + 3 * increment);
        foCharacter.setActionPoints(5 + foCharacter.getAgility() / 2 - (2 * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 1 : 0)));

    }

    public static void updateLuck(FoCharacter foCharacter, int increment) {
        foCharacter.setSkillValueByName(SkillFactory.GAMBLING, foCharacter.getSkillValueByName(SkillFactory.GAMBLING) + 5 * increment);
        foCharacter.setCriticalChance(foCharacter.getLuck() + (foCharacter.hasTrait(TraitFactory.FINESSE) ? 10 : 0));
    }

    public static void updateTraits(FoCharacter foCharacter) {
        foCharacter.setHealingRate((7 + foCharacter.getEndurance() / 2) * (foCharacter.hasTrait(TraitFactory.FAST_METABOLISM) ? 2 : 1));
        foCharacter.setMeleeDamage((1 + (foCharacter.getStrength() >= 7 ? foCharacter.getStrength() - 6 : 1)) * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 2 : 1)
                + (foCharacter.hasTrait(TraitFactory.HEAVY_HANDED) ? 5 : 0));
        foCharacter.setActionPoints(5 + foCharacter.getAgility() / 2 - (2 * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 1 : 0)));
        foCharacter.setCriticalChance(foCharacter.getLuck() + (foCharacter.hasTrait(TraitFactory.FINESSE) ? 10 : 0));
    }

    public static int getBaseSkillValueByName(FoCharacter foCharacter, String name) {
        switch (name) {
            case SkillFactory.SMALL_GUNS:
                return 5 + 4 * foCharacter.getAgility();
            case SkillFactory.BIG_GUNS:
            case SkillFactory.ENERGY_WEAPONS:
                return 2 * foCharacter.getAgility();
            case SkillFactory.CLOSE_COMBAT:
                return 30 + 2 * (foCharacter.getAgility() + foCharacter.getStrength());
            case SkillFactory.THROWING:
                return 4 * foCharacter.getAgility();
            case SkillFactory.FIRST_AID:
                return 2 * (foCharacter.getPerception() + foCharacter.getIntellect());
            case SkillFactory.DOCTOR:
                return 5 + foCharacter.getPerception() + foCharacter.getIntellect();
            case SkillFactory.SNEAK:
                return 5 + 3 * foCharacter.getAgility();
            case SkillFactory.LOCKPICK:
                return 10 + foCharacter.getAgility() + foCharacter.getPerception();
            case SkillFactory.STEAL:
                return 3 * foCharacter.getAgility();
            case SkillFactory.TRAPS:
                return 10 + foCharacter.getPerception() + foCharacter.getAgility();
            case SkillFactory.SCIENCE:
                return 4 * foCharacter.getIntellect();
            case SkillFactory.REPAIR:
                return 3 * foCharacter.getIntellect();
            case SkillFactory.SPEECH:
                return 5 * foCharacter.getCharisma();
            case SkillFactory.BARTER:
                return 4 * foCharacter.getCharisma();
            case SkillFactory.GAMBLING:
                return 5 * foCharacter.getLuck();
            case SkillFactory.OUTDOORSMAN:
                return 2 * (foCharacter.getIntellect() + foCharacter.getEndurance());
        }
        return 0;
    }

    public static int getMinimumSkillValueWithoutPointsSpentByName(FoCharacter foCharacter, String name) {
        switch (name) {
            case SkillFactory.SMALL_GUNS:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0) + 6 * foCharacter.getSkillBookRead(SkillBookFactory.SMALL_GUNS);
            case SkillFactory.BIG_GUNS:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.ENERGY_WEAPONS:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.CLOSE_COMBAT:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.THROWING:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.FIRST_AID:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0) + 6 * foCharacter.getSkillBookRead(SkillBookFactory.FIRST_AID);
            case SkillFactory.DOCTOR:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.SNEAK:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.LOCKPICK:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.STEAL:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.TRAPS:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.SCIENCE:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0) + 6 * foCharacter.getSkillBookRead(SkillBookFactory.SCIENCE);
            case SkillFactory.REPAIR:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0) + 6 * foCharacter.getSkillBookRead(SkillBookFactory.REPAIR);
            case SkillFactory.SPEECH:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.BARTER:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0) + 6 * foCharacter.getSkillBookRead(SkillBookFactory.BARTER);
            case SkillFactory.GAMBLING:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0);
            case SkillFactory.OUTDOORSMAN:
                return getBaseSkillValueByName(foCharacter, name) + (foCharacter.isTaggedSkill(name) ? 20 : 0) + 6 * foCharacter.getSkillBookRead(SkillBookFactory.OUTDOORSMAN);
        }
        return 0;    }
}
