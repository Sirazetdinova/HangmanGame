package backend.academy.dialogs.categorydialog;

import Hangman.dialogs.common.messagemapper.AbstractOptionMessageMapper;
import Hangman.dialogs.dialogcenter.DialogCenter;

public class CategoryMessageMapper extends AbstractOptionMessageMapper {
    private enum Key {
        INVALID_CATEGORY("invalid_category");

        public final String section = "CategoryMessageMapper";
        public final String key;

        Key(String key) {
            this.key = key;
        }
    }

    public CategoryMessageMapper(DialogCenter dialogCenter) {
        super(dialogCenter);
    }

    @Override
    protected String messageInputDoesNotMatchWithOptions() {
        return dialogCenter.get(Key.INVALID_CATEGORY.section, Key.INVALID_CATEGORY.key);
    }
}
