package net.fodev.foclassic.model.fochar;

import org.junit.Test;

public class TestFoCharacterFactory {
    @Test
    public void copyTo_simple_valid() {
        FoCharacter foChar1 = FoCharacterFactory.createNewCharacter("AAA", 15, "Male");
        foChar1.setHitPoints(100);
        FoCharacter foChar2 = FoCharacterFactory.copy(foChar1);
        foChar1.setActionPoints(20);
        FoCharacterFactory.copyTo(foChar1, foChar2);
        System.out.println(String.format("1 HP = %d, 2 Hp = %d", foChar1.getHitPoints(), foChar2.getHitPoints()));
        System.out.println(String.format("1 HP = %d, 2 Hp = %d", foChar1.getActionPoints(), foChar2.getActionPoints()));
    }
}
