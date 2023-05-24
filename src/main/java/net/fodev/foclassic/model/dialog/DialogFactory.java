package net.fodev.foclassic.model.dialog;

public class DialogFactory {

    public static final DialogQuestionNode EXIT = new DialogQuestionNode(0, "Exit!");

    public static DialogQuestionNode createRoot() {

        DialogQuestionNode root = new DialogQuestionNode(2, "What would you like to do?");
        DialogQuestionNode supportPerk = new DialogQuestionNode(3, "Which support perk would you like to add.");

        DialogAnswerNode gainOneLevel = new DialogAnswerNode("Gain one level.", root);
        DialogAnswerNode gainThreeLevels = new DialogAnswerNode("Gain three levels.", root);
        DialogAnswerNode gainSupportPerk = new DialogAnswerNode("Gain a support perk.", supportPerk);
        DialogAnswerNode mutate = new DialogAnswerNode("Mutate.", root);
        root.addAnswer(gainOneLevel);
        root.addAnswer(gainThreeLevels);
        root.addAnswer(gainSupportPerk);
        root.addAnswer(mutate);

        DialogAnswerNode supportPerkAwareness = new DialogAnswerNode("Awareness", root);
        DialogAnswerNode supportPerkCautiousNature = new DialogAnswerNode("Cautious Nature", root);
        DialogAnswerNode supportPerkEducated = new DialogAnswerNode("Educated", root);
        DialogAnswerNode supportPerkGeckoSkinning = new DialogAnswerNode("Gecko Skinning", root);
        DialogAnswerNode supportPerkPackRat = new DialogAnswerNode("Pack Rat", root);
        DialogAnswerNode supportPerkMagneticPersonality = new DialogAnswerNode("Magnetic Personality", root);
        DialogAnswerNode supportPerkStealthGirl = new DialogAnswerNode("Stealth Girl", root);
        DialogAnswerNode supportPerkStrongBack = new DialogAnswerNode("Strong Back", root);
        DialogAnswerNode supportPerkWayOfTheFruit = new DialogAnswerNode("Way of the Fruit", root);
        supportPerk.addAnswer(supportPerkAwareness);
        supportPerk.addAnswer(supportPerkCautiousNature);
        supportPerk.addAnswer(supportPerkEducated);
        supportPerk.addAnswer(supportPerkGeckoSkinning);
        supportPerk.addAnswer(supportPerkPackRat);
        supportPerk.addAnswer(supportPerkMagneticPersonality);
        supportPerk.addAnswer(supportPerkStealthGirl);
        supportPerk.addAnswer(supportPerkStrongBack);
        supportPerk.addAnswer(supportPerkWayOfTheFruit);

        return root;
    }
}
