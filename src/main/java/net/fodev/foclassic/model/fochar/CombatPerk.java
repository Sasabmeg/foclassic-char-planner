package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

public class CombatPerk extends Perk {

    @Getter @Setter private int stack;

    public CombatPerk(String name, int level, String description, String image) {
        super(name, level, description, image);
        this.stack = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombatPerk)) return false;
        if (!super.equals(o)) return false;

        CombatPerk that = (CombatPerk) o;

        return stack == that.stack;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + stack;
        return result;
    }
}
