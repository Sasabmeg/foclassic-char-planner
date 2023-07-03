package net.fodev.foclassic.model.fochar;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SkillBookFactory {

    private static final SkillBookFactory instance = new SkillBookFactory();
    @Getter private static List<SkillBookProto> skillBookProtos;

    public static final String NA = "N/A";
    public static final String SCIENCE = "Big Book of Science";
    public static final String REPAIR = "Dean's Electronics";
    public static final String OUTDOORSMAN = "Scout Handbook";
    public static final String BARTER = "Tales of a Junktown Jerky Vendor";
    public static final String FIRST_AID = "First Aid Book";
    public static final String SMALL_GUNS = "Guns and Bullets";

    private SkillBookFactory() {
        System.out.println("SkillBookFactory::Constructor()");
        skillBookProtos = new ArrayList<>();
        skillBookProtos.add(new SkillBookProto(SkillFactory.getSkillProto(SkillFactory.SCIENCE), SCIENCE, 6));
        skillBookProtos.add(new SkillBookProto(SkillFactory.getSkillProto(SkillFactory.REPAIR), REPAIR, 6));
        skillBookProtos.add(new SkillBookProto(SkillFactory.getSkillProto(SkillFactory.OUTDOORSMAN), OUTDOORSMAN, 6));
        skillBookProtos.add(new SkillBookProto(SkillFactory.getSkillProto(SkillFactory.BARTER), BARTER, 6));
        skillBookProtos.add(new SkillBookProto(SkillFactory.getSkillProto(SkillFactory.FIRST_AID), FIRST_AID, 6));
        skillBookProtos.add(new SkillBookProto(SkillFactory.getSkillProto(SkillFactory.SMALL_GUNS), SMALL_GUNS, 6));
    }

    public static SkillBookProto getSkillBookProto(int index) {
        if (index >= 0 && index < skillBookProtos.size()) {
            return skillBookProtos.get(index);
        } else {
            System.out.println("SkillBookFactory::getSkillBook() - index out of bounds.");
            return null;
        }
    }

    public static SkillBookProto getSkillBookProto(String name) {
        return skillBookProtos.stream()
                .filter(t -> name.equals(t.getName()))
                .findFirst()
                .orElse(null);
    }
}
