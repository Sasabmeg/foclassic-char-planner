package net.fodev.foclassic.controller;

import net.fodev.foclassic.model.fochar.FoCharacter;
import net.fodev.foclassic.model.fochar.FoCharacterFactory;
import net.fodev.foclassic.model.fochar.PerkFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestFoCharacterRuleset {

    @Test
    public void canFreePerk_simple_Valid() {
        FoCharacter fc = FoCharacterFactory.createNewCharacter("Test1", 15, "Male");
        fc.setLevel(24);
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_CRITICAL));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.IN_YOUR_FACE));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_AGILITY));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SILENT_DEATH));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RATE_OF_ATTACK));

        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.BONUS_RATE_OF_ATTACK));
        Assert.assertFalse(FoCharacterRuleset.canFreePerk(fc, PerkFactory.SILENT_DEATH));
        Assert.assertFalse(FoCharacterRuleset.canFreePerk(fc, PerkFactory.GAIN_AGILITY));
        Assert.assertFalse(FoCharacterRuleset.canFreePerk(fc, PerkFactory.SHARPSHOOTER));
        Assert.assertFalse(FoCharacterRuleset.canFreePerk(fc, PerkFactory.IN_YOUR_FACE));
        Assert.assertFalse(FoCharacterRuleset.canFreePerk(fc, PerkFactory.MORE_CRITICAL));
    }

    @Test
    public void canFreePerk_doubleLevelNinePerk_Valid() {
        FoCharacter fc = FoCharacterFactory.createNewCharacter("Test1", 15, "Male");
        fc.setLevel(24);
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_CRITICAL));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.IN_YOUR_FACE));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_AGILITY));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_CHARISMA));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SILENT_DEATH));
        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RATE_OF_ATTACK));

        fc.addCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SILENT_RUNNING));


        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.BONUS_RATE_OF_ATTACK));
        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.SILENT_DEATH));
        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.GAIN_CHARISMA));
        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.GAIN_AGILITY));
        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.SHARPSHOOTER));
        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.IN_YOUR_FACE));
        Assert.assertTrue(FoCharacterRuleset.canFreePerk(fc, PerkFactory.SILENT_RUNNING));
        Assert.assertFalse(FoCharacterRuleset.canFreePerk(fc, PerkFactory.MORE_CRITICAL));
    }

}
