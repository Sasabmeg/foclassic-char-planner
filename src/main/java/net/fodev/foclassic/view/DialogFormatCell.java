package net.fodev.foclassic.view;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import net.fodev.foclassic.model.dialog.DialogAnswerNode;

public class DialogFormatCell extends ListCell<DialogAnswerNode> {
    @Override
    protected void updateItem(DialogAnswerNode item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(this.getIndex() + 1 + ". " + item.toString());
            setTextFill(isSelected() ? Color.WHITE : item.areDemandsMet() ? Color.valueOf("38FB00") : Color.GREEN);
        } else {
            setText("");
            setTextFill(Color.TRANSPARENT);
        }
    }
}
