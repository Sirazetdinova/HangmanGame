package backend.academy;

import backend.academy.constant.Language;
import backend.academy.dialogs.common.Printer;
import backend.academy.dialogs.common.dialog.Dialog;
import backend.academy.dialogs.dialogcenter.DialogCenter;
import backend.academy.dialogs.dialogcenter.FileDialogCenter;
import backend.academy.dialogs.dialogcenter.exception.UnableGetDialogCenterException;
import backend.academy.dialogs.languagedialog.LanguageDialog;
import backend.academy.display.Display;
import backend.academy.display.InfoDisplay;
import backend.academy.launcher.HangmanGameLauncher;
import backend.academy.messagecenter.FileMessageCenter;
import backend.academy.messagecenter.MessageCenter;
import backend.academy.messagecenter.exception.UnableGetMessageCenterException;
import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    private static final String UNABLE_GET_LOCALIZATION_FILE = "Unable to get localization file";
    private static final String LEAVING_THE_GAME = "Leaving the game...";

    public static void main(String[] args) {
        Display display = new InfoDisplay();
        Language language = inputLanguage(display::show);

        Optional<FileMessageCenter> messageCenterOptional =
            getMessageCenter("lang/%s", "%s_messages.ini", language, display);
        if (messageCenterOptional.isEmpty()) {
            return;
        }
        Optional<FileDialogCenter> dialogCenterOptional =
            getDialogCenter("lang/%s", "%s_dialogs.ini", language, display);
        if (dialogCenterOptional.isEmpty()) {
            return;
        }
        DialogCenter dialogCenter = dialogCenterOptional.get();
        MessageCenter messageCenter = messageCenterOptional.get();

        HangmanGameLauncher hangmanGameLauncher =
            new HangmanGameLauncher(display, language, messageCenter, dialogCenter);
        hangmanGameLauncher.start();
    }

    private static Optional<FileMessageCenter> getMessageCenter(
        String directory,
        String filenameTemplate,
        Language language,
        Display display
    ) {
        try {
            return Optional.of(new FileMessageCenter(directory, filenameTemplate, language));
        } catch (UnableGetMessageCenterException e) {
            showErrorMessage(display);
            System.exit(0);
            return Optional.empty();
        }
    }

    private static Optional<FileDialogCenter> getDialogCenter(
        String directory,
        String filenameTemplate,
        Language language,
        Display display
    ) {
        try {
            return Optional.of(new FileDialogCenter(directory, filenameTemplate, language));
        } catch (UnableGetDialogCenterException e) {
            showErrorMessage(display);
            System.exit(0);
            return Optional.empty();
        }
    }

    private static void showErrorMessage(Display display) {
        display.show(UNABLE_GET_LOCALIZATION_FILE);
        display.show(LEAVING_THE_GAME);
    }

    private static Language inputLanguage(Printer printer) {
        String languageMessage = getLanguageMessage();
        Dialog dialog = new LanguageDialog(printer, languageMessage);
        String input = dialog.getInput();
        return Language.valueOf(input.toUpperCase());
    }

    private static String getLanguageMessage() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        Arrays.stream(Language.values()).map(Enum::name).forEach(stringJoiner::add);
        return "Choose language: %s".format(stringJoiner.toString());
    }
}
