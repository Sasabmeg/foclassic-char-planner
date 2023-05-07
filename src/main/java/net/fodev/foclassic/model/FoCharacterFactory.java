package net.fodev.foclassic.model;

public class FoCharacterFactory {
    public static FoCharacter createNewCharacter(String name, int age, String sex) {
        FoCharacter result = new FoCharacter(name, age, sex);
        result.setUnusedTagPoints(3);
        //  hit points formula: BASE = 55 + ST + 2 * EN ; PER LEVEL = EN / 2
        result.setHitPoints(55);
        result.getSkills().add(new Skill("Small Guns", 5 + 4 * result.getAgility(), false, "Small Guns Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Big Guns", 5 + 4 * result.getAgility(), false, "Big Guns Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Energy Weapons", 5 + 4 * result.getAgility(), false, "Energy Weapons Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Close Combat", 5 + 4 * result.getAgility(), false, "Close Combat Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Scavenging", 5 + 4 * result.getAgility(), false, "Scavenging Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Throwing", 5 + 4 * result.getAgility(), false, "Throwing Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("First Aid", 5 + 4 * result.getAgility(), false, "First Aid Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Doctor", 5 + 4 * result.getAgility(), false, "Doctor Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Sneak", 5 + 4 * result.getAgility(), false, "Sneak Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Lockpick", 5 + 4 * result.getAgility(), false, "Lockpick Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Steal", 5 + 4 * result.getAgility(), false, "Steal Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Traps", 5 + 4 * result.getAgility(), false, "Traps Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Science", 5 + 4 * result.getAgility(), false, "Science Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Repair", 5 + 4 * result.getAgility(), false, "Repair Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Speech", 5 + 4 * result.getAgility(), false, "Speech Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Barter", 5 + 4 * result.getAgility(), false, "Barter Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Gambling", 5 + 4 * result.getAgility(), false, "Gambling Description here", "IMAGE FILE"));
        result.getSkills().add(new Skill("Outdoorsman", 5 + 4 * result.getAgility(), false, "Outdoorsman Description here", "IMAGE FILE"));

        return result;
    }
}
