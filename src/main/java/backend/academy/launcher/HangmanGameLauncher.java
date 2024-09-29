package backend.academy.launcher;

import Hangman.constant.Language;
import Hangman.dialogs.categorydialog.CategoryDialog;
import Hangman.dialogs.common.dialog.Dialog;
import Hangman.dialogs.dialogcenter.DialogCenter;
import Hangman.dialogs.difficultdialog.DifficultDialog;
import Hangman.dialogs.letterdialog.LetterDialog;
import Hangman.dialogs.minmaxdialog.launcherminmaxdialog.LauncherMinMaxDialog;
import Hangman.display.Display;
import Hangman.exception.EmptyWordListException;
import Hangman.exception.InvalidWordException;
import Hangman.exception.OpenWordsFileException;
import Hangman.exception.ReadWordsFileException;
import Hangman.messagecenter.MessageCenter;
import Hangman.repository.WordRepository;
import Hangman.session.HangmanSession;
import Hangman.session.HangmanSession.Category;
import Hangman.session.HangmanSession.Difficult;
import Hangman.session.HiddenWord;
import java.util.List;
import java.util.Optional;

public class HangmanGameLauncher {
    private enum Key {
        START_TEMPLATE("start_template"),
        DIFFICULT_TEMPLATE("difficult_template"),
        CATEGORY_TEMPLATE("category_template"),
        WELCOME("welcome"),
        EXIT("exit"),
        UNABLE_CONTINUE("unable_continue"),
        OPEN_FILE_ERROR("open_file_error"),
        READ_FILE_ERROR("read_file_error"),
        EMPTY_WORD_LIST("empty_word_list"),
        INVALID_COMMAND("invalid_command"),
        INVALID_WORD_TEMPLATE("invalid_word_template");

        public final String section = "Launcher";
        public final String key;

        Key(String key) {
            this.key = key;
        }
    }

    private static final String START_NEW_GAME_COMMAND = "1";
    private static final String EXIT_COMMAND = "2";
    private static final String EASY_DIFFICULT_COMMAND = "1";
    private static final String CLASSIC_DIFFICULT_COMMAND = "2";
    private static final String NATURE_CATEGORY_COMMAND = "1";
    private static final String COUNTRIES_CATEGORY_COMMAND = "2";
    private static final String ANIMALS_CATEGORY_COMMAND = "3";
    private static final String RANDOM_CATEGORY_COMMAND = "4";

    private final WordRepository wordRepository;
    private final Dialog dialog;
    private final Display infoDisplay;
    private final Display errorDisplay;
    private final MessageCenter messageCenter;
    private final DialogCenter dialogCenter;
    private final Language language;
    private boolean running;

    public HangmanGameLauncher(WordRepository wordRepository, Display infoDisplay, Display errorDisplay, Language language, MessageCenter messageCenter, DialogCenter dialogCenter) {
        this.wordRepository = wordRepository;
        this.dialog = new LauncherMinMaxDialog(infoDisplay::show, errorDisplay::show, dialogCenter, ">>> ", 2, 1);
        this.infoDisplay = infoDisplay;
        this.errorDisplay = errorDisplay;
        this.messageCenter = messageCenter;
        this.dialogCenter = dialogCenter;
        this.language = language;
    }

    public HangmanGameLauncher(WordRepository wordRepository, Display display, Language language, MessageCenter messageCenter, DialogCenter dialogCenter) {
        this(wordRepository, display, display, language, messageCenter, dialogCenter);
    }

    public void start() {
        running = true;
        infoDisplay.show(messageCenter.get(Key.WELCOME.section, Key.WELCOME.key));

        while (running) {
            displayStartMessage();
            String playerInput = dialog.getInput();
            chooseAction(playerInput);
        }
    }

    private void displayStartMessage() {
        String startTemplate = messageCenter.get(Key.START_TEMPLATE.section, Key.START_TEMPLATE.key);
        infoDisplay.show(startTemplate.formatted(START_NEW_GAME_COMMAND, EXIT_COMMAND));
    }

    private void chooseAction(String playerInput) {
        switch (playerInput) {
            case START_NEW_GAME_COMMAND:
                startNewGame();
                break;
            case EXIT_COMMAND:
                exit();
                break;
            default:
                errorDisplay.show(messageCenter.get(Key.INVALID_COMMAND.section, Key.INVALID_COMMAND.key));
                break;
        }
    }

    private void startNewGame() {
        Optional<HiddenWord> wordOptional = getHiddenWord();
        if (wordOptional.isEmpty()) {
            return;
        }
        Optional<Difficult> difficultOptional = getDifficult();
        if (difficultOptional.isEmpty()) {
            return;
        }
        Optional<Category> categoryOptional = getCategory();
        if (categoryOptional.isEmpty()) {
            return;
        }

        HiddenWord word = wordOptional.get();
        Difficult difficult = difficultOptional.get();
        Category category = categoryOptional.get();
        HangmanSession hangmanSession = createHangmanSession(word, difficult, category);
        hangmanSession.start();
    }

    private Optional<HiddenWord> getHiddenWord() {
        try {
            String text = wordRepository.get();
            HiddenWord hiddenWord = new HiddenWord(text);
            return Optional.of(hiddenWord);
        } catch (RuntimeException e) {
            String exceptionMessage = getExceptionMessage(e).orElseThrow(() -> new IllegalArgumentException("Illegal exception: " + e));
            handleWordRepositoryException(exceptionMessage);
        }
        return Optional.empty();
    }

    private Optional<String> getExceptionMessage(RuntimeException e) {
        if (e instanceof OpenWordsFileException) {
            String message = messageCenter.get(Key.OPEN_FILE_ERROR.section, Key.OPEN_FILE_ERROR.key);
            return Optional.of(message);
        } else if (e instanceof ReadWordsFileException) {
            String message = messageCenter.get(Key.READ_FILE_ERROR.section, Key.READ_FILE_ERROR.key);
            return Optional.of(message);
        } else if (e instanceof EmptyWordListException) {
            String message = messageCenter.get(Key.EMPTY_WORD_LIST.section, Key.EMPTY_WORD_LIST.key);
            return Optional.of(message);
        } else if (e instanceof InvalidWordException invalidWordException) {
            String invalidWordTemplate = messageCenter.get(Key.INVALID_WORD_TEMPLATE.section, Key.INVALID_WORD_TEMPLATE.key);
            String message = invalidWordTemplate.formatted(invalidWordException.getInvalidWord());
            return Optional.of(message);
        }
        return Optional.empty();
    }

    private void handleWordRepositoryException(String exceptionMessage) {
        errorDisplay.show(exceptionMessage);
        errorDisplay.show(messageCenter.get(Key.UNABLE_CONTINUE.section, Key.UNABLE_CONTINUE.key));
        exit();
    }

    private Optional<Difficult> getDifficult() {
        String difficultInput = inputDifficult();
        return switch (difficultInput) {
            case EASY_DIFFICULT_COMMAND -> Optional.of(Difficult.EASY);
            case CLASSIC_DIFFICULT_COMMAND -> Optional.of(Difficult.CLASSIC);
            default -> Optional.empty();
        };
    }

    private String inputDifficult() {
        String difficultDialogMessage = getDifficultMessage();
        String[] difficultCommands = List.of(EASY_DIFFICULT_COMMAND, CLASSIC_DIFFICULT_COMMAND).toArray(String[]::new);
        Dialog difficultDialog = new DifficultDialog(infoDisplay::show, errorDisplay::show, dialogCenter, difficultDialogMessage, difficultCommands);
        return difficultDialog.getInput();
    }

    private String getDifficultMessage() {
        String difficult_template = messageCenter.get(Key.DIFFICULT_TEMPLATE.section, Key.DIFFICULT_TEMPLATE.key);
        return String.format("""
                %s
                >>>""", difficult_template.formatted(
                EASY_DIFFICULT_COMMAND, Difficult.EASY.MAX_ATTEMPTS,
                CLASSIC_DIFFICULT_COMMAND, Difficult.CLASSIC.MAX_ATTEMPTS));
    }

    private Optional<Category> getCategory() {
        String categoryInput = inputCategory();
        return switch (categoryInput) {
            case NATURE_CATEGORY_COMMAND -> Optional.of(Category.NATURE);
            case COUNTRIES_CATEGORY_COMMAND -> Optional.of(Category.COUNTRIES);
            case ANIMALS_CATEGORY_COMMAND -> Optional.of(Category.ANIMALS);
            case RANDOM_CATEGORY_COMMAND -> Optional.of(Category.RANDOM);
            default -> Optional.empty();
        };
    }

    private String inputCategory() {
        String categoryDialogMessage = getCategoryMessage();
        String[] categoryCommands = List.of(NATURE_CATEGORY_COMMAND, COUNTRIES_CATEGORY_COMMAND, ANIMALS_CATEGORY_COMMAND, RANDOM_CATEGORY_COMMAND).toArray(String[]::new);
        Dialog categoryDialog = new CategoryDialog(infoDisplay::show, errorDisplay::show, dialogCenter, categoryDialogMessage, categoryCommands);
        return categoryDialog.getInput();
    }

    private String getCategoryMessage() {
        String category_template = messageCenter.get(Key.CATEGORY_TEMPLATE.section, Key.CATEGORY_TEMPLATE.key);
        return String.format("""
                %s
                >>>""", category_template.formatted(
                NATURE_CATEGORY_COMMAND, Category.NATURE,
                COUNTRIES_CATEGORY_COMMAND, Category.COUNTRIES,
                ANIMALS_CATEGORY_COMMAND, Category.ANIMALS,
                RANDOM_CATEGORY_COMMAND, Category.RANDOM));
    }

    // right permutes
    private HangmanSession createHangmanSession(HiddenWord word, Difficult difficult, Category category) {
        Dialog sessionDialog = new LetterDialog(infoDisplay::show, errorDisplay::show, dialogCenter, language, ">>> ");
        return new HangmanSession(difficult, word, category, sessionDialog, infoDisplay, errorDisplay, messageCenter);
    }

    private void exit() {
        infoDisplay.show(messageCenter.get(Key.EXIT.section, Key.EXIT.key));
        running = false;
    }
}
