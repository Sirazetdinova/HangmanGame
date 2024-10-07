package backend.academy.dialogs.difficultdialog;

import backend.academy.dialogs.common.messagemapper.AbstractOptionMessageMapper;
import backend.academy.dialogs.dialogcenter.DialogCenter;

public class DifficultMessageMapper extends AbstractOptionMessageMapper {
    public DifficultMessageMapper(DialogCenter dialogCenter) {
        super(dialogCenter);
    }

    @Override
    protected String messageInputDoesNotMatchWithOptions() {
        return dialogCenter.get(Key.INVALID_DIFFICULT.section, Key.INVALID_DIFFICULT.key);
    }

    private enum Key {
        INVALID_DIFFICULT("invalid_difficult");

        public final String section = "DifficultMessageMapper";
        public final String key;

        Key(String key) {
            this.key = key;
        }
    }
}
