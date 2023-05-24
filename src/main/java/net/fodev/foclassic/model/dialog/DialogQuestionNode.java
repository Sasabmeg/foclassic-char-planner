package net.fodev.foclassic.model.dialog;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class DialogQuestionNode {
    @Getter @Setter int index;
    @Getter @Setter String message;
    @Getter @Setter List<DialogAnswerNode> answers;

    public DialogQuestionNode(int index, String message) {
        this.index = index;
        this.message = message;
        answers = new ArrayList<>();
    }

    public void addAnswer(DialogAnswerNode answer) {
        answers.add(answer);
    }
}
