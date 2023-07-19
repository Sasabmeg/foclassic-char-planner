package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

public class Perk {
    @Getter @Setter protected String name;
    @Getter @Setter protected String image;
    @Getter @Setter protected String description;
    @Getter @Setter protected int minLevel;

    public Perk(String name, int level, String image, String description) {
        this.name = name;
        this.minLevel = level;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Perk)) return false;

        Perk perk = (Perk) o;

        if (!name.equals(perk.name)) return false;
        if (!image.equals(perk.image)) return false;
        return description.equals(perk.description);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + image.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
