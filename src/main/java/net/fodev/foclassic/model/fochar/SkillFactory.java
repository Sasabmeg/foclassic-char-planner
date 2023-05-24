package net.fodev.foclassic.model.fochar;

import java.util.ArrayList;
import java.util.List;

public class SkillFactory {
    public static final String NA = "N/A";
    public static final String SMALL_GUNS = "Small Guns";
    public static final String BIG_GUNS = "Big Guns";
    public static final String ENERGY_WEAPONS = "Energy Weapons";
    public static final String CLOSE_COMBAT = "Close Combat";
    public static final String SCAVENGING = "Scavenging";
    public static final String THROWING = "Throwing";
    public static final String FIRST_AID = "First Aid";
    public static final String DOCTOR = "Doctor";
    public static final String SNEAK = "Sneak";
    public static final String LOCKPICK = "Lockpick";
    public static final String STEAL = "Steal";
    public static final String TRAPS = "Traps";
    public static final String SCIENCE = "Science";
    public static final String REPAIR = "Repair";
    public static final String SPEECH = "Speech";
    public static final String BARTER = "Barter";
    public static final String GAMBLING = "Gambling";
    public static final String OUTDOORSMAN = "Outdoorsman";

    private static final SkillFactory instance = new SkillFactory();
    private static List<SkillProto> skillProtos;


    private SkillFactory() {
        skillProtos = new ArrayList<>();
        skillProtos.add(new SkillProto(NA,"images/skilldex/", "N/A"));
        skillProtos.add(new SkillProto(SMALL_GUNS, "images/skilldex/GUNSML.png", "The use, care and general knowledge of small firearms - pistols, SMGs and rifles."));
        skillProtos.add(new SkillProto(BIG_GUNS, "images/skilldex/GUNBIG.png", "The operation and maintenance of really big guns - miniguns, rocket launchers, flamethrowers and such."));
        skillProtos.add(new SkillProto(ENERGY_WEAPONS, "images/skilldex/ENERGYWP.png", "The care and feeding of energy-based weapons. How to arm and operate weapons that use laser or plasma technology."));
        skillProtos.add(new SkillProto(CLOSE_COMBAT,  "images/skilldex/HND2HND.png", "A combination of martial arts, non-ranged weapons in hand-to-hand, or melee combat - knives, sledgehammers, spears, clubs and so on."));
        skillProtos.add(new SkillProto(SCAVENGING,  "images/skilldex/SCROUNGR.png", "The ability to search through waste, junk, etc., for something that can be saved or used. The more time you spend on searching the better you are at it."));
        skillProtos.add(new SkillProto(THROWING, "images/skilldex/THROWING.png", "The skill of muscle-propelled ranged weapons, such as throwing knives, spears and grenades."));
        skillProtos.add(new SkillProto(FIRST_AID, "images/skilldex/FIRSTAID.png", "General healing skill. Used to heal small cuts, abrasions and other minor ills. The use of first aid can heal some hit points immediately."));
        skillProtos.add(new SkillProto(DOCTOR, "images/skilldex/DOCTOR.png", "The healing of major wounds and crippled limbs.  Without this skill, it will take a much longer period of time to restore crippled limbs to use."));
        skillProtos.add(new SkillProto(SNEAK, "images/skilldex/SNEAK.png", "Quiet movement, and the ability to remain unnoticed. If successful, you will be much harder to locate. You cannot run and sneak at the same time."));
        skillProtos.add(new SkillProto(LOCKPICK, "images/skilldex/LOCKPICK.png", "The skill of opening locks without the proper key. The use of lock picks or electronic lock picks will greatly enhance this skill."));
        skillProtos.add(new SkillProto(STEAL, "images/skilldex/STEAL.png", "The ability to make the things of others your own. Can be used to steal from people or places."));
        skillProtos.add(new SkillProto(TRAPS, "images/skilldex/TRAPS.png", "The finding and removal of traps. Also the setting of explosives for demolition purposes."));
        skillProtos.add(new SkillProto(SCIENCE, "images/skilldex/SCIENCE.png", "Covers a variety of high technology skills, such as computers, biology, physics and geology."));
        skillProtos.add(new SkillProto(REPAIR, "images/skilldex/REPAIR.png", "The practical application of the Science skill for fixing broken equipment, machinery and electronics."));
        skillProtos.add(new SkillProto(SPEECH, "images/skilldex/SPEECH.png", "The ability to communicate in a practical and efficient manner. The skill of convincing others that your position is correct. The ability to lie and not get caught."));
        skillProtos.add(new SkillProto(BARTER, "images/skilldex/BARTER.png", "Trading and trade-related tasks. The ability to get better prices for items you sell, and lower prices for items you buy."));
        skillProtos.add(new SkillProto(GAMBLING,  "images/skilldex/GAMBLING.png", "The knowledge and practical skills related to wagering. The skill at cards, dice and other games."));
        skillProtos.add(new SkillProto(OUTDOORSMAN, "images/skilldex/OUTDOORS.png", "Practical knowledge of the outdoors, and the ability to live off the land. The knowledge of plants and animals."));
    }

    public static SkillFactory getInstance() {
        return instance;
    }

    public static SkillProto getSkillProto(int index) {
        if (index >= 0 && index < skillProtos.size()) {
            return skillProtos.get(index);
        } else {
            System.out.println("TraitFactory::get() - index out of bounds.");
            return null;
        }
    }

    public static SkillProto getSkillProto(String name) {
        return skillProtos.stream()
                .filter(t -> name.equals(t.getName()))
                .findFirst()
                .orElse(null);
    }

}
