package net.fodev.foclassic.model.fochar;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialProto)) return false;

        SpecialProto that = (SpecialProto) o;

        if (!name.equals(that.name)) return false;
        if (!shortName.equals(that.shortName)) return false;
        if (!image.equals(that.image)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + shortName.hashCode();
        result = 31 * result + image.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
