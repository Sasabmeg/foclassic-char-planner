package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

public class Skill {
    @Getter @Setter private String name;
    @Getter @Setter private int value;
    @Getter @Setter private boolean tagged;
    @Getter @Setter private String description;
    @Getter @Setter private String image;

    public Skill(String name, int value, boolean tagged, String description, String image) {
        this.name = name;
        this.value = value;
        this.tagged = tagged;
        this.description = description;
        this.image = image;
    }

    public void increaseValue(int byValue) {
        this.value += byValue;
    }

    public void decreaseValue(int byValue) {
        this.value -= byValue;
    }
}
