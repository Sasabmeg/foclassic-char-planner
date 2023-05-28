package net.fodev.foclassic.model.dialog;

import lombok.Getter;
import lombok.Setter;
import net.fodev.foclassic.model.fochar.FoCharacter;

import java.util.function.Function;

public class DialogDemandNode {
    @Getter @Setter private String message;
    @Getter @Setter private Function<FoCharacter, Boolean> demand;

    public DialogDemandNode(Function<FoCharacter, Boolean> demand, String message) {
        this.message = message;
        this.demand = demand;
    }
}
