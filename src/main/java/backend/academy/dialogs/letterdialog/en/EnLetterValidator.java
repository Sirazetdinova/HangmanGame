package backend.academy.dialogs.letterdialog.en;

import Hangman.dialogs.common.AbstractLetterValidator;
import java.lang.Character.UnicodeBlock;

public class EnLetterValidator extends AbstractLetterValidator {
    public EnLetterValidator() {
        super(UnicodeBlock.BASIC_LATIN);
    }
}
