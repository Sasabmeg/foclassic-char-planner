package net.fodev.foclassic.model;

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
}
