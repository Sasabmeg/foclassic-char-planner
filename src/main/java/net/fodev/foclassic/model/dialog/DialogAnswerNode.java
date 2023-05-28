package net.fodev.foclassic.model.dialog;

import lombok.Getter;
import lombok.Setter;
import net.fodev.foclassic.model.fochar.FoCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DialogAnswerNode {
    @Getter @Setter FoCharacter foCharacter;
    @Getter @Setter String message;
    @Getter @Setter DialogQuestionNode question;
    @Getter @Setter List<DialogDemandNode> demands;
    @Getter @Setter List<DialogResultNode> results;

    public DialogAnswerNode(String message, DialogQuestionNode question, FoCharacter foCharacter) {
        this.message = message;
        this.question = question;
        this.foCharacter = foCharacter;
        demands = new ArrayList<>();
        results = new ArrayList<>();
    }

    @Override
    public String toString() {
        return message;
    }

    public void addResult(DialogResultNode resultNode) {
        results.add(resultNode);
    }

    public void applyResults() {
        results.forEach(r -> r.getResult().accept(foCharacter));
    }

    public void addDemand(DialogDemandNode demandNode) {
        demands.add(demandNode);
    }

    public boolean areDemandsMet() {
        if (demands.isEmpty()) {
            return true;
        } else {
            return demands.stream().allMatch(d -> d.getDemand().apply(foCharacter));
        }
    }

    public String getDemandErrors() {
        return demands.stream().filter(d -> !d.getDemand().apply(foCharacter)).map(d -> d.getMessage()).collect(Collectors.joining(", "));
    }
}
