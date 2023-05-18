package net.fodev.foclassic.model;

import java.util.ArrayList;
import java.util.List;

public class TraitFactory {
    public static final String NA = "N/A";
    public static final String FAST_METABOLISM = "Fast Metabolism";
    public static final String BRUISER = "Bruiser";
    public static final String ONE_HANDER = "One Hander";
    public static final String FINESSE = "Finesse";
    public static final String KAMIKAZE = "Kamikaze";
    public static final String HEAVY_HANDED = "Heavy Handed";
    public static final String FAST_SHOT = "Fast Shot";
    public static final String BLOODY_MESS = "Bloody Mess";
    public static final String JINXED = "Jinxed";
    public static final String GOOD_NATURED = "Good Natured";
    public static final String CHEM_RELIANT = "Chem Reliant";
    public static final String BONEHEAD = "Bonehead";
    public static final String SKILLED = "Skilled";
    public static final String LONER = "Loner";

    private static final TraitFactory instance = new TraitFactory();
    private static List<Trait> traits;

    private TraitFactory() {
        System.out.println("TraitFactory::Constructor()");
        traits = new ArrayList<>();
        traits.add(new Trait(NA, false, "images/skilldex/", "N/A"));
        traits.add(new Trait(FAST_METABOLISM, false, "images/skilldex/FASTMETA.png", "Your metabolic rate is twice as normal.  This means that effects of drugs and medicines wear off twice as fast, but your body heals faster (every 30s instead of every 60s"));
        traits.add(new Trait(BRUISER, false, "images/skilldex/BRUISER.png", "A little slower, but a little bigger.  You may not hit as often, but they will feel it when you do!  Your total action points are lowered by 2, but your Strength is increased by 4 and you deal even more damage in melee attacks."));
        traits.add(new Trait(ONE_HANDER, false, "images/skilldex/ONEHAND.png", "One of your hands is very dominant.  You excel with single-handed weapons (+20 chance to hit), but two-handed weapons cause a problem (-40 chance to hit). You do 5 extra damage with single-handed range weapons."));
        traits.add(new Trait(FINESSE, false, "images/skilldex/FINESSE.png", "Your attacks show a lot of finesse.  You don't do as much damage (+30 target's damage resistance), but you cause more critical hits (+10 critical chance)."));
        traits.add(new Trait(KAMIKAZE, false, "images/skilldex/KAMIKAZE.png", "By not paying attention to any threats. You do 10% more damage to enemies but your damage resistance is lowered by 10."));
        traits.add(new Trait(HEAVY_HANDED, false, "images/skilldex/HEAVYHND.png", "You swing harder, not better.  Your attacks are very brutal, but lack finesse.  You rarely cause a good critical (-20 critical power), but you always do more unarmed damage (+5 final damage). Additionally, all your close combat attacks knock down the opponent if a successful Strength roll was made."));
        traits.add(new Trait(FAST_SHOT, false, "images/skilldex/FASTSHOT.png", "You don't have time to aim for a targeted attack, because you attack faster than normal people and your bullets can randomly hit different body parts.  It costs you one less action point for guns and thrown weapons, and then one less with two handed weapons."));
        traits.add(new Trait(BLOODY_MESS, false, "images/skilldex/BLDMESS.png", "By some strange twist of fate, people around you die violently.  You always see the worst way a person can die (100% chance to kill target if your damage gets him on negative HP but you can be killed like that too)."));
        traits.add(new Trait(JINXED, false, "images/skilldex/JINXED.png", "The good thing is that everyone around you has more critical failures in combat, the bad thing is so do you!"));
        traits.add(new Trait(GOOD_NATURED, false, "images/skilldex/GOODNATR.png", "There is a positive aura about you. People are more likely to follow you (+50 party points), but you can't own any slaves."));
        traits.add(new Trait(CHEM_RELIANT, false, "images/skilldex/ADDICT.png", "The drug effects last twice as long, but you regain only 2/3 the normal hit points while using medicines."));
        traits.add(new Trait(BONEHEAD, false, "images/skilldex/bonehead.png", "A thicker than average cranium means you can shrug off hits to the head that would floor others.  Unfortunatly that doesn't leave much room up there."));
        traits.add(new Trait(SKILLED, false, "images/skilldex/SKILLED.png", "Since you spent more time improving your skills than a normal person, you gain 5 additional skill points per experience level. The tradeoff is that you do not gain as many extra abilities. You gain a perk every four levels."));
        traits.add(new Trait(LONER, false, "images/skilldex/t_loner.png", "You appreciate the nature more than company of others. You gain 10% more experience, but you can't have any followers.n"));
    }

    public static TraitFactory getInstance() {
        return instance;
    }

    public static Trait get(int index) {
        if (index >= 0 && index < traits.size()) {
            return traits.get(index);
        } else {
            System.out.println("TraitFactory::get() - index out of bounds.");
            return null;
        }
    }

    public static Trait get(String name) {
        return traits.stream()
                .filter(t -> name.equals(t.getName()))
                .findFirst()
                .orElse(null);
    }
}
