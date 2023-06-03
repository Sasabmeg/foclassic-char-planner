package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trait)) return false;

        Trait trait = (Trait) o;

        if (tagged != trait.tagged) return false;
        if (!Objects.equals(name, trait.name)) return false;
        if (!Objects.equals(image, trait.image)) return false;
        return Objects.equals(description, trait.description);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tagged ? 1 : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
