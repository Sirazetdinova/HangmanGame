package backend.academy.dialogs.common;

import Hangman.dialogs.letterdialog.exception.NotLetterException;
import Hangman.dialogs.letterdialog.exception.NotLetterInLanguageException;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractLetterValidator implements Validator<String> {
    private final List<UnicodeBlock> unicodeBlocks = new ArrayList<>();

    public AbstractLetterValidator(UnicodeBlock unicodeBlock) {
        unicodeBlocks.add(unicodeBlock);
    }

    @Override
    public void validate(String playerInput) {
        if (playerInput.length() != 1) {
            throw new MoreCharactersInputException();
        }
        char typedLetter = playerInput.charAt(0);
        if (!isLetter(typedLetter)) {
            throw new NotLetterException();
        }
        if (!isLetterInLanguage(typedLetter)) {
            throw new NotLetterInLanguageException();
        }
    }

    public boolean isLetterInLanguage(char letter) {
        Predicate<UnicodeBlock> predicate = unicodeBlock -> isLetter(letter) && UnicodeBlock.of(letter).equals(unicodeBlock);
        return unicodeBlocks.stream().anyMatch(predicate);
    }

    public boolean isLetter(char letter) {
        return Character.isLetter(letter);
    }
}
