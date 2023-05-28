package net.fodev.foclassic.model.fochar;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PerkFactory {
    private static final PerkFactory instance = new PerkFactory();

    public static final String NA = "N/A";
    public static final String AWARENESS = "Awareness";
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

    private PerkFactory() {
        System.out.println("PerkFactory::Constructor()");
        perks = new ArrayList<>();
        supportPerks = new ArrayList<>();

        supportPerks.add(new SupportPerk(AWARENESS, "images/skilldex/AWARENES.png", "With Awareness, you are given detailed information about any critters you examine. You see their exact Hit Points and information about any Weapon they are equipped with."));
        supportPerks.add(new SupportPerk(CAUTIOUS_NATURE, "images/skilldex/CAUTIOUS.png", "You are more alert outdoors and enemies are less likely to sneak up on you. With this perk you get a +3 bonus to your Perception in random encounters when determining placement."));
        supportPerks.add(new SupportPerk(DEAD_MAN_WALKING, "images/skilldex/deadmanwalking.png", "Like Rasputin, it takes a lot to finally finish you off. Your hit points can drop to twice the normally allowed negative value before you finally die."));
        supportPerks.add(new SupportPerk(DEMOLITION_EXPERT, "images/skilldex/P_DEMO.png", "You are an expert when it comes to the fine art of handling explosives. They always go off when they're supposed to, as well as causing extra damage."));
        supportPerks.add(new SupportPerk(DISMANTLER, "images/skilldex/p_disman.png", "You're the kind of a person who always finds spare screws after dismantling a clock and putting it back together. You recycle twice the usual number of base resources whenever dismantling an item."));
        supportPerks.add(new SupportPerk(EDUCATED, "images/skilldex/EDUCATED.png", "Educated adds +2 Skill Points when you gain a new experience level. This perk works best when acquired early in your adventure."));
        supportPerks.add(new SupportPerk(EXPLORER, "images/skilldex/p_explor.png", "The mark of the Explorer is to search out new and interesting locations."));
        supportPerks.add(new SupportPerk(FASTER_HEALING, "images/skilldex/p_healrt.png", "With this perk, you get a +5 bonus to your Healing Rate."));
        supportPerks.add(new SupportPerk(FORTUNE_FINDER, "images/skilldex/FORTUNFD.png", "(This perk is planned for a future update, and not implemented yet.)"));
        supportPerks.add(new SupportPerk(GECKO_SKINNING, "images/skilldex/GECKOSKN.png", "You have the knowledge of how to skin geckos properly in order to get their pelts."));
        supportPerks.add(new SupportPerk(HARMLESS, "images/skilldex/HARMLESS.png", "Your innocent demeanor makes stealing from people a little easier."));
        supportPerks.add(new SupportPerk(KAMA_SUTRA_MASTER, "images/skilldex/KMASUTRA.png", "When it comes to pleasing sexually, you wrote the book."));
        supportPerks.add(new SupportPerk(LIGHT_STEP, "images/skilldex/LITESTEP.png", "You are agile, lucky, and always careful. This Perk halves your chances of setting off a trap."));
        supportPerks.add(new SupportPerk(MAGNETIC_PERSONALITY, "images/skilldex/MAGPERS.png", "Everyone enjoys your company! Your Party Points increase by 50."));
        supportPerks.add(new SupportPerk(MASTER_THIEF, "images/skilldex/MTRTHIEF.png", "A Master Thief is proficient at stealing. He is able to attempt his thievery twice as often as his less skilled counterparts"));
        supportPerks.add(new SupportPerk(MR_FIXIT, "images/skilldex/MRFIXIT.png", "Mr. Fixit is able to Repair items to a higher standard than his peers."));
        supportPerks.add(new SupportPerk(NEGOTIATOR, "images/skilldex/p_negotr.png", "You are a notorious negotiator. Not only can you Barter with the best of them but you can also talk your way into or out of nearly anything. With this perk even your worst Reputations count as neutral."));
        supportPerks.add(new SupportPerk(PACK_RAT, "images/skilldex/PACKRAT.png", "You are efficient at arranging your inventory in general. This makes it much easier to carry that little extra you've always needed."));
        supportPerks.add(new SupportPerk(PATHFINDER, "images/skilldex/PATHFNDR.png", "The Pathfinder is able to find the shortest route available. With this perk, your travel speed on the World Map increases by 25%."));
        supportPerks.add(new SupportPerk(PICKPOCKET, "images/skilldex/PICKPOCK.png", "Pickpocket makes stealing much easier by reducing both size and facing modifiers by 50%."));
        supportPerks.add(new SupportPerk(RAD_RESISTANCE, "images/skilldex/RADRESIS.png", "You know how to avoid radiation and its bad effects more effectively. This perk adds 30% to your Rad Resistance and 20% to your Poison Resistance."));
        supportPerks.add(new SupportPerk(RANGER, "images/skilldex/RANGER.png", "Over the extensive amount of time you have spent in the wilderness, you have learned to create a number of items and tools necessary for survival. Also, when you make a tent, it is automatically upgraded to a safe house."));
        supportPerks.add(new SupportPerk(SCOUT, "images/skilldex/SCOUT.png", "You have improved your ability to see and recognize distant locations and events, increasing the size of explorations on the World Map by one square in each direction. Additionally, you get information about possible encounters in area."));
        supportPerks.add(new SupportPerk(SCROUNGER, "images/skilldex/STRANGER.png", "(This perk is planned for a future update, and not implemented yet.)"));
        supportPerks.add(new SupportPerk(SEX_APPEAL, "images/skilldex/EMPHATY.png", "Your raw sexual magnetism affects your relationship with the opposite sex in your favor. All reputations in such relationships are better for 250 points."));
        supportPerks.add(new SupportPerk(SNAKE_EATER, "images/skilldex/SNAKEEAT.png", "Yum! Tastes like chicken. This perk gives you a 30% bonus to your Poison Resistance and a 20% bonus to your Rad Resistance."));
        supportPerks.add(new SupportPerk(SPEAKER, "images/skilldex/SPEAKER.png", "Being a speaker means that your Followers’ loyalty decreases at half the normal rate."));
        supportPerks.add(new SupportPerk(STEALTH_GIRL, "images/skilldex/p_steboy.png", "Your intuitive understanding of the inner mechanism of Stealth Boys and Motion Sensor allows you to use them for twice as long."));
        supportPerks.add(new SupportPerk(STRONG_BACK, "images/skilldex/CARRYAMT.png", "AKA Mule. You can carry much more than an ordinary person."));
        supportPerks.add(new SupportPerk(SWIFT_LEARNER, "images/skilldex/SWIFTLERN.png", "You are indeed a Swift Learner with this perk, as it gives you a +10% bonus whenever you earn experience points."));
        supportPerks.add(new SupportPerk(THIEF, "images/skilldex/STEAL.png", "The blood of a thief runs through your veins. You are granted a Luck/50 chance of no timeout when unsuccessfully attempting to steal. A well-rounded thief is a live thief."));
        supportPerks.add(new SupportPerk(TREASURE_HUNTER, "images/skilldex/p_treahu.png", "Treasure Hunter is a support perk that enables you to find more items in the lockers that sometimes appear in random encounters than you would have been able to find without it. It gives a bonus 25% more items found from encounter containers."));
        supportPerks.add(new SupportPerk(WAY_OF_THE_FRUIT, "images/skilldex/p_wfruit.png", "Ever since you were a child you loved Fruit. Your body can digest them better; you heal for 16 Hit Points and don't suffer from radiation when eating Fruits."));
    }

    public static SupportPerk getSupportPerk(String name) {
        return supportPerks.stream()
                .filter(t -> name.equals(t.getName()))
                .findFirst()
                .orElse(null);
    }

}
