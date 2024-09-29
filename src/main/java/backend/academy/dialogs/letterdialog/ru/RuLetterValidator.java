package backend.academy.dialogs.letterdialog.ru;

import Hangman.dialogs.common.AbstractLetterValidator;
import java.lang.Character.UnicodeBlock;

public class RuLetterValidator extends AbstractLetterValidator {
    public RuLetterValidator() {
        super(UnicodeBlock.CYRILLIC);
    }
}
