package backend.academy.dialogs.minmaxdialog;

import backend.academy.dialogs.common.messagemapper.AbstractMinMaxMessageMapper;
import backend.academy.dialogs.dialogcenter.DialogCenter;

public class MinMaxMessageMapper extends AbstractMinMaxMessageMapper {
    public MinMaxMessageMapper(DialogCenter dialogCenter) {
        super(dialogCenter);
    }

    @Override
    protected String messageMoreCharactersInputException() {
        return dialogCenter.get(Key.MORE_CHARACTERS.section, Key.MORE_CHARACTERS.key);
    }

    @Override
    protected String messageNotDigitException() {
        return dialogCenter.get(Key.NOT_DIGIT.section, Key.NOT_DIGIT.key);
    }

    @Override
    protected String messageNumberOutOfRangeException() {
        return dialogCenter.get(Key.NUMBER_OUT_OF_RANGE.section, Key.NUMBER_OUT_OF_RANGE.key);
    }

    private enum Key {
        MORE_CHARACTERS("more_characters"),
        NOT_DIGIT("not_digit"),
        NUMBER_OUT_OF_RANGE("number_out_of_range");

        public final String section = "MinMaxMessageMapper";
        public final String key;

        Key(String key) {
            this.key = key;
        }
    }
}
