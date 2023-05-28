package net.fodev.foclassic.model.dialog;

import net.fodev.foclassic.model.fochar.FoCharacter;
import net.fodev.foclassic.model.fochar.FoCharacterFactory;
import org.junit.Assert;
import org.junit.Test;

public class TestDialogAnswerNode {
    @Test
    public void areDemandsMet_simple_valid() {
        FoCharacter foCharacter = FoCharacterFactory.createNewCharacter("Test", 15, "Male");
        DialogAnswerNode gainOneLevel = new DialogAnswerNode("Gain one level.", null, foCharacter);
        gainOneLevel.addDemand(new DialogDemandNode(fc -> fc.getUnusedSkillPoints() < 5, ""));
        Assert.assertTrue(gainOneLevel.areDemandsMet());
        gainOneLevel.addDemand(new DialogDemandNode(fc -> fc.getUnusedSkillPoints() < 0, ""));
        Assert.assertFalse(gainOneLevel.areDemandsMet());
        gainOneLevel.addDemand(new DialogDemandNode(fc -> fc.getUnusedSkillPoints() < 5, ""));
        Assert.assertFalse(gainOneLevel.areDemandsMet());
    }
}
