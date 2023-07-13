package net.fodev.foclassic.model.dialog;

import net.fodev.foclassic.model.fochar.*;

public class DialogFactory {
    public static int specialMutateLooseIndex = -1;

    public static final DialogQuestionNode EXIT = new DialogQuestionNode(0, "Exit!");

    public static DialogQuestionNode createMainDialog(FoCharacter foCharacter, FoCharacter oldCharacter) {

        DialogQuestionNode root = new DialogQuestionNode(2, "What would you like to do?");
        DialogQuestionNode supportPerkQuestion = new DialogQuestionNode(3, "Which support perk would you like to add?");
        DialogQuestionNode supportPerkShowAllQuestion = new DialogQuestionNode(4, "Which support perk would you like to add?");
        DialogQuestionNode readSkillBookQuestion = new DialogQuestionNode(5, "Which skill book would you like to read?");
        DialogQuestionNode mutateQuestion = new DialogQuestionNode(6,
                "The radiation of the wasteland has changed you! One of your traits, perks, specials, skill tags or skills might have mutated into something else...");

        //  main dialog
        DialogAnswerNode gainOneLevel = new DialogAnswerNode("Gain one level.", root, foCharacter);
        gainOneLevel.addDemand(new DialogDemandNode(fc -> fc.getUnusedSkillPoints() + fc.getSkillPointsPerLevel() <= 99, "Gaining one level would overflow unspent skill point above 99."));
        gainOneLevel.addResult(new DialogResultNode(fc -> fc.gainExperience(fc.getLevel() * 1000)));
        gainOneLevel.addResult(new DialogResultNode(fc -> FoCharacterFactory.copyTo(oldCharacter, fc)));
        gainOneLevel.addResult(new DialogResultNode(fc -> System.out.println("Gained one level. Character saved.")));

        DialogAnswerNode gainThreeLevels = new DialogAnswerNode("Gain three levels.", root, foCharacter);
        gainThreeLevels.addDemand(new DialogDemandNode(fc -> fc.getUnusedSkillPoints() + fc.getSkillPointsPerLevel() * 3 <= 99, "Gaining three levels would overflow unspent skill point above 99."));
        gainThreeLevels.addResult(new DialogResultNode(fc -> fc.gainExperience((fc.getLevel() * 3 + 1 + 2) * 1000)));
        gainThreeLevels.addResult(new DialogResultNode(fc -> FoCharacterFactory.copyTo(oldCharacter, fc)));
        gainThreeLevels.addResult(new DialogResultNode(fc -> System.out.println("Gained three level. Character saved.")));

        DialogAnswerNode gainSupportPerk = new DialogAnswerNode("Gain a support perk. (Available)", supportPerkQuestion, foCharacter);
        DialogAnswerNode gainSupportPerkShowAll = new DialogAnswerNode("Gain a support perk. (Show all)", supportPerkShowAllQuestion, foCharacter);
        DialogAnswerNode readSkillBook = new DialogAnswerNode("Read a skill book.", readSkillBookQuestion, foCharacter);
        DialogAnswerNode mutate = new DialogAnswerNode("Mutate.", mutateQuestion, foCharacter);

        DialogAnswerNode saveChanges = new DialogAnswerNode("Save changes.", root, foCharacter);
        saveChanges.addDemand(new DialogDemandNode(fc -> !fc.equals(oldCharacter), "Error: No character changes to save."));
        saveChanges.addResult(new DialogResultNode(fc -> FoCharacterFactory.copyTo(oldCharacter, fc)));
        saveChanges.addResult(new DialogResultNode(fc -> System.out.println("Character saved.")));

        root.addAnswer(gainOneLevel);
        root.addAnswer(gainThreeLevels);
        root.addAnswer(gainSupportPerk);
        root.addAnswer(gainSupportPerkShowAll);
        root.addAnswer(readSkillBook);
        root.addAnswer(mutate);
        root.addAnswer(saveChanges);

        //  support perks (available)
        addSupportPerkAnswers(foCharacter, root, supportPerkQuestion);
        addSupportPerkDemands(supportPerkQuestion, foCharacter);
        supportPerkQuestion.getAnswers().removeIf(a -> !a.areDemandsMet());

        //  support perks (show all)
        addSupportPerkAnswers(foCharacter, root, supportPerkShowAllQuestion);
        addSupportPerkDemands(supportPerkShowAllQuestion, foCharacter);

        //  read a book
        addReadSkillBookAnswers(foCharacter, root, readSkillBookQuestion);
        
        //  mutate
        addMutateAnswers(foCharacter, root, mutateQuestion);

        return root;
    }

    private static void addMutateAnswers(FoCharacter foCharacter, DialogQuestionNode root, DialogQuestionNode mutateQuestion) {
        mutateSpecials(foCharacter, mutateQuestion);

        DialogAnswerNode backToRoot = new DialogAnswerNode("[Back]", root, foCharacter);
        mutateQuestion.addAnswer(backToRoot);
    }

    private static DialogQuestionNode mutateSpecials(FoCharacter foCharacter, DialogQuestionNode mutateQuestion) {
        DialogQuestionNode mutateSpecialQuestion = new DialogQuestionNode(1, "Your S.P.E.C.I.A.L. attributes are mutating...");

        refreshMutateSpecials(foCharacter, mutateQuestion, mutateSpecialQuestion);

        DialogAnswerNode mutateSpecialAnswer = new DialogAnswerNode("Mutate S.P.E.C.I.A.L. attributes.", mutateSpecialQuestion, foCharacter);
        mutateQuestion.addAnswer(mutateSpecialAnswer);

        return mutateSpecialQuestion;
    }

    private static void refreshMutateSpecials(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        mutateSpecialQuestion.clear();

        DialogAnswerNode looseStrengthAnswer = new DialogAnswerNode("Loose Strength.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, looseStrengthAnswer, 0);
        DialogAnswerNode loosePerceptionAnswer = new DialogAnswerNode("Loose Perception.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, loosePerceptionAnswer, 1);
        DialogAnswerNode looseEnduranceAnswer = new DialogAnswerNode("Loose Endurance.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, looseEnduranceAnswer, 2);
        DialogAnswerNode looseCharismaAnswer = new DialogAnswerNode("Loose Charisma.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, looseCharismaAnswer, 3);
        DialogAnswerNode looseIntellectAnswer = new DialogAnswerNode("Loose Intellect.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, looseIntellectAnswer, 4);
        DialogAnswerNode looseAgilityAnswer = new DialogAnswerNode("Loose Agility.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, looseAgilityAnswer, 5);
        DialogAnswerNode looseLuckAnswer = new DialogAnswerNode("Loose Luck.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, looseLuckAnswer, 6);

        DialogAnswerNode gainStrengthAnswer = new DialogAnswerNode("Gain Strength.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainStrengthAnswer, 0);
        DialogAnswerNode gainPerceptionAnswer = new DialogAnswerNode("Gain Perception.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainPerceptionAnswer, 1);
        DialogAnswerNode gainEnduranceAnswer = new DialogAnswerNode("Gain Endurance.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainEnduranceAnswer, 2);
        DialogAnswerNode gainCharismaAnswer = new DialogAnswerNode("Gain Charisma.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainCharismaAnswer, 3);
        DialogAnswerNode gainIntellectAnswer = new DialogAnswerNode("Gain Intellect.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainIntellectAnswer, 4);
        DialogAnswerNode gainAgilityAnswer = new DialogAnswerNode("Gain Agility.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainAgilityAnswer, 5);
        DialogAnswerNode gainLuckAnswer = new DialogAnswerNode("Gain Luck.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainLuckAnswer, 6);

        DialogAnswerNode backToMutateSpecials = new DialogAnswerNode("[Back]", mutateQuestion, foCharacter);
        mutateSpecialQuestion.addAnswer(backToMutateSpecials);

        mutateSpecialQuestion.getAnswers().removeIf(a -> !a.areDemandsMet());

        mutateSpecialQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateSpecials(foCharacter, mutateQuestion, mutateSpecialQuestion)));
    }

    private static void addLooseSpecialsDemandsAndResults(DialogQuestionNode mutateSpecialQuestion, DialogAnswerNode looseSpecialAnswer, int specialIndex) {
        looseSpecialAnswer.addDemand(new DialogDemandNode(fc -> fc.getUnusedSpecialPoints() == 0, "Error: Must spend unused special points first."));
        looseSpecialAnswer.addDemand(new DialogDemandNode(fc -> fc.getSpecial(specialIndex).getValue() > 1, "Error: Special attribute must be higher than 1 to remove points in it."));
        looseSpecialAnswer.addResult(new DialogResultNode(fc -> fc.setUnusedSpecialPoints(fc.getUnusedSpecialPoints() + 1)));
        looseSpecialAnswer.addResult(new DialogResultNode(fc -> specialMutateLooseIndex = specialIndex));
        mutateSpecialQuestion.addAnswer(looseSpecialAnswer);
    }

    private static void addGainSpecialsDemandsAndResults(DialogQuestionNode mutateSpecialQuestion, DialogAnswerNode gainSpecialAnswer, int specialIndex) {
        gainSpecialAnswer.addDemand(new DialogDemandNode(fc -> fc.getUnusedSpecialPoints() > 0, "Error: No unused special points left."));
        gainSpecialAnswer.addDemand(new DialogDemandNode(fc -> fc.getSpecial(specialIndex).getValue() <= 9, "Error: Cannot raise special attribute above maximum."));
        gainSpecialAnswer.addResult(new DialogResultNode(fc -> fc.setUnusedSpecialPoints(fc.getUnusedSpecialPoints() - 1)));
        gainSpecialAnswer.addResult(new DialogResultNode(fc -> {
            fc.setSpecialValue(specialMutateLooseIndex, fc.getSpecial(specialMutateLooseIndex).getValue() - 1);
            fc.setSpecialValue(specialIndex, fc.getSpecial(specialIndex).getValue() + 1);
        }));
        mutateSpecialQuestion.addAnswer(gainSpecialAnswer);
    }

    private static void addSupportPerkAnswers(FoCharacter foCharacter, DialogQuestionNode root, DialogQuestionNode supportPerkQuestion) {
        PerkFactory.getSupportPerks().forEach(sp -> {
            DialogAnswerNode answer = new DialogAnswerNode(sp.getName(), root, foCharacter);
            answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(sp), "Support Perk already picked: " + sp.getName()));
            answer.addResult(new DialogResultNode(fc -> fc.addSupportPerk(sp)));
            answer.addResult(new DialogResultNode(fc -> System.out.println("Added support perk: " + sp.getName())));
            supportPerkQuestion.addAnswer(answer);
        });
        DialogAnswerNode backToRoot = new DialogAnswerNode("[Back]", root, foCharacter);
        supportPerkQuestion.addAnswer(backToRoot);
    }

    private static void addReadSkillBookAnswers(FoCharacter foCharacter, DialogQuestionNode root, DialogQuestionNode readSkillBookQuestion) {
        SkillBookFactory.getSkillBookProtos().forEach(sbp -> {
            DialogAnswerNode answer = new DialogAnswerNode(sbp.getName(), root, foCharacter);
            answer.addDemand(new DialogDemandNode(fc -> fc.canReadSkillBook(sbp.getName()), "Cannot read any more of " + sbp.getName() + " skill books."));
            answer.addResult(new DialogResultNode(fc -> fc.readSkillBook(sbp.getName())));
            answer.addResult(new DialogResultNode(fc -> System.out.printf("Read skill book: %s (%d/%d)\n",
                    sbp.getName(), fc.getSkillBookRead(sbp.getName()), fc.getSkillBookMaxUses(sbp.getName()) ) ) );
            readSkillBookQuestion.addAnswer(answer);
        });
        DialogAnswerNode backToRootReadSkillBook = new DialogAnswerNode("[Back]", root, foCharacter);
        readSkillBookQuestion.addAnswer(backToRootReadSkillBook);
    }

    private static void addSupportPerkDemands(DialogQuestionNode root, FoCharacter foCharacter) {
        DialogAnswerNode cautiousNature = root.getAnswer(PerkFactory.CAUTIOUS_NATURE);
        cautiousNature.addDemand(new DialogDemandNode(fc -> fc.getPerception() >= 6, "Missing requirement: Perception 6"));
        cautiousNature.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) >= 100,
                "Missing requirement: Outdoorsman >= 100%"));

        DialogAnswerNode deadManWalking = root.getAnswer(PerkFactory.DEAD_MAN_WALKING);
        deadManWalking.addDemand(new DialogDemandNode(fc -> fc.getIntellect() >= 5, "Missing requirement: Intelligence >= 5"));
        deadManWalking.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.DOCTOR) >= 50,
                "Missing requirement: Doctor >= 50%"));

        DialogAnswerNode demolitionExpert = root.getAnswer(PerkFactory.DEMOLITION_EXPERT);
        demolitionExpert.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.TRAPS) >= 125,
                "Missing requirement: Traps >= 125%"));

        DialogAnswerNode dismantler = root.getAnswer(PerkFactory.DISMANTLER);
        dismantler.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SCIENCE) >= 120,
                "Missing requirement: Science >= 120%"));

        DialogAnswerNode educated = root.getAnswer(PerkFactory.EDUCATED);
        educated.addDemand(new DialogDemandNode(fc -> fc.getIntellect() >= 8, "Missing requirement: Intelligence >= 8"));
        educated.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SCIENCE) >= 100,
                "Missing requirement: Science >= 100%"));

        DialogAnswerNode explorer = root.getAnswer(PerkFactory.EXPLORER);
        explorer.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) >= 150,
                "Missing requirement: Outdoorsman >= 150%"));

        DialogAnswerNode fasterHealing = root.getAnswer(PerkFactory.FASTER_HEALING);
        fasterHealing.addDemand(new DialogDemandNode(fc -> fc.getIntellect() >= 6, "Missing requirement: Intelligence >= 6"));
        fasterHealing.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.DOCTOR) >= 75,
                "Missing requirement: Doctor >= 75%"));

        DialogAnswerNode geckoSkinning = root.getAnswer(PerkFactory.GECKO_SKINNING);
        geckoSkinning.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) >= 50,
                "Missing requirement: Outdoorsman >= 50%"));

        DialogAnswerNode harmless = root.getAnswer(PerkFactory.HARMLESS);
        harmless.addDemand(new DialogDemandNode(fc -> fc.getCharisma() >= 6, "Missing requirement: Charisma >= 6"));
        harmless.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.STEAL) >= 125,
                "Missing requirement: Steal >= 125%"));

        DialogAnswerNode lightStep = root.getAnswer(PerkFactory.LIGHT_STEP);
        lightStep.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.TRAPS) >= 50,
                "Missing requirement: Traps >= 50%"));

        DialogAnswerNode magneticPersonality = root.getAnswer(PerkFactory.MAGNETIC_PERSONALITY);
        magneticPersonality.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SPEECH) >= 100,
                "Missing requirement: Speech >= 100%"));

        DialogAnswerNode masterThief = root.getAnswer(PerkFactory.MASTER_THIEF);
        masterThief.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.STEAL) >= 125,
                "Missing requirement: Steal >= 125%"));

        DialogAnswerNode mrFixit = root.getAnswer(PerkFactory.MR_FIXIT);
        mrFixit.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.REPAIR) >= 120,
                "Missing requirement: Repair >= 120%"));

        DialogAnswerNode negotiator = root.getAnswer(PerkFactory.NEGOTIATOR);
        negotiator.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.BARTER) >= 125,
                "Missing requirement: Barter >= 125%"));

        DialogAnswerNode packRat = root.getAnswer(PerkFactory.PACK_RAT);
        packRat.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 6, "Missing requirement: Level 6"));

        DialogAnswerNode pathfinder = root.getAnswer(PerkFactory.PATHFINDER);
        pathfinder.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) >= 150,
                "Missing requirement: Outdoorsman >= 150%"));

        DialogAnswerNode pickpocket = root.getAnswer(PerkFactory.PICKPOCKET);
        pickpocket.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.STEAL) >= 125,
                "Missing requirement: Steal >= 125%"));

        DialogAnswerNode radResistance = root.getAnswer(PerkFactory.RAD_RESISTANCE);
        radResistance.addDemand(new DialogDemandNode(fc -> fc.getIntellect() >= 7, "Missing requirement: Intelligence >= 7"));
        radResistance.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.DOCTOR) >= 100,
                "Missing requirement: Doctor >= 100%"));

        DialogAnswerNode ranger = root.getAnswer(PerkFactory.RANGER);
        ranger.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) >= 100,
                "Missing requirement: Outdoorsman >= 100%"));

        DialogAnswerNode scout = root.getAnswer(PerkFactory.SCOUT);
        scout.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) >= 150,
                "Missing requirement: Outdoorsman >= 150%"));

        DialogAnswerNode sexAppeal = root.getAnswer(PerkFactory.SEX_APPEAL);
        sexAppeal.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SPEECH) >= 75,
                "Missing requirement: Speech >= 75%"));

        DialogAnswerNode snakeEater = root.getAnswer(PerkFactory.SNAKE_EATER);
        snakeEater.addDemand(new DialogDemandNode(fc -> fc.getEndurance() >= 6, "Missing requirement: Endurance >= 6"));

        DialogAnswerNode speaker = root.getAnswer(PerkFactory.SPEAKER);
        speaker.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SPEECH) >= 125,
                "Missing requirement: Speech >= 125%"));

        DialogAnswerNode stealthGirl = root.getAnswer(PerkFactory.STEALTH_GIRL);
        stealthGirl.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SNEAK) >= 100
                && fc.getSkillValueByName(SkillFactory.REPAIR) >= 100,
                "Missing requirement: Sneak and Repair >= 100%"));

        DialogAnswerNode strongBack = root.getAnswer(PerkFactory.STRONG_BACK);
        strongBack.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 6, "Missing requirement: Level 6"));
        strongBack.addDemand(new DialogDemandNode(fc -> fc.getEndurance() >= 6, "Missing requirement: Endurance >= 6"));

        DialogAnswerNode swiftLearner = root.getAnswer(PerkFactory.SWIFT_LEARNER);
        swiftLearner.addDemand(new DialogDemandNode(fc -> fc.getIntellect() >= 6, "Missing requirement: Intelligence >= 6"));
        swiftLearner.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SCIENCE) >= 50,
                "Missing requirement: Science >= 50%"));

        DialogAnswerNode thief = root.getAnswer(PerkFactory.THIEF);
        thief.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.STEAL) >= 100,
                "Missing requirement: Steal >= 100%"));

        DialogAnswerNode treasureHunter = root.getAnswer(PerkFactory.TREASURE_HUNTER);
        treasureHunter.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.LOCKPICK) >= 125,
                "Missing requirement: Lockpick >= 125%"));

        DialogAnswerNode wayOfTheFruit = root.getAnswer(PerkFactory.WAY_OF_THE_FRUIT);
        wayOfTheFruit.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 3, "Missing requirement: Level 3"));
        wayOfTheFruit.addDemand(new DialogDemandNode(fc -> fc.getCharisma() >= 3, "Missing requirement: Charisma >= 3"));
    }

    public static DialogQuestionNode createChooseCombatPerkDialog(FoCharacter foCharacter) {
        DialogQuestionNode root = new DialogQuestionNode(2, "Choose your perk.");
        PerkFactory.getCombatPerks().forEach(cp -> {
            DialogAnswerNode answer = new DialogAnswerNode(cp.getName(), root, foCharacter);
            answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(cp), "Combat Perk already picked: " + cp.getName()));
            answer.addResult(new DialogResultNode(fc -> fc.addCombatPerk(cp)));
            root.addAnswer(answer);
        });

        addLevelThreeCombatPerkDemands(root);
        addLevelSixCombatPerkDemands(root);
        addLevelNineCombatPerkDemands(root);
        addLevelTwelveCombatPerkDemands(root);
        addLevelFifteenCombatPerkDemands(root);
        addLevelEighteenCombatPerkDemands(root);

        return root;
    }

    private static void addLevelThreeCombatPerkDemands(DialogQuestionNode dialogQuestionNode) {
        DialogAnswerNode moreCritical = dialogQuestionNode.getAnswer(PerkFactory.MORE_CRITICAL);
        moreCritical.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                        || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 100,
                "Missing requirement: SG, BG, EW, Close Combat or Throwing >= 100%"));

        DialogAnswerNode quickPockets = dialogQuestionNode.getAnswer(PerkFactory.QUICK_POCKETS);
        quickPockets.addDemand(new DialogDemandNode(fc -> fc.getAgility() >= 5, "Missing requirement: Agility >= 5"));

        DialogAnswerNode adrenalineRush = dialogQuestionNode.getAnswer(PerkFactory.ADRENALINE_RUSH);
        adrenalineRush.addDemand(new DialogDemandNode(fc -> fc.getStrength() >= 5, "Missing requirement: Strength >= 5"));

        DialogAnswerNode quickRecovery = dialogQuestionNode.getAnswer(PerkFactory.QUICK_RECOVERY);
        quickRecovery.addDemand(new DialogDemandNode(fc -> fc.getAgility() >= 6, "Missing requirement: Agility >= 6"));

        DialogAnswerNode weaponHandling = dialogQuestionNode.getAnswer(PerkFactory.WEAPON_HANDLING);
        weaponHandling.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 100,
                "Missing requirement: SG, BG, EW or Throwing >= 100%"));

    }

    private static void addLevelSixCombatPerkDemands(DialogQuestionNode dialogQuestionNode) {
        DialogAnswerNode inYourFace = dialogQuestionNode.getAnswer(PerkFactory.IN_YOUR_FACE);
        inYourFace.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 6, "Missing requirement: Level 6"));
        inYourFace.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 125,
                "Missing requirement: Close Combat >= 125%"));

        DialogAnswerNode evenMoreCriticals = dialogQuestionNode.getAnswer(PerkFactory.EVEN_MORE_CRITICALS);
        evenMoreCriticals.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 6, "Missing requirement: Level 6"));
        evenMoreCriticals.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 125
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 125
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 125
                        || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 125
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 125,
                "Missing requirement: SG, BG, EW, Close Combat or Throwing >= 125%"));

        DialogAnswerNode silentRunning = dialogQuestionNode.getAnswer(PerkFactory.SILENT_RUNNING);
        silentRunning.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 6, "Missing requirement: Level 6"));
        silentRunning.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SNEAK) >= 100,
                "Missing requirement: Sneak >= 100%"));

        DialogAnswerNode toughness = dialogQuestionNode.getAnswer(PerkFactory.TOUGHNESS);
        toughness.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 6, "Missing requirement: Level 6"));
        toughness.addDemand(new DialogDemandNode(fc -> fc.getEndurance() >= 4,
                "Missing requirement: Endurance >= 4"));

    }

    private static void addLevelNineCombatPerkDemands(DialogQuestionNode dialogQuestionNode) {
        DialogAnswerNode sharpShooter = dialogQuestionNode.getAnswer(PerkFactory.SHARPSHOOTER);
        sharpShooter.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        sharpShooter.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                        || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                        && fc.getIntellect() >= 3,
                "Missing requirement: SG, BG, EW, Close Combat or Throwing >= 150% and Intelligence >= 3"));

        DialogAnswerNode pyromaniac = dialogQuestionNode.getAnswer(PerkFactory.PYROMANIAC);
        pyromaniac.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        pyromaniac.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                        || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 100,
                "Missing requirement: SG, BG, EW, Close Combat or Throwing >= 100%"));

        DialogAnswerNode closeCombatMaster = dialogQuestionNode.getAnswer(PerkFactory.CLOSE_COMBAT_MASTER);
        closeCombatMaster.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        closeCombatMaster.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150,
                "Missing requirement: Close Combat ≥ 150%"));

        DialogAnswerNode bonusRangedDamage = dialogQuestionNode.getAnswer(PerkFactory.BONUS_RANGED_DAMAGE);
        bonusRangedDamage.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        bonusRangedDamage.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 150,
                "Missing requirement: SG, BG, EW or Throwing >= 150%"));

        DialogAnswerNode evenTougher = dialogQuestionNode.getAnswer(PerkFactory.EVEN_TOUGHER);
        evenTougher.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        evenTougher.addDemand(new DialogDemandNode(fc -> fc.getEndurance() >= 6, "Missing requirement: Endurance >= 6"));

        DialogAnswerNode stonewall = dialogQuestionNode.getAnswer(PerkFactory.STONEWALL);
        stonewall.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        stonewall.addDemand(new DialogDemandNode(fc -> fc.getStrength() >= 6, "Missing requirement: Strength >= 6"));

        DialogAnswerNode medic = dialogQuestionNode.getAnswer(PerkFactory.MEDIC);
        medic.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        medic.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.FIRST_AID) >= 125
                        && fc.getSkillValueByName(SkillFactory.DOCTOR) >= 125
                        && fc.getIntellect() >= 3,
                "Missing requirement: First Aid, Doctor ≥ 125% and Intelligence >= 3"));

        DialogAnswerNode heaveHo = dialogQuestionNode.getAnswer(PerkFactory.HEAVE_HO);
        heaveHo.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 9, "Missing requirement: Level 9"));
        heaveHo.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.THROWING) >= 125,
                "Missing requirement: Throwing ≥ 125%"));
    }

    private static void addLevelTwelveCombatPerkDemands(DialogQuestionNode dialogQuestionNode) {
        DialogAnswerNode gainAgility = dialogQuestionNode.getAnswer(PerkFactory.GAIN_AGILITY);
        gainAgility.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        gainAgility.addDemand(new DialogDemandNode(fc -> fc.getAgility() < 9, "Missing requirement: Agility <= 9"));

        DialogAnswerNode gainCharisma = dialogQuestionNode.getAnswer(PerkFactory.GAIN_CHARISMA);
        gainCharisma.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        gainCharisma.addDemand(new DialogDemandNode(fc -> fc.getCharisma() < 9, "Missing requirement: Charisma <= 9"));

        DialogAnswerNode gainEndurance = dialogQuestionNode.getAnswer(PerkFactory.GAIN_ENDURANCE);
        gainEndurance.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        gainEndurance.addDemand(new DialogDemandNode(fc -> fc.getEndurance() < 9, "Missing requirement: Endurance <= 9"));

        DialogAnswerNode gainIntelligence = dialogQuestionNode.getAnswer(PerkFactory.GAIN_INTELLIGENCE);
        gainIntelligence.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        gainIntelligence.addDemand(new DialogDemandNode(fc -> fc.getIntellect() < 9, "Missing requirement: Intelligence <= 9"));

        DialogAnswerNode gainLuck = dialogQuestionNode.getAnswer(PerkFactory.GAIN_LUCK);
        gainLuck.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        gainLuck.addDemand(new DialogDemandNode(fc -> fc.getLuck() < 9, "Missing requirement: Luck <= 9"));

        DialogAnswerNode gainPerception = dialogQuestionNode.getAnswer(PerkFactory.GAIN_PERCEPTION);
        gainPerception.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        gainPerception.addDemand(new DialogDemandNode(fc -> fc.getPerception() < 9, "Missing requirement: Perception <= 9"));

        DialogAnswerNode gainStrength = dialogQuestionNode.getAnswer(PerkFactory.GAIN_STRENGTH);
        gainStrength.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        gainStrength.addDemand(new DialogDemandNode(fc -> fc.getStrength() < 9, "Missing requirement: Strength <= 9"));

        DialogAnswerNode betterCriticals = dialogQuestionNode.getAnswer(PerkFactory.BETTER_CRITICALS);
        betterCriticals.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        betterCriticals.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 175
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 175
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 175
                        || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 175
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 175,
                "Missing requirement: SG, BG, EW, Close Combat or Throwing >= 175%"));

        DialogAnswerNode ghost = dialogQuestionNode.getAnswer(PerkFactory.GHOST);
        ghost.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        ghost.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SNEAK) >= 150,
                "Missing requirement: Sneak >= 150%"));

        DialogAnswerNode lifeGiver = dialogQuestionNode.getAnswer(PerkFactory.LIFEGIVER);
        lifeGiver.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));

        DialogAnswerNode actionBoy = dialogQuestionNode.getAnswer(PerkFactory.ACTIONBOY);
        actionBoy.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 12, "Missing requirement: Level 12"));
        actionBoy.addDemand(new DialogDemandNode(fc -> fc.getAgility() >= 6, "Missing requirement: Agility >= 6"));
    }

    private static void addLevelFifteenCombatPerkDemands(DialogQuestionNode dialogQuestionNode) {
        DialogAnswerNode actionBoy2 = dialogQuestionNode.getAnswer(PerkFactory.ACTIONBOY2);
        actionBoy2.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        actionBoy2.addDemand(new DialogDemandNode(fc -> fc.getAgility() >= 6, "Missing requirement: Agility >= 6"));
        actionBoy2.addDemand(new DialogDemandNode(fc -> fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.ACTIONBOY)),
                "Missing requirement: Action boy"));

        DialogAnswerNode lifeGiver2 = dialogQuestionNode.getAnswer(PerkFactory.LIFEGIVER2);
        lifeGiver2.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        lifeGiver2.addDemand(new DialogDemandNode(fc -> fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.LIFEGIVER)),
                "Missing requirement: Lifegiver"));

        DialogAnswerNode liveWire = dialogQuestionNode.getAnswer(PerkFactory.LIVEWIRE);
        liveWire.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        liveWire.addDemand(new DialogDemandNode(fc -> fc.getAgility() >= 6, "Missing requirement: Agility >= 6"));

        DialogAnswerNode manOfSteel = dialogQuestionNode.getAnswer(PerkFactory.MAN_OF_STEEL);
        manOfSteel.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        manOfSteel.addDemand(new DialogDemandNode(fc -> fc.getEndurance() >= 8, "Missing requirement: Endurance >= 8"));

        DialogAnswerNode fieldMedic = dialogQuestionNode.getAnswer(PerkFactory.FIELD_MEDIC);
        fieldMedic.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        fieldMedic.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.FIRST_AID) >= 175
                        && fc.getSkillValueByName(SkillFactory.DOCTOR) >= 175
                        && fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MEDIC)),
                "Missing requirement: First Aid >= 175%, Doctor >= 175 and Medic perk"));

        DialogAnswerNode moreRangedDamage = dialogQuestionNode.getAnswer(PerkFactory.MORE_RANGED_DAMAGE);
        moreRangedDamage.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        moreRangedDamage.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 200
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 200
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 200
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 200,
                "Missing requirement: SG, BG, EW or Throwing >= 200%"));

        DialogAnswerNode silentDeath = dialogQuestionNode.getAnswer(PerkFactory.SILENT_DEATH);
        silentDeath.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        silentDeath.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(SkillFactory.SNEAK) >= 175,
                "Missing requirement: Sneak >= 175%"));

        DialogAnswerNode ironLimbs = dialogQuestionNode.getAnswer(PerkFactory.IRON_LIMBS);
        ironLimbs.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        ironLimbs.addDemand(new DialogDemandNode(fc -> fc.getEndurance() >= 6 && fc.getStrength() >= 6,
                "Missing requirement: Strength >= 6 and Endurance >= 6"));

        DialogAnswerNode dodger = dialogQuestionNode.getAnswer(PerkFactory.DODGER);
        dodger.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 15, "Missing requirement: Level 15"));
        dodger.addDemand(new DialogDemandNode(fc ->
                fc.getAgility() >= 8 && fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 175,
                "Missing requirement: Agility >= 6 and Close Combat >= 175"));
    }

    private static void addLevelEighteenCombatPerkDemands(DialogQuestionNode dialogQuestionNode) {
        DialogAnswerNode dodger2 = dialogQuestionNode.getAnswer(PerkFactory.DODGER2);
        dodger2.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 18, "Missing requirement: Level 18"));
        dodger2.addDemand(new DialogDemandNode(fc ->
                fc.getAgility() >= 10
                        && fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 175
                        && fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.DODGER)),
                "Missing requirement: Agility >= 6, Close Combat >= 175 and Dodger"));

        DialogAnswerNode lifeGiver3 = dialogQuestionNode.getAnswer(PerkFactory.LIFEGIVER3);
        lifeGiver3.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 18, "Missing requirement: Level 18"));
        lifeGiver3.addDemand(new DialogDemandNode(fc -> fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.LIFEGIVER2)),
                "Missing requirement: Lifegiver (2)"));

        DialogAnswerNode bonusRateOfAttack = dialogQuestionNode.getAnswer(PerkFactory.BONUS_RATE_OF_ATTACK);
        bonusRateOfAttack.addDemand(new DialogDemandNode(fc -> fc.getLevel() >= 18, "Missing requirement: Level 18"));
        bonusRateOfAttack.addDemand(new DialogDemandNode(fc ->
                fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 180
                        || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 180
                        || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 180
                        || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 180
                        || fc.getSkillValueByName(SkillFactory.THROWING) >= 180,
                "Missing requirement: SG, BG, EW, Close Combat or Throwing >= 180%"));
    }
}
