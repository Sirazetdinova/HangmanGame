package backend.academy.dialogs.categorydialog;

import Hangman.dialogs.common.Printer;
import Hangman.dialogs.dialogcenter.DialogCenter;
import Hangman.dialogs.optiondialog.OptionDialog;

public class CategoryDialog extends OptionDialog {
    public CategoryDialog(Printer infoPrinter, Printer errorPrinter, DialogCenter dialogCenter, String title, String... options) {
        super(infoPrinter, errorPrinter, new CategoryMessageMapper(dialogCenter), title, options);
    }

    public CategoryDialog(Printer printer, String title, DialogCenter dialogCenter, String... options) {
        super(printer, title, new CategoryMessageMapper(dialogCenter), options);
    }
}
