package net.fodev.foclassic.model.fochar;

public class FoCharacterFactory {
    public static FoCharacter copy(FoCharacter other) {
        FoCharacter result = createNewCharacter(other.getName(), other.getAge(), other.getSex());
        cpy(other, result);
        return result;
    }

    private static void cpy(FoCharacter other, FoCharacter result) {
        result.setLevel(other.getLevel());
        result.setExperience(other.getExperience());
        result.setHitPoints(other.getHitPoints());
        result.setPoisoned(other.getPoisoned());
        result.setRadiated(other.getRadiated());
        result.setEyeDamage(other.isEyeDamage());
        result.setCrippledRightArm(other.isCrippledRightArm());
        result.setCrippledLeftArm(other.isCrippledLeftArm());
        result.setCrippledRightLeg(other.isCrippledRightLeg());
        result.setCrippledLeftLeg(other.isCrippledLeftLeg());
        result.setArmorClass(other.getArmorClass());
        result.setActionPoints(other.getActionPoints());
        result.setCarryWeight(other.getCarryWeight());
        result.setMeleeDamage(other.getMeleeDamage());
        result.setDamageResistance(other.getDamageResistance());
        result.setPoisonResistance(other.getPoisonResistance());
        result.setRadiationResistance(other.getRadiationResistance());
        result.setSequence(other.getSequence());
        result.setHealingRate(other.getHealingRate());
        result.setCriticalChance(other.getCriticalChance());
        result.setUnusedSpecialPoints(other.getUnusedSpecialPoints());
        result.setUnusedSkillPoints(other.getUnusedSkillPoints());
        result.setUnusedTagPoints(other.getUnusedTagPoints());
        result.setUnusedTraitPoints(other.getUnusedTraitPoints());
        result.setUnusedPerkPoints(other.getUnusedPerkPoints());
        for (int i = 0; i < result.getSpecials().size(); i++) {
            result.getSpecial(i).setValue(other.getSpecial(i).getValue());
        }
        for (int i = 0; i < result.getSkills().size(); i++) {
            //System.out.println(String.format("Skill (%s:%d) -> (%s:%d)",
            //        result.getSkillName(i), result.getSkillValue(i), other.getSkillName(i), other.getSkillValue(i)));
            result.getSkill(i).setValue(other.getSkill(i).getValue());
            result.getSkill(i).setTagged(other.getSkill(i).isTagged());
        }
        for (int i = 0; i < result.getTraits().size(); i++) {
            result.getTrait(i).setTagged(other.getTrait(i).isTagged());
        }
        result.getCombatPerks().clear();
        other.getCombatPerks().forEach(cp -> result.getCombatPerks().add(cp));
        result.getSupportPerks().clear();
        other.getSupportPerks().forEach(sp -> result.getSupportPerks().add(sp));
    }

    public static void copyTo(FoCharacter to, FoCharacter from) {
        to.setName(from.getName());
        to.setAge(from.getAge());
        to.setSex(from.getSex());
        cpy(from, to);
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
        result.getSkills().add(new Skill(SkillFactory.getSkillProto(SkillFactory.TRAPS), 10 + result.getAgility() + result.getPerception(), false));
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

        result.getSkillBooks().add(new SkillBook(SkillBookFactory.getSkillBookProto(SkillBookFactory.SCIENCE), 0, 10));
        result.getSkillBooks().add(new SkillBook(SkillBookFactory.getSkillBookProto(SkillBookFactory.REPAIR), 0, 10));
        result.getSkillBooks().add(new SkillBook(SkillBookFactory.getSkillBookProto(SkillBookFactory.OUTDOORSMAN), 0, 10));
        result.getSkillBooks().add(new SkillBook(SkillBookFactory.getSkillBookProto(SkillBookFactory.BARTER), 0, 10));
        result.getSkillBooks().add(new SkillBook(SkillBookFactory.getSkillBookProto(SkillBookFactory.FIRST_AID), 0, 10));
        result.getSkillBooks().add(new SkillBook(SkillBookFactory.getSkillBookProto(SkillBookFactory.SMALL_GUNS), 0, 10));

        //  awareness support perk is given at start
        result.addSupportPerk(PerkFactory.getSupportPerk(PerkFactory.AWARENESS));

        return result;
    }
}
