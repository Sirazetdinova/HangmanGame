package backend.academy.dialogs.letterdialog;

import Hangman.constant.Language;
import Hangman.dialogs.common.Printer;
import Hangman.dialogs.common.Validator;
import Hangman.dialogs.common.dialog.AbstractDialog;
import Hangman.dialogs.dialogcenter.DialogCenter;
import Hangman.dialogs.letterdialog.en.EnLetterValidator;
import Hangman.dialogs.letterdialog.ru.RuLetterValidator;

public class LetterDialog extends AbstractDialog {
    private static Validator<String> createValidator(Language language) {
        return switch (language) {
            case RU -> new RuLetterValidator();
            case EN -> new EnLetterValidator();
        };
    }

    public LetterDialog(Printer infoPrinter, Printer errorPrinter, DialogCenter dialogCenter, Language language, String title) {
        super(infoPrinter, errorPrinter, title, new LetterMessageMapper(dialogCenter), createValidator(language));
    }
}
