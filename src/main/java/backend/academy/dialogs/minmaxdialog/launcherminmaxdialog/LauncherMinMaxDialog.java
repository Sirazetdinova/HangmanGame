package backend.academy.dialogs.minmaxdialog.launcherminmaxdialog;

import Hangman.dialogs.common.Printer;
import Hangman.dialogs.dialogcenter.DialogCenter;
import Hangman.dialogs.minmaxdialog.MinMaxDialog;

public class LauncherMinMaxDialog extends MinMaxDialog {
    public LauncherMinMaxDialog(Printer infoPrinter, Printer errorPrinter, DialogCenter dialogCenter, String title, int max, int min) {
        super(infoPrinter, errorPrinter, title, new LauncherMinMaxMessageMapper(dialogCenter), min, max);
    }

    public LauncherMinMaxDialog(Printer printer, DialogCenter dialogCenter, String title, int max, int min) {
        super(printer, title, new LauncherMinMaxMessageMapper(dialogCenter), min, max);
    }
}
