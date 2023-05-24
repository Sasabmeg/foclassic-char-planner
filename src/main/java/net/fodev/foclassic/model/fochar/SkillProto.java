package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

public class SkillProto {
    @Getter @Setter private String name;
    @Getter @Setter private String image;
    @Getter @Setter private String description;

    public SkillProto(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }
}
