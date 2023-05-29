package net.fodev.foclassic.model.dialog;

import net.fodev.foclassic.model.fochar.FoCharacter;
import net.fodev.foclassic.model.fochar.PerkFactory;

public class DialogFactory {

    public static final DialogQuestionNode EXIT = new DialogQuestionNode(0, "Exit!");

    public static DialogQuestionNode createMainDialog(FoCharacter foCharacter) {

        DialogQuestionNode root = new DialogQuestionNode(2, "What would you like to do?");
        DialogQuestionNode supportPerkQuestion = new DialogQuestionNode(3, "Which support perk would you like to add.");

        //  main dialog
        DialogAnswerNode gainOneLevel = new DialogAnswerNode("Gain one level.", root, foCharacter);
        gainOneLevel.addResult(new DialogResultNode(fc -> fc.gainExperience(fc.getLevel() * 1000)));
        gainOneLevel.addDemand(new DialogDemandNode(fc -> fc.getUnusedSkillPoints() + fc.getSkillPointsPerLevel() <= 99, "Gaining one level would overflow unspent skill point above 99."));

        DialogAnswerNode gainThreeLevels = new DialogAnswerNode("Gain three levels.", root, foCharacter);
        gainThreeLevels.addResult(new DialogResultNode(fc -> fc.gainExperience((fc.getLevel() * 3 + 1 + 2) * 1000)));
        gainThreeLevels.addDemand(new DialogDemandNode(fc -> fc.getUnusedSkillPoints() + fc.getSkillPointsPerLevel() * 3 <= 99, "Gaining three levels would overflow unspent skill point above 99."));

        DialogAnswerNode gainSupportPerk = new DialogAnswerNode("Gain a support perk.", supportPerkQuestion, foCharacter);

        DialogAnswerNode mutate = new DialogAnswerNode("Mutate.", root, foCharacter);

        root.addAnswer(gainOneLevel);
        root.addAnswer(gainThreeLevels);
        root.addAnswer(gainSupportPerk);
        root.addAnswer(mutate);

        //  support perks
        PerkFactory.getSupportPerks().forEach(sp -> {
            DialogAnswerNode answer = new DialogAnswerNode(sp.getName(), root, foCharacter);
            answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(sp), "Support Perk already picked: " + sp.getName()));
            answer.addResult(new DialogResultNode(fc -> fc.addSupportPerk(sp)));
            supportPerkQuestion.addAnswer(answer);
        });
        DialogAnswerNode backToRoot = new DialogAnswerNode("[Back]", root, foCharacter);
        supportPerkQuestion.addAnswer(backToRoot);

        return root;
    }

    public static DialogQuestionNode createChooseCombatPerkDialog(FoCharacter foCharacter) {
        DialogQuestionNode root = new DialogQuestionNode(2, "Choose your perk.");
        PerkFactory.getCombatPerks().forEach(cp -> {
            DialogAnswerNode answer = new DialogAnswerNode(cp.getName(), root, foCharacter);
            answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(cp), "Combat Perk already picked: " + cp.getName()));
            answer.addResult(new DialogResultNode(fc -> fc.addCombatPerk(cp)));
            root.addAnswer(answer);
        });
        return root;
    }
}
