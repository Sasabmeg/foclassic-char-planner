package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

public class Trait {
    @Getter @Setter private String name;
    @Getter @Setter private boolean tagged;
    @Getter @Setter private String image;
    @Getter @Setter private String description;

    public Trait(String name, boolean tagged, String image, String description) {
        this.name = name;
        this.tagged = tagged;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}
