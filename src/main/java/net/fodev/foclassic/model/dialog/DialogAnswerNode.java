package net.fodev.foclassic.model.dialog;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class DialogAnswerNode {
    @Getter @Setter String message;
    @Getter @Setter DialogQuestionNode question;
    @Getter @Setter List<DialogDemandNode> demands;
    @Getter @Setter List<DialogResultNode> results;

    public DialogAnswerNode(String message, DialogQuestionNode question) {
        this.message = message;
        this.question = question;
        demands = new ArrayList<>();
        results = new ArrayList<>();
    }

    @Override
    public String toString() {
        return message;
    }
}
