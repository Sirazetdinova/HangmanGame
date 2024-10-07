package backend.academy.dialogs.optiondialog;

import backend.academy.dialogs.common.messagemapper.AbstractOptionMessageMapper;
import backend.academy.dialogs.dialogcenter.DialogCenter;

public class OptionMessageMapper extends AbstractOptionMessageMapper {
    public OptionMessageMapper(DialogCenter dialogCenter) {
        super(dialogCenter);
    }

    @Override
    protected String messageInputDoesNotMatchWithOptions() {
        return dialogCenter.get(Key.INPUT_DOES_NOT_MATCH_WITH_OPTIONS.section, Key.INPUT_DOES_NOT_MATCH_WITH_OPTIONS.key);
    }

    private enum Key {
        INPUT_DOES_NOT_MATCH_WITH_OPTIONS("input_does_not_match_with_options");

        public final String section = "OptionMessageMapper";
        public final String key;

        Key(String key) {
            this.key = key;
        }
    }
}
