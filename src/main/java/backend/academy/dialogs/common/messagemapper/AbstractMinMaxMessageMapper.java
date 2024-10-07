package backend.academy.dialogs.common.messagemapper;

import backend.academy.dialogs.common.MoreCharactersInputException;
import backend.academy.dialogs.dialogcenter.DialogCenter;
import backend.academy.dialogs.minmaxdialog.exception.NotDigitException;
import backend.academy.dialogs.minmaxdialog.exception.NumberOutOfRangeException;


public abstract class AbstractMinMaxMessageMapper extends AbstractMessageMapper implements MessageMapper {
    public AbstractMinMaxMessageMapper(DialogCenter dialogCenter) {
        super(dialogCenter);
    }

    @Override
    public String apply(RuntimeException e) {
        if (e instanceof MoreCharactersInputException) {
            return messageMoreCharactersInputException();
        } else if (e instanceof NotDigitException) {
            return messageNotDigitException();
        } else if (e instanceof NumberOutOfRangeException) {
            return messageNumberOutOfRangeException();
        }
        throw new IllegalArgumentException("Illegal exception: " + e);
    }

    protected abstract String messageMoreCharactersInputException();

    protected abstract String messageNotDigitException();

    protected abstract String messageNumberOutOfRangeException();
}
