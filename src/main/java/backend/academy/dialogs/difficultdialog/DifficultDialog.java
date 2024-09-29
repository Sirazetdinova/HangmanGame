package backend.academy.dialogs.difficultdialog;

import Hangman.dialogs.common.Printer;
import Hangman.dialogs.dialogcenter.DialogCenter;
import Hangman.dialogs.optiondialog.OptionDialog;

public class DifficultDialog extends OptionDialog {
    public DifficultDialog(Printer infoPrinter, Printer errorPrinter, DialogCenter dialogCenter, String title, String... options) {
        super(infoPrinter, errorPrinter, new DifficultMessageMapper(dialogCenter), title, options);
    }

    public DifficultDialog(Printer printer, String title, DialogCenter dialogCenter, String... options) {
        super(printer, title, new DifficultMessageMapper(dialogCenter), options);
    }
}
