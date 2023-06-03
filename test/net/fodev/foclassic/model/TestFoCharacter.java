package net.fodev.foclassic.model;

import net.fodev.foclassic.model.fochar.FoCharacter;
import net.fodev.foclassic.model.fochar.FoCharacterFactory;
import net.fodev.foclassic.model.fochar.PerkFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestFoCharacter {
    @Test
    public void getExpForNextLevel_simple_valid() {
        System.out.println(FoCharacter.getExpForNextLevel(1));
        System.out.println(FoCharacter.getExpForNextLevel(2));
        System.out.println(FoCharacter.getExpForNextLevel(3));
        System.out.println(FoCharacter.getExpForNextLevel(4));
        System.out.println(FoCharacter.getExpForNextLevel(5));
        System.out.println(FoCharacter.getExpForNextLevel(6));
        System.out.println(FoCharacter.getExpForNextLevel(7));
    }

    @Test
    public void gainExperience_simple_valid() {
        FoCharacter foCharacter = FoCharacterFactory.createNewCharacter("Test", 15, "Male");
        System.out.println("Current level: " + foCharacter.getLevel());
        System.out.println("Current XP: " + foCharacter.getExperience());
        System.out.println("Xp req. for next level: " + foCharacter.getExpForNextLevel());
        foCharacter.gainExperience(500);
        foCharacter.gainExperience(1000);
        foCharacter.gainExperience(1500);
        foCharacter.gainExperience(2000);
        foCharacter.gainExperience(3000);
    }

    @Test
    public void hasSupportPerk_simple_valid() {
        FoCharacter foCharacter = FoCharacterFactory.createNewCharacter("Test", 15, "Male");
        Assert.assertTrue(foCharacter.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.AWARENESS)));
        Assert.assertFalse(foCharacter.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.STRONG_BACK)));
    }

    @Test
    public void equals_simple_valid() {
        FoCharacter foChar1 = FoCharacterFactory.createNewCharacter("Test1", 15, "Male");
        FoCharacter foChar2 = FoCharacterFactory.createNewCharacter("Test1", 15, "Male");
        System.out.println("FoCharacter.equals() = " + foChar1.equals(foChar2));

        foChar1.setSkillValue(0, 0);
        System.out.println("FoCharacter.equals() = " + foChar1.equals(foChar2));

        FoCharacterFactory.copyTo(foChar1, foChar2);
        System.out.println("FoCharacter.equals() = " + foChar1.equals(foChar2));
    }

}
