package net.fodev.foclassic.controller;

import net.fodev.foclassic.model.FoCharacter;
import net.fodev.foclassic.model.SkillFactory;
import net.fodev.foclassic.model.TraitFactory;

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
        System.out.println("FoCharacterRuleset::updateStrength(" + increment + ")");
        foCharacter.setSkillValueByName(SkillFactory.CLOSE_COMBAT, foCharacter.getSkillValueByName(SkillFactory.CLOSE_COMBAT) + 2 * increment);
        foCharacter.setHitPoints(foCharacter.getHitPoints() + increment);
        foCharacter.setMeleeDamage((1 + (foCharacter.getStrength() >= 7 ? foCharacter.getStrength() - 6 : 1)) * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 2 : 1)
                + (foCharacter.hasTrait(TraitFactory.HEAVY_HANDED) ? 5 : 0));
        foCharacter.setCarryWeight((int)(25 + (foCharacter.getStrength() * 25) /2.2) + 20);
    }

    public static void updatePerception(FoCharacter foCharacter, int increment) {
        System.out.println("FoCharacterRuleset::updatePerception(" + increment + ")");
        foCharacter.setSkillValueByName(SkillFactory.FIRST_AID, foCharacter.getSkillValueByName(SkillFactory.FIRST_AID) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.DOCTOR, foCharacter.getSkillValueByName(SkillFactory.DOCTOR) + increment);
        foCharacter.setSkillValueByName(SkillFactory.LOCKPICK, foCharacter.getSkillValueByName(SkillFactory.LOCKPICK) + increment);
        foCharacter.setSequence(4 + foCharacter.getPerception() * 2);
    }

    public static void updateEndurance(FoCharacter foCharacter, int increment) {
        System.out.println("FoCharacterRuleset::updateEndurance(" + increment + ")");
        foCharacter.setSkillValueByName(SkillFactory.OUTDOORSMAN, foCharacter.getSkillValueByName(SkillFactory.OUTDOORSMAN) + 2 * increment);
        foCharacter.setHitPoints(foCharacter.getHitPoints() + 2 * increment);
        foCharacter.setPoisonResistance(foCharacter.getPoisonResistance() + 3 * increment);
        foCharacter.setRadiationResistance(foCharacter.getRadiationResistance() + 2 * increment);
        foCharacter.setHealingRate((7 + foCharacter.getEndurance() / 2) * (foCharacter.hasTrait(TraitFactory.FAST_METABOLISM) ? 2 : 1));
    }

    public static void updateCharisma(FoCharacter foCharacter, int increment) {
        System.out.println("FoCharacterRuleset::updateCharisma(" + increment + ")");
        foCharacter.setSkillValueByName(SkillFactory.SPEECH, foCharacter.getSkillValueByName(SkillFactory.SPEECH) + 5 * increment);
        foCharacter.setSkillValueByName(SkillFactory.BARTER, foCharacter.getSkillValueByName(SkillFactory.BARTER) + 4 * increment);
    }

    public static void updateIntellect(FoCharacter foCharacter, int increment) {
        System.out.println("FoCharacterRuleset::updateIntellect(" + increment + ")");
        foCharacter.setSkillValueByName(SkillFactory.FIRST_AID, foCharacter.getSkillValueByName(SkillFactory.FIRST_AID) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.DOCTOR, foCharacter.getSkillValueByName(SkillFactory.DOCTOR) + increment);
        foCharacter.setSkillValueByName(SkillFactory.SCIENCE, foCharacter.getSkillValueByName(SkillFactory.SCIENCE) + 4 * increment);
        foCharacter.setSkillValueByName(SkillFactory.REPAIR, foCharacter.getSkillValueByName(SkillFactory.REPAIR) + 3 * increment);
        foCharacter.setSkillValueByName(SkillFactory.OUTDOORSMAN, foCharacter.getSkillValueByName(SkillFactory.OUTDOORSMAN) + 2 * increment);
    }

    public static void updateAgility(FoCharacter foCharacter, int increment) {
        System.out.println("FoCharacterRuleset::updateAgility(" + increment + ")");
        foCharacter.setSkillValueByName(SkillFactory.SMALL_GUNS, foCharacter.getSkillValueByName(SkillFactory.SMALL_GUNS) + 4 * increment);
        foCharacter.setSkillValueByName(SkillFactory.BIG_GUNS, foCharacter.getSkillValueByName(SkillFactory.BIG_GUNS) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.ENERGY_WEAPONS, foCharacter.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.CLOSE_COMBAT, foCharacter.getSkillValueByName(SkillFactory.CLOSE_COMBAT) + 2 * increment);
        foCharacter.setSkillValueByName(SkillFactory.THROWING, foCharacter.getSkillValueByName(SkillFactory.THROWING) + 4 * increment);
        foCharacter.setSkillValueByName(SkillFactory.SNEAK, foCharacter.getSkillValueByName(SkillFactory.SNEAK) + 3 * increment);
        foCharacter.setSkillValueByName(SkillFactory.LOCKPICK, foCharacter.getSkillValueByName(SkillFactory.LOCKPICK) + increment);
        foCharacter.setSkillValueByName(SkillFactory.STEAL, foCharacter.getSkillValueByName(SkillFactory.STEAL) + 3 * increment);
        foCharacter.setSkillValueByName(SkillFactory.TRAPS, foCharacter.getSkillValueByName(SkillFactory.TRAPS) + 3 * increment);
        foCharacter.setArmorClass(foCharacter.getArmorClass() + 3 * increment);
        foCharacter.setActionPoints(5 + foCharacter.getAgility() / 2 - (2 * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 1 : 0)));

    }

    public static void updateLuck(FoCharacter foCharacter, int increment) {
        System.out.println("FoCharacterRuleset::updateIntellect(" + increment + ")");
        foCharacter.setSkillValueByName(SkillFactory.GAMBLING, foCharacter.getSkillValueByName(SkillFactory.GAMBLING) + 5 * increment);
        foCharacter.setCriticalChance(foCharacter.getLuck() + (foCharacter.hasTrait(TraitFactory.FINESSE) ? 10 : 0));
    }

    public static void updateTraits(FoCharacter foCharacter) {
        System.out.println("FoCharacterRuleset::updateTraits()");
        foCharacter.setHealingRate((7 + foCharacter.getEndurance() / 2) * (foCharacter.hasTrait(TraitFactory.FAST_METABOLISM) ? 2 : 1));
        foCharacter.setMeleeDamage((1 + (foCharacter.getStrength() >= 7 ? foCharacter.getStrength() - 6 : 1)) * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 2 : 1)
                + (foCharacter.hasTrait(TraitFactory.HEAVY_HANDED) ? 5 : 0));
        foCharacter.setActionPoints(5 + foCharacter.getAgility() / 2 - (2 * (foCharacter.hasTrait(TraitFactory.BRUISER) ? 1 : 0)));
        foCharacter.setCriticalChance(foCharacter.getLuck() + (foCharacter.hasTrait(TraitFactory.FINESSE) ? 10 : 0));
    }
}
