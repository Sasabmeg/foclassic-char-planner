package net.fodev.foclassic.model.dialog;

import lombok.Getter;
import lombok.Setter;
import net.fodev.foclassic.model.fochar.FoCharacter;

import java.util.function.Consumer;

public class DialogResultNode {
    @Getter @Setter private Consumer<FoCharacter> result;

    public DialogResultNode(Consumer<FoCharacter> result) {
        this.result = result;
    }
}
