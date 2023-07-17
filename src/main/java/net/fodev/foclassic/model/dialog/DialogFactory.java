package net.fodev.foclassic.model.dialog;

import net.fodev.foclassic.controller.FoCharacterRuleset;
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
        refreshMutateAnswers(foCharacter, root, mutateQuestion);
        mutateQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateAnswers(foCharacter, root, mutateQuestion)));
    }

    private static void refreshMutateAnswers(FoCharacter foCharacter, DialogQuestionNode root, DialogQuestionNode mutateQuestion) {
        mutateQuestion.clear();

        mutateSpecials(foCharacter, mutateQuestion);
        mutateSkills(foCharacter, mutateQuestion);
        mutateTraits(foCharacter, mutateQuestion);
        mutateCombatPerks(foCharacter, mutateQuestion);
        mutateSupportPerks(foCharacter, mutateQuestion);

        DialogAnswerNode backToRoot = new DialogAnswerNode("[Back]", root, foCharacter);
        mutateQuestion.addAnswer(backToRoot);

        mutateQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateAnswers(foCharacter, root, mutateQuestion)));
    }

    private static void mutateSkills(FoCharacter foCharacter, DialogQuestionNode mutateQuestion) {
        DialogQuestionNode mutateSkillsQuestion = new DialogQuestionNode(1, "Your skills are mutating... \n[Select skill to free up skill points from]");

        refreshMutateSkills(foCharacter, mutateQuestion, mutateSkillsQuestion);

        DialogAnswerNode mutateSkillsAnswer = new DialogAnswerNode("Mutate skills.", mutateSkillsQuestion, foCharacter);
        mutateQuestion.addAnswer(mutateSkillsAnswer);
    }

    private static void refreshMutateSkills(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSkillsQuestion) {
        mutateSkillsQuestion.clear();

        addMutateSmallGunsAnswer(foCharacter, mutateSkillsQuestion);
        addMutateBigGunsAnswer(foCharacter, mutateSkillsQuestion);
        addMutateEnergyWeaponsAnswer(foCharacter, mutateSkillsQuestion);
        addMutateCloseCombatAnswer(foCharacter, mutateSkillsQuestion);
        addMutateThrowingAnswer(foCharacter, mutateSkillsQuestion);
        addMutateScavengingAnswer(foCharacter, mutateSkillsQuestion);
        addMutateFirstAidAnswer(foCharacter, mutateSkillsQuestion);
        addMutateDoctorAnswer(foCharacter, mutateSkillsQuestion);
        addMutateSneakAnswer(foCharacter, mutateSkillsQuestion);
        addMutateLockpickAnswer(foCharacter, mutateSkillsQuestion);
        addMutateStealAnswer(foCharacter, mutateSkillsQuestion);
        addMutateTrapsAnswer(foCharacter, mutateSkillsQuestion);
        addMutateScienceAnswer(foCharacter, mutateSkillsQuestion);
        addMutateRepairAnswer(foCharacter, mutateSkillsQuestion);
        addMutateSpeechAnswer(foCharacter, mutateSkillsQuestion);
        addMutateBarterAnswer(foCharacter, mutateSkillsQuestion);
        addMutateGamblingAnswer(foCharacter, mutateSkillsQuestion);
        addMutateOutdoorsmanAnswer(foCharacter, mutateSkillsQuestion);

        DialogAnswerNode backToMutateSkills = new DialogAnswerNode("[Back]", mutateQuestion, foCharacter);
        mutateSkillsQuestion.addAnswer(backToMutateSkills);

        mutateSkillsQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateSkills(foCharacter, mutateQuestion, mutateSkillsQuestion)));
    }

    private static void addMutateSmallGunsAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode mutateSmallGunsAnswer = new DialogAnswerNode(SkillFactory.SMALL_GUNS, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, mutateSmallGunsAnswer, SkillFactory.SMALL_GUNS);
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.WEAPON_HANDLING))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 101 : 100)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.WEAPON_HANDLING));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.PYROMANIAC))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 101 : 100)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.PYROMANIAC));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_CRITICAL))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 101 : 100)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_CRITICAL));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.EVEN_MORE_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 125
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 125
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 125
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 125
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 126 : 125)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.EVEN_MORE_CRITICALS));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 151 : 150)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RANGED_DAMAGE));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 151 : 150)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.SHARPSHOOTER));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BETTER_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 175
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 175
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 175
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 175
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 176 : 175)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.BETTER_CRITICALS));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RATE_OF_ATTACK))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 180
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 180
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 180
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 180
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 181 : 180)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RATE_OF_ATTACK));
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 200
                    || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 200
                    || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 200
                    || fc.getSkillValueByName(SkillFactory.THROWING) >= 200
                    || fc.getSkillValueByName(SkillFactory.SMALL_GUNS) > (fc.isTaggedSkill(SkillFactory.SMALL_GUNS) ? 201 : 200)
                ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_RANGED_DAMAGE));
    }

    private static void addMutateBigGunsAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.BIG_GUNS, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.BIG_GUNS);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.WEAPON_HANDLING))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.WEAPON_HANDLING));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.PYROMANIAC))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.PYROMANIAC));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_CRITICAL))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_CRITICAL));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.EVEN_MORE_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 125
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 125
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 125
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 125
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 126 : 125)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.EVEN_MORE_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RANGED_DAMAGE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.SHARPSHOOTER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BETTER_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 175
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 175
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 175
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 175
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 176 : 175)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BETTER_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RATE_OF_ATTACK))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 180
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 180
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 180
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 180
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 181 : 180)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RATE_OF_ATTACK));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 200
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 200
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 200
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 200
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) > (fc.isTaggedSkill(SkillFactory.BIG_GUNS) ? 201 : 200)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_RANGED_DAMAGE));
    }

    private static void addMutateEnergyWeaponsAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.ENERGY_WEAPONS, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.ENERGY_WEAPONS);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.WEAPON_HANDLING))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.WEAPON_HANDLING));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.PYROMANIAC))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.PYROMANIAC));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_CRITICAL))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_CRITICAL));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.EVEN_MORE_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 125
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 125
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 125
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 125
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 126 : 125)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.EVEN_MORE_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RANGED_DAMAGE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.SHARPSHOOTER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BETTER_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 175
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 175
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 175
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 175
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 176 : 175)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BETTER_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RATE_OF_ATTACK))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 180
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 180
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 180
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 180
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 181 : 180)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RATE_OF_ATTACK));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 200
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 200
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 200
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 200
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) > (fc.isTaggedSkill(SkillFactory.ENERGY_WEAPONS) ? 201 : 200)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_RANGED_DAMAGE));
    }

    private static void addMutateCloseCombatAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.CLOSE_COMBAT, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.CLOSE_COMBAT);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.PYROMANIAC))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.PYROMANIAC));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_CRITICAL))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_CRITICAL));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.EVEN_MORE_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 125
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 125
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 125
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 125
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 126 : 125)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.EVEN_MORE_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RANGED_DAMAGE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.SHARPSHOOTER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BETTER_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 175
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 175
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 175
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 175
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 176 : 175)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BETTER_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RATE_OF_ATTACK))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 180
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 180
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 180
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 180
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 181 : 180)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RATE_OF_ATTACK));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 200
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 200
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 200
                || fc.getSkillValueByName(SkillFactory.THROWING) >= 200
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 201 : 200)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_RANGED_DAMAGE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.IN_YOUR_FACE))
                || (fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.IN_YOUR_FACE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.CLOSE_COMBAT_MASTER))
                || (fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.CLOSE_COMBAT_MASTER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.DODGER))
                || (fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.DODGER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.DODGER2))
                || (fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) > (fc.isTaggedSkill(SkillFactory.CLOSE_COMBAT) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.DODGER2));
    }

    private static void addMutateThrowingAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.THROWING, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.THROWING);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.WEAPON_HANDLING))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.WEAPON_HANDLING));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.PYROMANIAC))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.PYROMANIAC));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_CRITICAL))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 100
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 100
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 100
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 101 : 100)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_CRITICAL));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.EVEN_MORE_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 125
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 125
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 125
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 125
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 126 : 125)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.EVEN_MORE_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RANGED_DAMAGE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 150
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 150
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 150
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 151 : 150)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.SHARPSHOOTER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BETTER_CRITICALS))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 175
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 175
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 175
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 175
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 176 : 175)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BETTER_CRITICALS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.BONUS_RATE_OF_ATTACK))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 180
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 180
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 180
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 180
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 181 : 180)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.BONUS_RATE_OF_ATTACK));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MORE_RANGED_DAMAGE))
                || (fc.getSkillValueByName(SkillFactory.SMALL_GUNS) >= 200
                || fc.getSkillValueByName(SkillFactory.BIG_GUNS) >= 200
                || fc.getSkillValueByName(SkillFactory.ENERGY_WEAPONS) >= 200
                || fc.getSkillValueByName(SkillFactory.CLOSE_COMBAT) >= 200
                || fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 201 : 200)
        ),
                "Error: Perk limiting mutation - " + PerkFactory.MORE_RANGED_DAMAGE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.HEAVE_HO))
                || (fc.getSkillValueByName(SkillFactory.THROWING) > (fc.isTaggedSkill(SkillFactory.THROWING) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.HEAVE_HO));
    }

    private static void addMutateScavengingAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.SCAVENGING, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.SCAVENGING);
        answer.addDemand(new DialogDemandNode(fc -> false, "Error: Your scavenging skill does not seem to be affected by mutation."));
    }

    private static void addMutateFirstAidAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.FIRST_AID, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.FIRST_AID);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MEDIC))
                || (fc.getSkillValueByName(SkillFactory.FIRST_AID) > (fc.isTaggedSkill(SkillFactory.FIRST_AID) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.MEDIC));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.FIELD_MEDIC))
                || (fc.getSkillValueByName(SkillFactory.FIRST_AID) > (fc.isTaggedSkill(SkillFactory.FIRST_AID) ? 176 : 175)),
                "Error: Perk limiting mutation - " + PerkFactory.FIELD_MEDIC));
    }

    private static void addMutateDoctorAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.DOCTOR, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.DOCTOR);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.DEAD_MAN_WALKING))
                || (fc.getSkillValueByName(SkillFactory.DOCTOR) > (fc.isTaggedSkill(SkillFactory.DOCTOR) ? 51 : 50)),
                "Error: Perk limiting mutation - " + PerkFactory.DEAD_MAN_WALKING));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.FASTER_HEALING))
                || (fc.getSkillValueByName(SkillFactory.DOCTOR) > (fc.isTaggedSkill(SkillFactory.DOCTOR) ? 76 : 75)),
                "Error: Perk limiting mutation - " + PerkFactory.FASTER_HEALING));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.RAD_RESISTANCE))
                || (fc.getSkillValueByName(SkillFactory.DOCTOR) > (fc.isTaggedSkill(SkillFactory.DOCTOR) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.RAD_RESISTANCE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.MEDIC))
                || (fc.getSkillValueByName(SkillFactory.DOCTOR) > (fc.isTaggedSkill(SkillFactory.DOCTOR) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.MEDIC));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.FIELD_MEDIC))
                || (fc.getSkillValueByName(SkillFactory.DOCTOR) > (fc.isTaggedSkill(SkillFactory.DOCTOR) ? 176 : 175)),
                "Error: Perk limiting mutation - " + PerkFactory.FIELD_MEDIC));
    }

    private static void addMutateSneakAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.SNEAK, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.SNEAK);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SILENT_RUNNING))
                || (fc.getSkillValueByName(SkillFactory.SNEAK) > (fc.isTaggedSkill(SkillFactory.SNEAK) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.SILENT_RUNNING));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GHOST))
                || (fc.getSkillValueByName(SkillFactory.SNEAK) > (fc.isTaggedSkill(SkillFactory.SNEAK) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.GHOST));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SILENT_DEATH))
                || (fc.getSkillValueByName(SkillFactory.SNEAK) > (fc.isTaggedSkill(SkillFactory.SNEAK) ? 176 : 175)),
                "Error: Perk limiting mutation - " + PerkFactory.SILENT_DEATH));
    }

    private static void addMutateLockpickAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.LOCKPICK, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.LOCKPICK);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.TREASURE_HUNTER))
                || (fc.getSkillValueByName(SkillFactory.LOCKPICK) > (fc.isTaggedSkill(SkillFactory.LOCKPICK) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.TREASURE_HUNTER));
    }

    private static void addMutateStealAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.STEAL, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.STEAL);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.THIEF))
                || (fc.getSkillValueByName(SkillFactory.STEAL) > (fc.isTaggedSkill(SkillFactory.STEAL) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.THIEF));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.PICKPOCKET))
                || (fc.getSkillValueByName(SkillFactory.STEAL) > (fc.isTaggedSkill(SkillFactory.STEAL) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.PICKPOCKET));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.MASTER_THIEF))
                || (fc.getSkillValueByName(SkillFactory.STEAL) > (fc.isTaggedSkill(SkillFactory.STEAL) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.MASTER_THIEF));
    }

    private static void addMutateTrapsAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.TRAPS, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.TRAPS);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.DEMOLITION_EXPERT))
                || (fc.getSkillValueByName(SkillFactory.TRAPS) > (fc.isTaggedSkill(SkillFactory.TRAPS) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.DEMOLITION_EXPERT));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.LIGHT_STEP))
                || (fc.getSkillValueByName(SkillFactory.TRAPS) > (fc.isTaggedSkill(SkillFactory.TRAPS) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.LIGHT_STEP));
    }

    private static void addMutateScienceAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.SCIENCE, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.SCIENCE);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.SWIFT_LEARNER))
                || (fc.getSkillValueByName(SkillFactory.SCIENCE) > (fc.isTaggedSkill(SkillFactory.SCIENCE) ? 51 : 50)),
                "Error: Perk limiting mutation - " + PerkFactory.SWIFT_LEARNER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.EDUCATED))
                || (fc.getSkillValueByName(SkillFactory.SCIENCE) > (fc.isTaggedSkill(SkillFactory.SCIENCE) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.EDUCATED));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.DISMANTLER))
                || (fc.getSkillValueByName(SkillFactory.SCIENCE) > (fc.isTaggedSkill(SkillFactory.SCIENCE) ? 121 : 120)),
                "Error: Perk limiting mutation - " + PerkFactory.DISMANTLER));
    }

    private static void addMutateRepairAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.REPAIR, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.REPAIR);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.STEALTH_GIRL))
                || (fc.getSkillValueByName(SkillFactory.REPAIR) > (fc.isTaggedSkill(SkillFactory.REPAIR) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.STEALTH_GIRL));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.MR_FIXIT))
                || (fc.getSkillValueByName(SkillFactory.REPAIR) > (fc.isTaggedSkill(SkillFactory.REPAIR) ? 121 : 120)),
                "Error: Perk limiting mutation - " + PerkFactory.MR_FIXIT));
    }

    private static void addMutateSpeechAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.SPEECH, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.SPEECH);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.MAGNETIC_PERSONALITY))
                || (fc.getSkillValueByName(SkillFactory.SPEECH) > (fc.isTaggedSkill(SkillFactory.SPEECH) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.MAGNETIC_PERSONALITY));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.SPEAKER))
                || (fc.getSkillValueByName(SkillFactory.SPEECH) > (fc.isTaggedSkill(SkillFactory.SPEECH) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.SPEAKER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.SEX_APPEAL))
                || (fc.getSkillValueByName(SkillFactory.SPEECH) > (fc.isTaggedSkill(SkillFactory.SPEECH) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.SEX_APPEAL));
    }

    private static void addMutateBarterAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.BARTER, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.BARTER);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.NEGOTIATOR))
                || (fc.getSkillValueByName(SkillFactory.BARTER) > (fc.isTaggedSkill(SkillFactory.BARTER) ? 126 : 125)),
                "Error: Perk limiting mutation - " + PerkFactory.NEGOTIATOR));
    }

    private static void addMutateGamblingAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.GAMBLING, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.GAMBLING);
    }

    private static void addMutateOutdoorsmanAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSkillsQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode(SkillFactory.OUTDOORSMAN, mutateSkillsQuestion, foCharacter);
        addMutateSkillDemandsResults(mutateSkillsQuestion, answer, SkillFactory.OUTDOORSMAN);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.SNAKE_EATER))
                || (fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) > (fc.isTaggedSkill(SkillFactory.OUTDOORSMAN) ? 76 : 75)),
                "Error: Perk limiting mutation - " + PerkFactory.SNAKE_EATER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.CAUTIOUS_NATURE))
                || (fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) > (fc.isTaggedSkill(SkillFactory.OUTDOORSMAN) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.CAUTIOUS_NATURE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.RANGER))
                || (fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) > (fc.isTaggedSkill(SkillFactory.OUTDOORSMAN) ? 101 : 100)),
                "Error: Perk limiting mutation - " + PerkFactory.RANGER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.PATHFINDER))
                || (fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) > (fc.isTaggedSkill(SkillFactory.OUTDOORSMAN) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.PATHFINDER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.EXPLORER))
                || (fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) > (fc.isTaggedSkill(SkillFactory.OUTDOORSMAN) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.EXPLORER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.SCOUT))
                || (fc.getSkillValueByName(SkillFactory.OUTDOORSMAN) > (fc.isTaggedSkill(SkillFactory.OUTDOORSMAN) ? 151 : 150)),
                "Error: Perk limiting mutation - " + PerkFactory.SCOUT));
    }

    private static void addMutateSkillDemandsResults(DialogQuestionNode mutateSkillsQuestion, DialogAnswerNode mutateSmallGunsAnswer, String skillName) {
        mutateSmallGunsAnswer.addDemand(new DialogDemandNode(fc -> fc.getSkillValueByName(skillName) >
                FoCharacterRuleset.getMinimumSkillValueWithoutPointsSpentByName(fc, skillName), "Error: Cannot remove more points from " + skillName + "."));
        mutateSmallGunsAnswer.addResult(new DialogResultNode(fc -> fc.unraiseSkill(skillName, FoCharacterRuleset.getMinimumSkillValueWithoutPointsSpentByName(fc, skillName))));
        mutateSkillsQuestion.addAnswer(mutateSmallGunsAnswer);
    }

    private static void mutateSpecials(FoCharacter foCharacter, DialogQuestionNode mutateQuestion) {
        DialogQuestionNode mutateSpecialQuestion = new DialogQuestionNode(1, "Your S.P.E.C.I.A.L. attributes are mutating...");

        refreshMutateSpecials(foCharacter, mutateQuestion, mutateSpecialQuestion);

        DialogAnswerNode mutateSpecialAnswer = new DialogAnswerNode("Mutate S.P.E.C.I.A.L. attributes.", mutateSpecialQuestion, foCharacter);
        mutateQuestion.addAnswer(mutateSpecialAnswer);
    }

    private static void refreshMutateSpecials(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        mutateSpecialQuestion.clear();

        addMutateLooseStrengthAnswer(foCharacter, mutateSpecialQuestion);
        addMutateLoosePerceptionAnswer(foCharacter, mutateSpecialQuestion);
        addMutateLooseEnduranceAnswer(foCharacter, mutateSpecialQuestion);
        addMutateLooseCharismaAnswer(foCharacter, mutateSpecialQuestion);
        addMutateLooseIntelligenceAnswer(foCharacter, mutateSpecialQuestion);
        addMutateLooseAgilityAnswer(foCharacter, mutateSpecialQuestion);
        addMutateLooseLuckAnswer(foCharacter, mutateSpecialQuestion);

        addMutateGainStrengthAnswer(foCharacter, mutateQuestion, mutateSpecialQuestion);
        addMutateGainPerceptionAnswer(foCharacter, mutateQuestion, mutateSpecialQuestion);
        addMutateGainEnduranceAnswer(foCharacter, mutateQuestion, mutateSpecialQuestion);
        addMutateGainCharismaAnswer(foCharacter, mutateQuestion, mutateSpecialQuestion);
        addMutateGainIntelligenceAnswer(foCharacter, mutateQuestion, mutateSpecialQuestion);
        addMutateGainAgilityAnswer(foCharacter, mutateQuestion, mutateSpecialQuestion);
        addMutateGainLuckAnswer(foCharacter, mutateQuestion, mutateSpecialQuestion);

        DialogAnswerNode backToMutateSpecials = new DialogAnswerNode("[Back]", mutateQuestion, foCharacter);
        mutateSpecialQuestion.addAnswer(backToMutateSpecials);

        mutateSpecialQuestion.getAnswers().removeIf(a -> !a.areDemandsMet());

        mutateSpecialQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateSpecials(foCharacter, mutateQuestion, mutateSpecialQuestion)));
    }

    private static void addMutateGainLuckAnswer(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode gainLuckAnswer = new DialogAnswerNode("Gain Luck.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainLuckAnswer, 6);
        gainLuckAnswer.addResult(new DialogResultNode(fc -> {
            updateLooseSpecial(fc);
            FoCharacterRuleset.updateLuck(fc, 1);
        }));
    }

    private static void addMutateGainAgilityAnswer(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode gainAgilityAnswer = new DialogAnswerNode("Gain Agility.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainAgilityAnswer, 5);
        gainAgilityAnswer.addResult(new DialogResultNode(fc -> {
            updateLooseSpecial(fc);
            FoCharacterRuleset.updateAgility(fc, 1);
        }));
    }

    private static void addMutateGainIntelligenceAnswer(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode gainIntellectAnswer = new DialogAnswerNode("Gain Intellect.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainIntellectAnswer, 4);
        gainIntellectAnswer.addResult(new DialogResultNode(fc -> {
            updateLooseSpecial(fc);
            FoCharacterRuleset.updateIntellect(fc, 1);
        }));
    }

    private static void addMutateGainCharismaAnswer(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode gainCharismaAnswer = new DialogAnswerNode("Gain Charisma.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainCharismaAnswer, 3);
        gainCharismaAnswer.addResult(new DialogResultNode(fc -> {
            updateLooseSpecial(fc);
            FoCharacterRuleset.updateCharisma(fc, 1);
        }));
    }

    private static void addMutateGainEnduranceAnswer(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode gainEnduranceAnswer = new DialogAnswerNode("Gain Endurance.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainEnduranceAnswer, 2);
        gainEnduranceAnswer.addResult(new DialogResultNode(fc -> {
            updateLooseSpecial(fc);
            FoCharacterRuleset.updateEndurance(fc, 1);
        }));
    }

    private static void addMutateGainPerceptionAnswer(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode gainPerceptionAnswer = new DialogAnswerNode("Gain Perception.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainPerceptionAnswer, 1);
        gainPerceptionAnswer.addResult(new DialogResultNode(fc -> {
            updateLooseSpecial(fc);
            FoCharacterRuleset.updatePerception(fc, 1);
        }));
    }

    private static void updateLooseSpecial(FoCharacter fc) {
        switch (specialMutateLooseIndex) {
            case 0: FoCharacterRuleset.updateStrength(fc, -1);
            case 1: FoCharacterRuleset.updatePerception(fc, -1);
            case 2: FoCharacterRuleset.updateEndurance(fc, -1);
            case 3: FoCharacterRuleset.updateCharisma(fc, -1);
            case 4: FoCharacterRuleset.updateIntellect(fc, -1);
            case 5: FoCharacterRuleset.updateAgility(fc, -1);
            case 6: FoCharacterRuleset.updateLuck(fc, -1);
        }
    }

    private static void addMutateGainStrengthAnswer(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode gainStrengthAnswer = new DialogAnswerNode("Gain Strength.", mutateQuestion, foCharacter);
        addGainSpecialsDemandsAndResults(mutateSpecialQuestion, gainStrengthAnswer, 0);
        gainStrengthAnswer.addResult(new DialogResultNode(fc -> {
            updateLooseSpecial(fc);
            FoCharacterRuleset.updateStrength(fc, 1);
        }));
    }

    private static void addMutateLooseStrengthAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode("Loose Strength.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, answer, 0);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_STRENGTH))
                || fc.getStrength() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.GAIN_STRENGTH));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.ADRENALINE_RUSH))
                || fc.getStrength() > 5,
                "Error: Perk limiting mutation - " + PerkFactory.ADRENALINE_RUSH));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.STONEWALL))
                || fc.getStrength() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.STONEWALL));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.IRON_LIMBS))
                || fc.getStrength() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.IRON_LIMBS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.BRUISER)
                || fc.getStrength() > 5,
                "Error: Trait limiting mutation - " + TraitFactory.BRUISER));
    }

    private static void addMutateLoosePerceptionAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode("Loose Perception.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, answer, 1);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_PERCEPTION))
                || fc.getPerception() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.GAIN_PERCEPTION));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.CAUTIOUS_NATURE))
                || fc.getPerception() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.CAUTIOUS_NATURE));
    }

    private static void addMutateLooseEnduranceAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode("Loose Endurance.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, answer, 2);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_ENDURANCE))
                || fc.getEndurance() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.GAIN_ENDURANCE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.TOUGHNESS))
                || fc.getEndurance() > 4,
                "Error: Perk limiting mutation - " + PerkFactory.TOUGHNESS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.EVEN_TOUGHER))
                || fc.getEndurance() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.EVEN_TOUGHER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.IRON_LIMBS))
                || fc.getEndurance() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.IRON_LIMBS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.SNAKE_EATER))
                || fc.getEndurance() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.SNAKE_EATER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.STRONG_BACK))
                || fc.getEndurance() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.STRONG_BACK));
    }

    private static void addMutateLooseCharismaAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode("Loose Charisma.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, answer, 3);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_CHARISMA))
                || fc.getCharisma() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.GAIN_CHARISMA));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.HARMLESS))
                || fc.getCharisma() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.HARMLESS));
    }

    private static void addMutateLooseIntelligenceAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode("Loose Intellect.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, answer, 4);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_INTELLIGENCE))
                || fc.getIntellect() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.GAIN_INTELLIGENCE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.SHARPSHOOTER))
                || fc.getIntellect() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.SHARPSHOOTER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.SWIFT_LEARNER))
                || fc.getIntellect() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.SWIFT_LEARNER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.RAD_RESISTANCE))
                || fc.getIntellect() > 7,
                "Error: Perk limiting mutation - " + PerkFactory.RAD_RESISTANCE));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasSupportPerk(PerkFactory.getSupportPerk(PerkFactory.EDUCATED))
                || fc.getIntellect() > 8,
                "Error: Perk limiting mutation - " + PerkFactory.EDUCATED));
    }

    private static void addMutateLooseAgilityAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode("Loose Agility.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, answer, 5);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_AGILITY))
                || fc.getAgility() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.GAIN_AGILITY));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.QUICK_POCKETS))
                || fc.getAgility() > 5,
                "Error: Perk limiting mutation - " + PerkFactory.QUICK_POCKETS));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.QUICK_RECOVERY))
                || fc.getAgility() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.QUICK_RECOVERY));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.ACTIONBOY))
                || fc.getAgility() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.ACTIONBOY));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.ACTIONBOY2))
                || fc.getAgility() > 6,
                "Error: Perk limiting mutation - " + PerkFactory.ACTIONBOY2));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.DODGER))
                || fc.getAgility() > 8,
                "Error: Perk limiting mutation - " + PerkFactory.DODGER));
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.DODGER2))
                || fc.getAgility() > 10,
                "Error: Perk limiting mutation - " + PerkFactory.DODGER2));
    }

    private static void addMutateLooseLuckAnswer(FoCharacter foCharacter, DialogQuestionNode mutateSpecialQuestion) {
        DialogAnswerNode answer = new DialogAnswerNode("Loose Luck.", mutateSpecialQuestion, foCharacter);
        addLooseSpecialsDemandsAndResults(mutateSpecialQuestion, answer, 6);
        answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.getCombatPerk(PerkFactory.GAIN_LUCK))
                || fc.getLuck() > 3,
                "Error: Perk limiting mutation - " + PerkFactory.GAIN_LUCK));
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

    private static void mutateTraits(FoCharacter foCharacter, DialogQuestionNode mutateQuestion) {
        DialogQuestionNode mutateTraitsQuestion = new DialogQuestionNode(3, "Your traits are mutating... \n[Select trait to free up]");

        refreshMutateTraits(foCharacter, mutateQuestion, mutateTraitsQuestion);

        DialogAnswerNode mutateTraitsAnswer = new DialogAnswerNode("Mutate traits.", mutateTraitsQuestion, foCharacter);
        mutateQuestion.addAnswer(mutateTraitsAnswer);
    }

    private static void refreshMutateTraits(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateTraitsQuestion) {
        mutateTraitsQuestion.clear();

        addMutateLooseTraits(foCharacter, mutateTraitsQuestion);
        addMutateGainTraits(foCharacter, mutateTraitsQuestion);

        mutateTraitsQuestion.getAnswers().removeIf(a -> !a.areDemandsMet());

        DialogAnswerNode backToMutateQuestion = new DialogAnswerNode("[Back]", mutateQuestion, foCharacter);
        mutateTraitsQuestion.addAnswer(backToMutateQuestion);

        mutateTraitsQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateTraits(foCharacter, mutateQuestion, mutateTraitsQuestion)));
    }

    private static void addMutateGainTraits(FoCharacter foCharacter, DialogQuestionNode mutateTraitsQuestion) {
        DialogAnswerNode fastMetabolismAnswer = new DialogAnswerNode("Gain Fast Metabolism.", mutateTraitsQuestion, foCharacter);
        fastMetabolismAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.FAST_METABOLISM), "Error: You already have the perk - Fast Metabolism"));
        fastMetabolismAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Fast Metabolism"));
        fastMetabolismAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(0)));
        mutateTraitsQuestion.addAnswer(fastMetabolismAnswer);

        DialogAnswerNode bruiserAnswer = new DialogAnswerNode("Gain Bruiser.", mutateTraitsQuestion, foCharacter);
        bruiserAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.BRUISER), "Error: You already have the perk - Bruiser"));
        bruiserAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Bruiser"));
        bruiserAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(1)));
        mutateTraitsQuestion.addAnswer(bruiserAnswer);

        DialogAnswerNode oneHanderAnswer = new DialogAnswerNode("Gain One Hander.", mutateTraitsQuestion, foCharacter);
        oneHanderAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.ONE_HANDER), "Error: You already have the perk - One Hander"));
        oneHanderAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - One Hander"));
        oneHanderAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(2)));
        mutateTraitsQuestion.addAnswer(oneHanderAnswer);

        DialogAnswerNode finesseAnswer = new DialogAnswerNode("Gain Finesse.", mutateTraitsQuestion, foCharacter);
        finesseAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.FINESSE), "Error: You already have the perk - Finesse"));
        finesseAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Finesse"));
        finesseAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(3)));
        mutateTraitsQuestion.addAnswer(finesseAnswer);

        DialogAnswerNode kamikazeAnswer = new DialogAnswerNode("Gain Kamikaze.", mutateTraitsQuestion, foCharacter);
        kamikazeAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.KAMIKAZE), "Error: You already have the perk - Kamikaze"));
        kamikazeAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Kamikaze"));
        kamikazeAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(4)));
        mutateTraitsQuestion.addAnswer(kamikazeAnswer);

        DialogAnswerNode heavyHandedAnswer = new DialogAnswerNode("Gain Heavy Handed.", mutateTraitsQuestion, foCharacter);
        heavyHandedAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.HEAVY_HANDED), "Error: You already have the perk - Heavy Handed"));
        heavyHandedAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Heavy Handed"));
        heavyHandedAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(5)));
        mutateTraitsQuestion.addAnswer(heavyHandedAnswer);

        DialogAnswerNode fastShotAnswer = new DialogAnswerNode("Gain Fast Shot.", mutateTraitsQuestion, foCharacter);
        fastShotAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.FAST_SHOT), "Error: You already have the perk - Fast Shot"));
        fastShotAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Fast Shot"));
        fastShotAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(6)));
        mutateTraitsQuestion.addAnswer(fastShotAnswer);

        DialogAnswerNode bloodyMessAnswer = new DialogAnswerNode("Gain Bloody Mess.", mutateTraitsQuestion, foCharacter);
        bloodyMessAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.BLOODY_MESS), "Error: You already have the perk - Bloody Mess"));
        bloodyMessAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Bloody Mess"));
        bloodyMessAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(8)));
        mutateTraitsQuestion.addAnswer(bloodyMessAnswer);

        DialogAnswerNode jinxedAnswer = new DialogAnswerNode("Gain Jinxed.", mutateTraitsQuestion, foCharacter);
        jinxedAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.JINXED), "Error: You already have the perk - Jinxed"));
        jinxedAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Jinxed"));
        jinxedAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(9)));
        mutateTraitsQuestion.addAnswer(jinxedAnswer);

        DialogAnswerNode goodNaturedAnswer = new DialogAnswerNode("Gain Good Natured.", mutateTraitsQuestion, foCharacter);
        goodNaturedAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.GOOD_NATURED), "Error: You already have the perk - Good Natured"));
        goodNaturedAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Good Natured"));
        goodNaturedAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(10)));
        mutateTraitsQuestion.addAnswer(goodNaturedAnswer);

        DialogAnswerNode chemReliantAnswer = new DialogAnswerNode("Gain Chem Reliant.", mutateTraitsQuestion, foCharacter);
        chemReliantAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.CHEM_RELIANT), "Error: You already have the perk - Chem Reliant"));
        chemReliantAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Chem Reliant"));
        chemReliantAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(11)));
        mutateTraitsQuestion.addAnswer(chemReliantAnswer);

        DialogAnswerNode boneheadAnswer = new DialogAnswerNode("Gain Bonehead.", mutateTraitsQuestion, foCharacter);
        boneheadAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.BONEHEAD), "Error: You already have the perk - Bonehead"));
        boneheadAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Bonehead"));
        boneheadAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(12)));
        mutateTraitsQuestion.addAnswer(boneheadAnswer);

        DialogAnswerNode skilledAnswer = new DialogAnswerNode("Gain Skilled.", mutateTraitsQuestion, foCharacter);
        skilledAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.SKILLED), "Error: You already have the perk - Skilled"));
        skilledAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Skilled"));
        skilledAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(13)));
        mutateTraitsQuestion.addAnswer(skilledAnswer);

        DialogAnswerNode lonerAnswer = new DialogAnswerNode("Gain Loner.", mutateTraitsQuestion, foCharacter);
        lonerAnswer.addDemand(new DialogDemandNode(fc -> !fc.hasTrait(TraitFactory.LONER), "Error: You already have the perk - Loner"));
        lonerAnswer.addDemand(new DialogDemandNode(FoCharacter::canTagTrait, "Error: You do not any trait points left for - Loner"));
        lonerAnswer.addResult(new DialogResultNode(fc -> fc.tagTrait(14)));
        mutateTraitsQuestion.addAnswer(lonerAnswer);
    }

    private static void addMutateLooseTraits(FoCharacter foCharacter, DialogQuestionNode mutateTraitsQuestion) {
        DialogAnswerNode fastMetabolismAnswer = new DialogAnswerNode("Loose Fast Metabolism.", mutateTraitsQuestion, foCharacter);
        fastMetabolismAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.FAST_METABOLISM), "Error: You do not have the perk - Fast Metabolism"));
        fastMetabolismAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(0)));
        mutateTraitsQuestion.addAnswer(fastMetabolismAnswer);

        DialogAnswerNode bruiserAnswer = new DialogAnswerNode("Loose Bruiser.", mutateTraitsQuestion, foCharacter);
        bruiserAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.BRUISER), "Error: You do not have the perk - Bruiser"));
        bruiserAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(1)));
        mutateTraitsQuestion.addAnswer(bruiserAnswer);

        DialogAnswerNode oneHanderAnswer = new DialogAnswerNode("Loose One Hander.", mutateTraitsQuestion, foCharacter);
        oneHanderAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.ONE_HANDER), "Error: You do not have the perk - One Hander"));
        oneHanderAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(2)));
        mutateTraitsQuestion.addAnswer(oneHanderAnswer);

        DialogAnswerNode finesseAnswer = new DialogAnswerNode("Loose Finesse.", mutateTraitsQuestion, foCharacter);
        finesseAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.FINESSE), "Error: You do not have the perk - Finesse"));
        finesseAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(3)));
        mutateTraitsQuestion.addAnswer(finesseAnswer);

        DialogAnswerNode kamikazeAnswer = new DialogAnswerNode("Loose Kamikaze.", mutateTraitsQuestion, foCharacter);
        kamikazeAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.KAMIKAZE), "Error: You do not have the perk - Kamikaze"));
        kamikazeAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(4)));
        mutateTraitsQuestion.addAnswer(kamikazeAnswer);

        DialogAnswerNode heavyHandedAnswer = new DialogAnswerNode("Loose Heavy Handed.", mutateTraitsQuestion, foCharacter);
        heavyHandedAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.HEAVY_HANDED), "Error: You do not have the perk - Heavy Handed"));
        heavyHandedAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(5)));
        mutateTraitsQuestion.addAnswer(heavyHandedAnswer);

        DialogAnswerNode fastShotAnswer = new DialogAnswerNode("Loose Fast Shot.", mutateTraitsQuestion, foCharacter);
        fastShotAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.FAST_SHOT), "Error: You do not have the perk - Fast Shot"));
        fastShotAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(6)));
        mutateTraitsQuestion.addAnswer(fastShotAnswer);

        DialogAnswerNode bloodyMessAnswer = new DialogAnswerNode("Loose Bloody Mess.", mutateTraitsQuestion, foCharacter);
        bloodyMessAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.BLOODY_MESS), "Error: You do not have the perk - Bloody Mess"));
        bloodyMessAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(8)));
        mutateTraitsQuestion.addAnswer(bloodyMessAnswer);

        DialogAnswerNode jinxedAnswer = new DialogAnswerNode("Loose Jinxed.", mutateTraitsQuestion, foCharacter);
        jinxedAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.JINXED), "Error: You do not have the perk - Jinxed"));
        jinxedAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(9)));
        mutateTraitsQuestion.addAnswer(jinxedAnswer);

        DialogAnswerNode goodNaturedAnswer = new DialogAnswerNode("Loose Good Natured.", mutateTraitsQuestion, foCharacter);
        goodNaturedAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.GOOD_NATURED), "Error: You do not have the perk - Good Natured"));
        goodNaturedAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(10)));
        mutateTraitsQuestion.addAnswer(goodNaturedAnswer);

        DialogAnswerNode chemReliantAnswer = new DialogAnswerNode("Loose Chem Reliant.", mutateTraitsQuestion, foCharacter);
        chemReliantAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.CHEM_RELIANT), "Error: You do not have the perk - Chem Reliant"));
        chemReliantAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(11)));
        mutateTraitsQuestion.addAnswer(chemReliantAnswer);

        DialogAnswerNode boneheadAnswer = new DialogAnswerNode("Loose Bonehead.", mutateTraitsQuestion, foCharacter);
        boneheadAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.BONEHEAD), "Error: You do not have the perk - Bonehead"));
        boneheadAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(12)));
        mutateTraitsQuestion.addAnswer(boneheadAnswer);

        DialogAnswerNode skilledAnswer = new DialogAnswerNode("Loose Skilled.", mutateTraitsQuestion, foCharacter);
        skilledAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.SKILLED), "Error: You do not have the perk - Skilled"));
        skilledAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(13)));
        mutateTraitsQuestion.addAnswer(skilledAnswer);

        DialogAnswerNode lonerAnswer = new DialogAnswerNode("Loose Loner.", mutateTraitsQuestion, foCharacter);
        lonerAnswer.addDemand(new DialogDemandNode(fc -> fc.hasTrait(TraitFactory.LONER), "Error: You do not have the perk - Loner"));
        lonerAnswer.addResult(new DialogResultNode(fc -> fc.untagTrait(14)));
        mutateTraitsQuestion.addAnswer(lonerAnswer);
    }

    private static void mutateCombatPerks(FoCharacter foCharacter, DialogQuestionNode mutateQuestion) {
        DialogQuestionNode mutateCombatPerksQuestion = new DialogQuestionNode(4, "Your Combat Perks are mutating... \n[Select Combat Perk to forget]");

        refreshMutateCombatPerks(foCharacter, mutateQuestion, mutateCombatPerksQuestion);

        DialogAnswerNode mutateSupportPerksAnswer = new DialogAnswerNode("Mutate Combat Perks.", mutateCombatPerksQuestion, foCharacter);
        mutateSupportPerksAnswer.addResult(new DialogResultNode(fc -> DialogFactory.refreshMutateCombatPerks(foCharacter, mutateQuestion, mutateCombatPerksQuestion)));
        mutateQuestion.addAnswer(mutateSupportPerksAnswer);
    }

    private static void refreshMutateCombatPerks(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateCombatPerksQuestion) {
        mutateCombatPerksQuestion.clear();

        addMutateDropCombatPerks(foCharacter, mutateCombatPerksQuestion);

        mutateCombatPerksQuestion.getAnswers().removeIf(a -> !a.areDemandsMet());

        DialogAnswerNode backToMutateQuestion = new DialogAnswerNode("[Back]", mutateQuestion, foCharacter);
        mutateCombatPerksQuestion.addAnswer(backToMutateQuestion);

        mutateCombatPerksQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateCombatPerks(foCharacter, mutateQuestion, mutateCombatPerksQuestion)));
    }

    private static void addMutateDropCombatPerks(FoCharacter foCharacter, DialogQuestionNode mutateCombatPerksQuestion) {
        PerkFactory.getCombatPerks().forEach(cp -> {
            DialogAnswerNode answer = new DialogAnswerNode("Forget " + cp.getName(), mutateCombatPerksQuestion, foCharacter);
            answer.addDemand(new DialogDemandNode(fc -> fc.hasCombatPerk(cp), "Error: Does not have Combat Perk - " + cp.getName()));
            answer.addResult(new DialogResultNode(fc -> fc.removeCombatPerk(cp)));
            answer.addResult(new DialogResultNode(fc -> System.out.println("Removed combat perk: " + cp.getName())));
            mutateCombatPerksQuestion.addAnswer(answer);

            //  add case specific specific dependencies
            if (cp.getName().equals(PerkFactory.DODGER)) {
                answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.DODGER2),
                        "Error: Blocking perk - " + PerkFactory.DODGER2));
            }
            if (cp.getName().equals(PerkFactory.LIFEGIVER2)) {
                answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.LIFEGIVER3),
                        "Error: Blocking perk - " + PerkFactory.LIFEGIVER3));
            }
            if (cp.getName().equals(PerkFactory.LIFEGIVER)) {
                answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.LIFEGIVER2),
                        "Error: Blocking perk - " + PerkFactory.LIFEGIVER2));
                answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.LIFEGIVER3),
                        "Error: Blocking perk - " + PerkFactory.LIFEGIVER3));
            }
            if (cp.getName().equals(PerkFactory.ACTIONBOY)) {
                answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.ACTIONBOY2),
                        "Error: Blocking perk - " + PerkFactory.ACTIONBOY2));
            }
            if (cp.getName().equals(PerkFactory.MEDIC)) {
                answer.addDemand(new DialogDemandNode(fc -> !fc.hasCombatPerk(PerkFactory.FIELD_MEDIC),
                        "Error: Blocking perk - " + PerkFactory.FIELD_MEDIC));
            }
        });

    }

    private static void mutateSupportPerks(FoCharacter foCharacter, DialogQuestionNode mutateQuestion) {
        DialogQuestionNode mutateSupportPerksQuestion = new DialogQuestionNode(5, "Your Support Perks are mutating... \n[Select Support Perk to forget]");

        refreshMutateSupportPerks(foCharacter, mutateQuestion, mutateSupportPerksQuestion);

        DialogAnswerNode mutateSupportPerksAnswer = new DialogAnswerNode("Mutate Support Perks.", mutateSupportPerksQuestion, foCharacter);
        mutateSupportPerksAnswer.addResult(new DialogResultNode(fc -> DialogFactory.refreshMutateSupportPerks(foCharacter, mutateQuestion, mutateSupportPerksQuestion)));
        mutateQuestion.addAnswer(mutateSupportPerksAnswer);
    }

    private static void refreshMutateSupportPerks(FoCharacter foCharacter, DialogQuestionNode mutateQuestion, DialogQuestionNode mutateSupportPerksQuestion) {
        mutateSupportPerksQuestion.clear();

        addMutateDropSupportPerks(foCharacter, mutateSupportPerksQuestion);

        mutateSupportPerksQuestion.getAnswers().removeIf(a -> !a.areDemandsMet());

        DialogAnswerNode backToMutateQuestion = new DialogAnswerNode("[Back]", mutateQuestion, foCharacter);
        mutateSupportPerksQuestion.addAnswer(backToMutateQuestion);

        mutateSupportPerksQuestion.addResultToAllAnswers(new DialogResultNode(fc -> DialogFactory.refreshMutateSupportPerks(foCharacter, mutateQuestion, mutateSupportPerksQuestion)));
    }

    private static void addMutateDropSupportPerks(FoCharacter foCharacter, DialogQuestionNode mutateSupportPerksQuestion) {
        PerkFactory.getSupportPerks().forEach(sp -> {
            DialogAnswerNode answer = new DialogAnswerNode("Forget " + sp.getName(), mutateSupportPerksQuestion, foCharacter);
            answer.addDemand(new DialogDemandNode(fc -> fc.hasSupportPerk(sp), "Error: Does not have Support Perk - " + sp.getName()));
            answer.addResult(new DialogResultNode(fc -> fc.removeSupportPerk(sp)));
            answer.addResult(new DialogResultNode(fc -> System.out.println("Removed support perk: " + sp.getName())));
            mutateSupportPerksQuestion.addAnswer(answer);
        });
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
