package net.fodev.foclassic.model.fochar;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PerkFactory {
    private static final PerkFactory instance = new PerkFactory();

    //  combat perks (or level perks)
    //  level 3
    public static final String MORE_CRITICAL = "More Critical";
    public static final String QUICK_POCKETS = "Quick Pockets";
    public static final String ADRENALINE_RUSH = "Adrenaline Rush";
    public static final String QUICK_RECOVERY = "Quick Recovery";
    public static final String WEAPON_HANDLING = "Weapon Handling";
    //  level 6
    public static final String IN_YOUR_FACE = "In Your Face";
    public static final String EVEN_MORE_CRITICALS = "Even More Criticals";
    public static final String SILENT_RUNNING = "Silent Running";
    public static final String TOUGHNESS = "Toughness";
    //  level 9
    public static final String SHARPSHOOTER = "Sharpshooter";
    public static final String PYROMANIAC = "Pyromaniac";
    public static final String CLOSE_COMBAT_MASTER = "Close Combat Master";
    public static final String BONUS_RANGED_DAMAGE = "Bonus Ranged Damage";
    public static final String EVEN_TOUGHER = "Even Tougher";
    public static final String STONEWALL = "Stonewall";
    public static final String MEDIC = "Medic";
    public static final String HEAVE_HO = "Heave Ho!";
    //  level 12
    public static final String GAIN_AGILITY = "Gain Agility";
    public static final String GAIN_CHARISMA = "Gain Charisma";
    public static final String GAIN_ENDURANCE = "Gain Endurance";
    public static final String GAIN_INTELLIGENCE = "Gain Intelligence";
    public static final String GAIN_LUCK = "Gain Luck";
    public static final String GAIN_PERCEPTION = "Gain Perception";
    public static final String GAIN_STRENGTH = "Gain Strength";
    public static final String BETTER_CRITICALS = "Better Criticals";
    public static final String GHOST = "Ghost";
    public static final String LIFEGIVER = "Lifegiver";
    public static final String ACTIONBOY = "Actionboy";
    //  level 15
    public static final String ACTIONBOY2 = "Actionboy (2)";
    public static final String LIFEGIVER2 = "Lifegiver (2)";
    public static final String LIVEWIRE = "Livewire";
    public static final String MAN_OF_STEEL = "Man of Steel";
    public static final String FIELD_MEDIC = "Field Medic";
    public static final String MORE_RANGED_DAMAGE = "More Ranged Damage";
    public static final String SILENT_DEATH = "Silent Death";
    public static final String IRON_LIMBS = "Iron Limbs";
    public static final String DODGER = "Dodger";
    //  level 18
    public static final String DODGER2 = "Dodger (2)";
    public static final String LIFEGIVER3 = "Lifegiver (3)";
    public static final String BONUS_RATE_OF_ATTACK = "Bonus Rate of Attack";

    //  support perks
    public static final String NA = "N/A";
    public static final String AWARENESS = "Awareness";
    public static final String BONEYARD_GUARD = "Boneyard Guard";
    public static final String CAUTIOUS_NATURE = "Cautious Nature";
    public static final String DEAD_MAN_WALKING = "Dead Man Walking";
    public static final String DEMOLITION_EXPERT = "Demolition Expert";
    public static final String DISMANTLER = "Dismantler";
    public static final String EDUCATED = "Educated";
    public static final String EXPLORER = "Explorer";
    public static final String FASTER_HEALING = "Faster Healing";
    public static final String FORTUNE_FINDER = "Fortune Finder";
    public static final String GECKO_SKINNING = "Gecko Skinning";
    public static final String HARMLESS = "Harmless";
    public static final String KAMA_SUTRA_MASTER = "Kama Sutra Master";
    public static final String LIGHT_STEP = "Light Step";
    public static final String MAGNETIC_PERSONALITY = "Magnetic Personality";
    public static final String MASTER_THIEF = "Master Thief";
    public static final String MR_FIXIT = "Mr. Fixit";
    public static final String NEGOTIATOR = "Negotiator";
    public static final String PACK_RAT = "Pack Rat";
    public static final String PATHFINDER = "Pathfinder";
    public static final String PICKPOCKET = "Pickpocket";
    public static final String RAD_RESISTANCE = "Rad Resistance";
    public static final String RANGER = "Ranger";
    public static final String SCOUT = "Scout";
    public static final String SCROUNGER = "Scrounger";
    public static final String SEX_APPEAL = "Sex Appeal";
    public static final String SNAKE_EATER = "Snake Eater";
    public static final String SPEAKER = "Speaker";
    public static final String STEALTH_GIRL = "Stealth Girl";
    public static final String STRONG_BACK = "Strong Back";
    public static final String SWIFT_LEARNER = "Swift Learner";
    public static final String THIEF = "Thief";
    public static final String TREASURE_HUNTER = "Treasure Hunter";
    public static final String WAY_OF_THE_FRUIT = "Way of the Fruit";

    public static PerkFactory getInstance() {
        return instance;
    }

    @Getter private static List<Perk> perks;
    @Getter private static List<SupportPerk> supportPerks;
    @Getter private static List<CombatPerk> combatPerks;

    private PerkFactory() {
        System.out.println("PerkFactory::Constructor()");
        perks = new ArrayList<>();
        supportPerks = new ArrayList<>();
        combatPerks = new ArrayList<>();

        //  level 3
        combatPerks.add(new CombatPerk(MORE_CRITICAL, 3, "images/skilldex/MORECRIT.png", "With this perk, you are 5% more likely to cause critical hits in combat than without it."));
        combatPerks.add(new CombatPerk(QUICK_POCKETS, 3, "images/skilldex/QUIKPOCK.png", "You have learned to pack your equipment better. (Un)equipping/picking up items is done at half its normal AP cost. Reloading cost reduced to 1 AP."));
        combatPerks.add(new CombatPerk(ADRENALINE_RUSH, 3, "images/skilldex/ADRNRUSH.png", "Adrenaline Rush increases one's damage thresholds and damage resistances depending on what percentage of his/her total hit points the player has temporarily lost."));
        combatPerks.add(new CombatPerk(QUICK_RECOVERY, 3, "images/skilldex/QWKRECOV.png", "You are quick at recovering from being knocked down"));
        combatPerks.add(new CombatPerk(WEAPON_HANDLING, 3, "images/skilldex/WEPNHAND.png", "You can wield weapons much larger than normally allowed. You gain a +2 bonus to your Strength for the purposes of Strength checks when trying to wield weaponry."));

        //  level 6
        combatPerks.add(new CombatPerk(IN_YOUR_FACE, 6, "images/skilldex/p_inyfac.png", "You cut an intimidating figure, and people cower when you are near. Opponents have a 50% chance of missing you when in an adjacent square."));
        combatPerks.add(new CombatPerk(EVEN_MORE_CRITICALS, 6, "images/skilldex/p_evenmc.png", "Taking this perk increases your critical hit chance by 10%."));
        combatPerks.add(new CombatPerk(SILENT_RUNNING, 6, "images/skilldex/SILNTRUN.png", "With this perk, you now have the ability to move quickly and still remain quiet. You can sneak and run at the same time. Without this perk, you would automatically stop sneaking if you ran."));
        combatPerks.add(new CombatPerk(TOUGHNESS, 6, "images/skilldex/TOUGHNES.png", "When you are tough, you take less damage. This perk adds 5% to all Damage Resistances and 1 to all your Damage Threshold."));

        //  level 9
        combatPerks.add(new CombatPerk(SHARPSHOOTER, 9, "images/skilldex/SHARPSHT.png", "You have a talent for hitting things at longer distances. This perk expands your field of vision by 6 hexes, and increases your chance to hit by 8% and -50% to your target's armor's (both head and body) critical chance modifiers for aimed attacks only."));
        combatPerks.add(new CombatPerk(PYROMANIAC, 9, "images/skilldex/PYROMNAC.png", "You do 25% more fire-based damage."));
        combatPerks.add(new CombatPerk(CLOSE_COMBAT_MASTER, 9, "images/skilldex/DAMAGE.png", "+15 critical chance for melee/unarmed attacks and +10 melee damage"));
        combatPerks.add(new CombatPerk(BONUS_RANGED_DAMAGE, 9, "images/skilldex/BONUSRNG.png", "Your training in firearms and other ranged weapons has made you more deadly in ranged combat. For each level of this perk, you do +3 damage with ranged weapons."));
        combatPerks.add(new CombatPerk(EVEN_TOUGHER, 9, "images/skilldex/p_evtoug.png", "This Perk increases your normal Damage Threshold by 3, and normal Damage Resistances by 10%."));
        combatPerks.add(new CombatPerk(STONEWALL, 9, "images/skilldex/STONWALL.png", "You are much less likely to be knocked down in combat."));
        combatPerks.add(new CombatPerk(MEDIC, 9, "images/skilldex/MEDIC.png", "The Medic perk halves the cooldowns for using the First Aid and Doctor skills, and the ability to remove knockout effect with Doctor skill."));
        combatPerks.add(new CombatPerk(HEAVE_HO, 9, "images/skilldex/HEAVEHO.png", "Heave Ho! gives you a +2 bonus to Strength (up to 10) for purposes of determining Range with thrown weapons only. This perk will increase a weapon's maximum Range by 6."));

        //  level 12
        combatPerks.add(new CombatPerk(GAIN_AGILITY, 12, "images/skilldex/p_gainag.png", "With this perk you gain +2 to your Agility."));
        combatPerks.add(new CombatPerk(GAIN_CHARISMA, 12, "images/skilldex/p_gainch.png", "With this perk you gain +2 to your Charisma."));
        combatPerks.add(new CombatPerk(GAIN_ENDURANCE, 12, "images/skilldex/p_gainen.png", "With this perk you gain +2 to your Endurance."));
        combatPerks.add(new CombatPerk(GAIN_INTELLIGENCE, 12, "images/skilldex/p_gainin.png", "With this perk you gain +2 to your Intelligence."));
        combatPerks.add(new CombatPerk(GAIN_LUCK, 12, "images/skilldex/p_gainlk.png", "With this perk you gain +2 to your Luck."));
        combatPerks.add(new CombatPerk(GAIN_PERCEPTION, 12, "images/skilldex/p_gainpe.png", "With this perk you gain +2 to your Perception."));
        combatPerks.add(new CombatPerk(GAIN_STRENGTH, 12, "images/skilldex/p_gainst.png", "With this perk you gain +2 to your Strength."));
        combatPerks.add(new CombatPerk(BETTER_CRITICALS, 12, "images/skilldex/BETRCRIT.png", "The critical hits you cause in combat are more devastating."));
        combatPerks.add(new CombatPerk(GHOST, 12, "images/skilldex/GHOST.png", "The Ghost perk gives a +30 bonus to Sneak whenever the player who took it finds himself within 5 hexes from a wall."));
        combatPerks.add(new CombatPerk(LIFEGIVER, 12, "images/skilldex/LIFEGIVR.png", "With each respective level of this perk, you gain an additional 30 Hit Points."));
        combatPerks.add(new CombatPerk(ACTIONBOY, 12, "images/skilldex/ACTION.png", "Each level of Action Boy gives you an additional action point to spend every combat turn. You can use these generic APs on any task."));

        //  level 15
        combatPerks.add(new CombatPerk(ACTIONBOY2, 15, "images/skilldex/ACTION.png", "Each level of Action Boy gives you an additional action point to spend every combat turn. You can use these generic APs on any task."));
        combatPerks.add(new CombatPerk(LIFEGIVER2, 15, "images/skilldex/LIFEGIVR.png", "With each respective level of this perk, you gain an additional 30 Hit Points."));
        combatPerks.add(new CombatPerk(LIVEWIRE, 15, "images/skilldex/p_lvewre.png", "You have learned to harness your extra energy and in battle you are a blur. With this Perk, your AC derived from Agility gets doubled."));
        combatPerks.add(new CombatPerk(MAN_OF_STEEL, 15, "images/skilldex/p_manste.png", "You are as tough as they come. Critical hits just don’t seem to affect you as much as they do others."));
        combatPerks.add(new CombatPerk(FIELD_MEDIC, 15, "images/skilldex/MEDIC.png", "You get +20 to +40 HP healed when using First Aid skill, 5% chance to score critical success when using First Aid, when you use First Aid/Doctor on other players, your cooldown is applied to them."));
        combatPerks.add(new CombatPerk(MORE_RANGED_DAMAGE, 15, "images/skilldex/p_mrrndm.png", "Your advanced training in firearms and other ranged weapons has made you even more deadly in ranged combat. You do +4 damage with ranged weapons."));
        combatPerks.add(new CombatPerk(SILENT_DEATH, 15, "images/skilldex/SILENTD.png", "While sneaking, if you attack a critter(whether it's Player or NPC) from behind, you will always cause a critical hit with a +10 bonus on the critical power roll. Silent Death is that kind of perk."));
        combatPerks.add(new CombatPerk(IRON_LIMBS, 15, "images/skilldex/p_treett.png", "Gain a chance to avoid leg and arm cripples or weapon drop."));
        combatPerks.add(new CombatPerk(DODGER, 15, "images/skilldex/DODGER.png", "-5% to final hit chance (works only with melee/unarmed/throwing weapons in both hands)"));

        //  level 18
        combatPerks.add(new CombatPerk(DODGER2, 18, "images/skilldex/DODGER.png", "-5% to final hit chance (works only with melee/unarmed/throwing weapons in both hands)"));
        combatPerks.add(new CombatPerk(LIFEGIVER3, 18, "images/skilldex/LIFEGIVR.png", "With each respective level of this perk, you gain an additional 30 Hit Points."));
        combatPerks.add(new CombatPerk(BONUS_RATE_OF_ATTACK, 18, "images/skilldex/BONUSRAT.png", "This Perk allows you to shoot and punch a little faster and still remain as accurate as before. Each attack costs 1 AP less to perform."));

        //  support perks
        supportPerks.add(new SupportPerk(AWARENESS, 0, "images/skilldex/AWARENES.png", "With Awareness, you are given detailed information about any critters you examine. You see their exact Hit Points and information about any Weapon they are equipped with."));
        supportPerks.add(new SupportPerk(BONEYARD_GUARD, 0, "images/skilldex/STRANGER.png", "+10% in any fighting skill of your choice."));
        supportPerks.add(new SupportPerk(CAUTIOUS_NATURE, 0, "images/skilldex/CAUTIOUS.png", "You are more alert outdoors and enemies are less likely to sneak up on you. With this perk you get a +3 bonus to your Perception in random encounters when determining placement."));
        supportPerks.add(new SupportPerk(DEAD_MAN_WALKING, 0, "images/skilldex/deadmanwalking.png", "Like Rasputin, it takes a lot to finally finish you off. Your hit points can drop to twice the normally allowed negative value before you finally die."));
        supportPerks.add(new SupportPerk(DEMOLITION_EXPERT, 0, "images/skilldex/P_DEMO.png", "You are an expert when it comes to the fine art of handling explosives. They always go off when they're supposed to, as well as causing extra damage."));
        supportPerks.add(new SupportPerk(DISMANTLER, 0, "images/skilldex/p_disman.png", "You're the kind of a person who always finds spare screws after dismantling a clock and putting it back together. You recycle twice the usual number of base resources whenever dismantling an item."));
        supportPerks.add(new SupportPerk(EDUCATED, 0,"images/skilldex/EDUCATED.png", "Educated adds +2 Skill Points when you gain a new experience level. This perk works best when acquired early in your adventure."));
        supportPerks.add(new SupportPerk(EXPLORER, 0, "images/skilldex/p_explor.png", "The mark of the Explorer is to search out new and interesting locations."));
        supportPerks.add(new SupportPerk(FASTER_HEALING, 0, "images/skilldex/p_healrt.png", "With this perk, you get a +5 bonus to your Healing Rate."));
        supportPerks.add(new SupportPerk(FORTUNE_FINDER, 0, "images/skilldex/FORTUNFD.png", "(This perk is planned for a future update, and not implemented yet.)"));
        supportPerks.add(new SupportPerk(GECKO_SKINNING, 0, "images/skilldex/GECKOSKN.png", "You have the knowledge of how to skin geckos properly in order to get their pelts."));
        supportPerks.add(new SupportPerk(HARMLESS, 0, "images/skilldex/HARMLESS.png", "Your innocent demeanor makes stealing from people a little easier."));
        supportPerks.add(new SupportPerk(KAMA_SUTRA_MASTER, 0, "images/skilldex/KMASUTRA.png", "When it comes to pleasing sexually, you wrote the book."));
        supportPerks.add(new SupportPerk(LIGHT_STEP, 0, "images/skilldex/LITESTEP.png", "You are agile, lucky, and always careful. This Perk halves your chances of setting off a trap."));
        supportPerks.add(new SupportPerk(MAGNETIC_PERSONALITY, 0, "images/skilldex/MAGPERS.png", "Everyone enjoys your company! Your Party Points increase by 50."));
        supportPerks.add(new SupportPerk(MASTER_THIEF, 0, "images/skilldex/MTRTHIEF.png", "A Master Thief is proficient at stealing. He is able to attempt his thievery twice as often as his less skilled counterparts"));
        supportPerks.add(new SupportPerk(MR_FIXIT, 0, "images/skilldex/MRFIXIT.png", "Mr. Fixit is able to Repair items to a higher standard than his peers."));
        supportPerks.add(new SupportPerk(NEGOTIATOR, 0, "images/skilldex/p_negotr.png", "You are a notorious negotiator. Not only can you Barter with the best of them but you can also talk your way into or out of nearly anything. With this perk even your worst Reputations count as neutral."));
        supportPerks.add(new SupportPerk(PACK_RAT, 6, "images/skilldex/PACKRAT.png", "You are efficient at arranging your inventory in general. This makes it much easier to carry that little extra you've always needed."));
        supportPerks.add(new SupportPerk(PATHFINDER, 0, "images/skilldex/PATHFNDR.png", "The Pathfinder is able to find the shortest route available. With this perk, your travel speed on the World Map increases by 25%."));
        supportPerks.add(new SupportPerk(PICKPOCKET, 0, "images/skilldex/PICKPOCK.png", "Pickpocket makes stealing much easier by reducing both size and facing modifiers by 50%."));
        supportPerks.add(new SupportPerk(RAD_RESISTANCE, 0, "images/skilldex/RADRESIS.png", "You know how to avoid radiation and its bad effects more effectively. This perk adds 30% to your Rad Resistance and 20% to your Poison Resistance."));
        supportPerks.add(new SupportPerk(RANGER, 0, "images/skilldex/RANGER.png", "Over the extensive amount of time you have spent in the wilderness, you have learned to create a number of items and tools necessary for survival. Also, when you make a tent, it is automatically upgraded to a safe house."));
        supportPerks.add(new SupportPerk(SCOUT, 0, "images/skilldex/SCOUT.png", "You have improved your ability to see and recognize distant locations and events, increasing the size of explorations on the World Map by one square in each direction. Additionally, you get information about possible encounters in area."));
        supportPerks.add(new SupportPerk(SCROUNGER, 0, "images/skilldex/STRANGER.png", "(This perk is planned for a future update, and not implemented yet.)"));
        supportPerks.add(new SupportPerk(SEX_APPEAL, 0, "images/skilldex/EMPATHY.png", "Your raw sexual magnetism affects your relationship with the opposite sex in your favor. All reputations in such relationships are better for 250 points."));
        supportPerks.add(new SupportPerk(SNAKE_EATER, 0, "images/skilldex/SNAKEEAT.png", "Yum! Tastes like chicken. This perk gives you a 30% bonus to your Poison Resistance and a 20% bonus to your Rad Resistance."));
        supportPerks.add(new SupportPerk(SPEAKER, 0, "images/skilldex/SPEAKER.png", "Being a speaker means that your Followers’ loyalty decreases at half the normal rate."));
        supportPerks.add(new SupportPerk(STEALTH_GIRL, 0, "images/skilldex/p_steboy.png", "Your intuitive understanding of the inner mechanism of Stealth Boys and Motion Sensor allows you to use them for twice as long."));
        supportPerks.add(new SupportPerk(STRONG_BACK, 6, "images/skilldex/CARRYAMT.png", "AKA Mule. You can carry much more than an ordinary person."));
        supportPerks.add(new SupportPerk(SWIFT_LEARNER, 0, "images/skilldex/SWFTLERN.png", "You are indeed a Swift Learner with this perk, as it gives you a +10% bonus whenever you earn experience points."));
        supportPerks.add(new SupportPerk(THIEF, 0, "images/skilldex/STEAL.png", "The blood of a thief runs through your veins. You are granted a Luck/50 chance of no timeout when unsuccessfully attempting to steal. A well-rounded thief is a live thief."));
        supportPerks.add(new SupportPerk(TREASURE_HUNTER, 0, "images/skilldex/p_treahu.png", "Treasure Hunter is a support perk that enables you to find more items in the lockers that sometimes appear in random encounters than you would have been able to find without it. It gives a bonus 25% more items found from encounter containers."));
        supportPerks.add(new SupportPerk(WAY_OF_THE_FRUIT, 0, "images/skilldex/p_wfruit.png", "Ever since you were a child you loved Fruit. Your body can digest them better; you heal for 16 Hit Points and don't suffer from radiation when eating Fruits."));
    }

    public static SupportPerk getSupportPerk(String name) {
        return supportPerks.stream()
                .filter(t -> name.equals(t.getName()))
                .findFirst()
                .orElse(null);
    }

    public static CombatPerk getCombatPerk(String name) {
        return combatPerks.stream()
                .filter(t -> name.equals(t.getName()))
                .findFirst()
                .orElse(null);
    }
}
