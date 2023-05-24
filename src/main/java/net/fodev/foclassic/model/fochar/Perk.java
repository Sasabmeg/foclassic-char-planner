package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

public class Perk {
    @Getter @Setter protected String name;
    @Getter @Setter protected String image;
    @Getter @Setter protected String description;

    public Perk(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}
