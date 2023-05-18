package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

public class SpecialProto {

    @Getter @Setter private String name;
    @Getter @Setter private String shortName;
    @Getter @Setter private String image;
    @Getter @Setter private String description;

    public SpecialProto(String name, String shortName, String image, String description) {
        this.name = name;
        this.shortName = shortName;
        this.image = image;
        this.description = description;
    }
}
