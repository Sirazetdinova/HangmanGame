package backend.academy.dialogs.difficultdialog;

import Hangman.dialogs.common.messagemapper.AbstractOptionMessageMapper;
import Hangman.dialogs.dialogcenter.DialogCenter;

public class DifficultMessageMapper extends AbstractOptionMessageMapper {
    private enum Key {
        INVALID_DIFFICULT("invalid_difficult");

        public final String section = "DifficultMessageMapper";
        public final String key;

        Key(String key) {
            this.key = key;
        }
    }

    public DifficultMessageMapper(DialogCenter dialogCenter) {
        super(dialogCenter);
    }

    @Override
    protected String messageInputDoesNotMatchWithOptions() {
        return dialogCenter.get(Key.INVALID_DIFFICULT.section, Key.INVALID_DIFFICULT.key);
    }
}
