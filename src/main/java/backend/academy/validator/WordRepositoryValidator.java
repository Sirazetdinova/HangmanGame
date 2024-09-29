package backend.academy.validator;

import Hangman.constant.Language;
import Hangman.dialogs.common.AbstractLetterValidator;
import Hangman.dialogs.common.Validator;
import Hangman.dialogs.letterdialog.en.EnLetterValidator;
import Hangman.dialogs.letterdialog.ru.RuLetterValidator;
import Hangman.exception.EmptyWordListException;
import Hangman.exception.InvalidWordException;
import java.util.List;

public class WordRepositoryValidator implements Validator<List<String>> {
    private final AbstractLetterValidator letterValidator;

    public WordRepositoryValidator(Language language) {
        this.letterValidator = createValidator(language);
    }

    private AbstractLetterValidator createValidator(Language language) {
        return switch (language) {
            case RU -> new RuLetterValidator();
            case EN -> new EnLetterValidator();
        };
    }

    @Override
    public void validate(List<String> words) {
        if (words.isEmpty()) {
            throw new EmptyWordListException();
        }

        for (String word : words) {
            if (!isWord(word)) {
                throw new InvalidWordException(word);
            }
        }
    }

    private boolean isWord(String line) {
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (!letterValidator.isLetterInLanguage(ch)) {
                return false;
            }
        }
        return true;
    }
}
