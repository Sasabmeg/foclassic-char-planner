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

    public void raiseSkillValue() {
        if (isTagged()) {
            value+= 2;
        } else {
            value++;
        }
    }

    public int getSkillRaiseCost() {
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
