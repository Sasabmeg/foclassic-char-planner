package net.fodev.foclassic.model;

import lombok.Getter;
import lombok.Setter;

public class CombatPerk extends Perk {

    @Getter @Setter private int stack;

    public CombatPerk(String name, String description, String image) {
        super(name, description, image);
        this.stack = 1;
    }
}
