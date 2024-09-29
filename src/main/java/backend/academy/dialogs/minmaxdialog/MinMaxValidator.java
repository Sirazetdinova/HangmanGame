package backend.academy.dialogs.minmaxdialog;

import Hangman.dialogs.common.MoreCharactersInputException;
import Hangman.dialogs.common.Validator;
import Hangman.dialogs.minmaxdialog.exception.NotDigitException;
import Hangman.dialogs.minmaxdialog.exception.NumberOutOfRangeException;

public class MinMaxValidator implements Validator<String> {
    private final int min;
    private final int max;

    public MinMaxValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void validate(String playerInput) {
        if (playerInput.length() != 1) {
            throw new MoreCharactersInputException();
        }

        char typedChar = playerInput.charAt(0);
        if (!isDigit(typedChar)) {
            throw new NotDigitException();
        }

        int typedNumber = parseInt(playerInput);
        if (typedNumber < min | typedNumber > max) {
            throw new NumberOutOfRangeException(typedNumber);
        }
    }

    private int parseInt(String playerInput) {
        try {
            return Integer.parseInt(playerInput);
        } catch (NumberFormatException e) {
            throw new NotDigitException(e);
        }
    }

    private boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }
}
