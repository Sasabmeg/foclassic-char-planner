package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

public class Skill {
    @Getter @Setter SkillProto proto;
    @Getter @Setter private int value;
    @Getter @Setter private boolean tagged;

    public Skill(SkillProto proto, int value, boolean tagged) {
        this.proto = proto;
        this.value = value;
        this.tagged = tagged;
    }

    public void increaseValue(int byValue) {
        this.value += byValue;
    }

    public int getSkillRaiseCost() {
        if (value > 200) return 6;
        if (value > 175) return 5;
        if (value > 150) return 4;
        if (value > 125) return 3;
        if (value > 100) return 2;
        return 1;
    }

    public void raiseSkillValue() {
        if (isTagged()) {
            value+= 2;
        } else {
            value++;
        }
    }

    public int getSkillUnraiseGain() {
        if (tagged) {
            if (value > 202) return 6;
            if (value > 177) return 5;
            if (value > 152) return 4;
            if (value > 127) return 3;
            if (value > 102) return 2;

        } else {
            if (value > 200) return 6;
            if (value > 175) return 5;
            if (value > 150) return 4;
            if (value > 125) return 3;
            if (value > 100) return 2;

        }
        return 1;
    }


    public void unraiseSkillValue() {
        if (isTagged()) {
            value-= 2;
        } else {
            value--;
        }
    }

    public void decreaseValue(int byValue) {
        this.value -= byValue;
    }

    @Override
    public String toString() {
        return proto.getName();
    }

    public String getName() {
        return proto.getName();
    }

    public String getDescription() {
        return proto.getDescription();
    }

    public String getImage() {
        return proto.getImage();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;

        Skill skill = (Skill) o;

        if (value != skill.value) return false;
        if (tagged != skill.tagged) return false;
        return proto.equals(skill.proto);
    }

    @Override
    public int hashCode() {
        int result = proto.hashCode();
        result = 31 * result + value;
        result = 31 * result + (tagged ? 1 : 0);
        return result;
    }
}
