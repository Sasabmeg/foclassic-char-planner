package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class SkillProto {
    @Getter @Setter private String name;
    @Getter @Setter private String image;
    @Getter @Setter private String description;

    public SkillProto(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillProto)) return false;

        SkillProto that = (SkillProto) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(image, that.image)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
