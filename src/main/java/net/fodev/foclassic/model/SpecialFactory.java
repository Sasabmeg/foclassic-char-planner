package net.fodev.foclassic.model;

import java.util.ArrayList;
import java.util.List;

public class SpecialFactory {

    private static final SpecialFactory instance = new SpecialFactory();
    private static List<SpecialProto> specialProtos;

    public static final String NA = "N/A";
    public static final String STRENGTH = "Strength";
    public static final String PERCEPTION = "Perception";
    public static final String ENDURANCE = "Endurance";
    public static final String CHARISMA = "Charisma";
    public static final String INTELLECT = "Intellect";
    public static final String AGILITY = "Agility";
    public static final String LUCK = "Luck";

    public static SpecialFactory getInstance() {
        return instance;
    }
    private SpecialFactory() {
        System.out.println("SpecialFactory::Constructor()");
        specialProtos = new ArrayList<>();
        specialProtos.add(new SpecialProto("Strength","ST", "images/skilldex/STRENGTH.png", "Raw physical strength. A high Strength is good for physical characters. Modifies: Hit Points, Melee Damage, and Carry Weight."));
        specialProtos.add(new SpecialProto("Perception", "PE", "images/skilldex/PERCEPTN.png", "The ability to see, hear, taste and notice unusual things. A high Perception is important for a sharpshooter. Modifies: Field of view, sequence and ranged combat distance modifiers."));
        specialProtos.add(new SpecialProto("Endurance", "EN",  "images/skilldex/ENDUR.png", "Stamina and physical toughness. A character with a high Endurance will survive where others may not. Modifies: Hit Points, Poison & Radiation Resistance, Healing Rate, and the additional hit points per level."));
        specialProtos.add(new SpecialProto("Charisma", "CH",  "images/skilldex/CHARISMA.png", "A combination of appearance and charm. A high Charisma is important for characters that want to influence people with words. Modifies: ability to lead other characters, NPC reactions, and barter prices."));
        specialProtos.add(new SpecialProto("Intellect", "IN",  "images/skilldex/INTEL.png", "Knowledge, wisdom and the ability to think quickly. A high Intelligence is important for any character. Modifies: the number of new skill points per level, dialogue options, and many skills."));
        specialProtos.add(new SpecialProto("Agility", "AG",  "images/skilldex/AGILITY.png", "Coordination and the ability to move well. A high Agility is important for any active character. Modifies: Action Points, Armor Class, and many skills."));
        specialProtos.add(new SpecialProto("Luck", "LK",  "images/skilldex/LUCK.png", "Fate. Karma. An extremely high or low Luck will affect the character - somehow. Events and situations will be changed by how lucky (or unlucky) your character is."));
    }

    public static SpecialProto getSpecialProto(int index) {
        if (index >= 0 && index < specialProtos.size()) {
            return specialProtos.get(index);
        } else {
            System.out.println("TraitFactory::get() - index out of bounds.");
            return null;
        }
    }

    public static SpecialProto getSpecialProto(String name) {
        return specialProtos.stream()
                .filter(t -> name.equals(t.getName()))
                .findFirst()
                .orElse(null);
    }

}
