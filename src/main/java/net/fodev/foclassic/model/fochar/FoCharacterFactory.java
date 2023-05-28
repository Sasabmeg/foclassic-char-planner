package net.fodev.foclassic.model.fochar;

public class FoCharacterFactory {
    public static FoCharacter copy(FoCharacter other) {
        FoCharacter result = new FoCharacter(other.getName(), other.getAge(), other.getSex());
        other.getSpecials().forEach(special -> result.getSpecials().add(special));
        other.getSkills().forEach(skill -> result.getSkills().add(skill));
        other.getTraits().forEach(trait -> result.getTraits().add(trait));
        result.setUnusedSpecialPoints(other.getUnusedSpecialPoints());
        result.setUnusedSkillPoints(other.getUnusedSkillPoints());
        result.setUnusedTagPoints(other.getUnusedTagPoints());
        result.setUnusedTraitPoints(other.getUnusedTraitPoints());
        result.setUnusedPerkPoints(other.getUnusedPerkPoints());

        result.setHitPoints(other.getHitPoints());

        return result;
    }

    public static FoCharacter createNewCharacter(String name, int age, String sex) {
        FoCharacter result = new FoCharacter(name, age, sex);

        result.setUnusedSpecialPoints(5);
        result.getSpecials().add(new Special(SpecialFactory.getSpecialProto(SpecialFactory.STRENGTH), 5));
        result.getSpecials().add(new Special(SpecialFactory.getSpecialProto(SpecialFactory.PERCEPTION), 5));
        result.getSpecials().add(new Special(SpecialFactory.getSpecialProto(SpecialFactory.ENDURANCE), 5));
        result.getSpecials().add(new Special(SpecialFactory.getSpecialProto(SpecialFactory.CHARISMA), 5));
        result.getSpecials().add(new Special(SpecialFactory.getSpecialProto(SpecialFactory.INTELLECT), 5));
        result.getSpecials().add(new Special(SpecialFactory.getSpecialProto(SpecialFactory.AGILITY), 5));
        result.getSpecials().add(new Special(SpecialFactory.getSpecialProto(SpecialFactory.LUCK), 5));

        result.setUnusedTagPoints(3);
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.SMALL_GUNS), 5 + 4 * result.getAgility(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.BIG_GUNS), 2 * result.getAgility(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.ENERGY_WEAPONS), 2 * result.getAgility(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.CLOSE_COMBAT), 30 + 2 * (result.getAgility() + result.getStrength()), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.SCAVENGING), 0, false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.THROWING), 4 * result.getAgility(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.FIRST_AID), 2 * (result.getPerception() + result.getIntellect()), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.DOCTOR), 5 + result.getPerception() + result.getIntellect(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.SNEAK), 5 + 3 * result.getAgility(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.LOCKPICK), 10 + result.getAgility() + result.getPerception(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.STEAL), 3 * result.getAgility(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.TRAPS), 3 * result.getAgility(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.SCIENCE), 4 * result.getIntellect(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.REPAIR), 3 * result.getIntellect(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.SPEECH), 5 * result.getCharisma(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.BARTER), 4 * result.getCharisma(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.GAMBLING), 5 * result.getLuck(), false));
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.OUTDOORSMAN), 2 * (result.getEndurance() + result.getIntellect()), false));

        result.setUnusedTraitPoints(2);

        result.getTraits().add(TraitFactory.get(TraitFactory.FAST_METABOLISM));
        result.getTraits().add(TraitFactory.get(TraitFactory.BRUISER));
        result.getTraits().add(TraitFactory.get(TraitFactory.ONE_HANDER));
        result.getTraits().add(TraitFactory.get(TraitFactory.FINESSE));
        result.getTraits().add(TraitFactory.get(TraitFactory.KAMIKAZE));
        result.getTraits().add(TraitFactory.get(TraitFactory.HEAVY_HANDED));
        result.getTraits().add(TraitFactory.get(TraitFactory.FAST_SHOT));
        result.getTraits().add(TraitFactory.get(TraitFactory.NA));
        result.getTraits().add(TraitFactory.get(TraitFactory.BLOODY_MESS));
        result.getTraits().add(TraitFactory.get(TraitFactory.JINXED));
        result.getTraits().add(TraitFactory.get(TraitFactory.GOOD_NATURED));
        result.getTraits().add(TraitFactory.get(TraitFactory.CHEM_RELIANT));
        result.getTraits().add(TraitFactory.get(TraitFactory.BONEHEAD));
        result.getTraits().add(TraitFactory.get(TraitFactory.SKILLED));
        result.getTraits().add(TraitFactory.get(TraitFactory.LONER));
        result.getTraits().add(TraitFactory.get(TraitFactory.NA));

        //  hit points formula: BASE = 55 + ST + 2 * EN ; PER LEVEL = EN / 2
        result.setHitPoints(55);
        result.setActionPoints(7);
        result.setCarryWeight((int)((25 + result.getStrength() * 25) / 2.2) + 20);
        result.setDamageResistance(0);
        result.setPoisonResistance(15);
        result.setRadiationResistance(10);
        result.setSequence(14);
        result.setHealingRate(7 + result.getEndurance() / 2);
        result.setCriticalChance(result.getLuck());

        //  awareness support perk is given at start
        result.addSupportPerk(PerkFactory.getSupportPerk(PerkFactory.AWARENESS));

        return result;
    }
}
