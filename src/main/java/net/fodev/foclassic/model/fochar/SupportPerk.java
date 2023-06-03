package net.fodev.foclassic.model.fochar;

public class SupportPerk extends Perk {

    public SupportPerk(String name, String description, String image) {
        super(name, description, image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombatPerk)) return false;
        return  !super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}
