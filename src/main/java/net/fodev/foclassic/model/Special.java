package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

public class Special {
    @Getter @Setter private String name;
    @Getter @Setter private int value;
    @Getter @Setter private String description;
    @Getter @Setter private String image;

    public Special(String name, int value, String description, String image) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.image = image;
    }
}