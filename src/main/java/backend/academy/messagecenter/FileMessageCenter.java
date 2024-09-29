package backend.academy.messagecenter;

import Hangman.constant.Language;
import Hangman.messagecenter.exception.MessageNotFoundException;
import Hangman.messagecenter.exception.UnableGetMessageCenterException;
import Hangman.util.inireader.IniReader;
import Hangman.util.inireader.exception.IniValueNotFoundException;
import Hangman.util.inireader.exception.OpenIniFileException;
import Hangman.util.inireader.exception.ReadIniFileException;

public class FileMessageCenter implements MessageCenter {
    private final IniReader iniReader;

    public FileMessageCenter(String directory, String filenameTemplate, Language language) {
        String languageName = language.name().toLowerCase();
        String filename = filenameTemplate.formatted(languageName);
        String iniFilename = String.format("%s/%s", directory.formatted(languageName), filename);
        this.iniReader = getIniReader(iniFilename);
    }

    private IniReader getIniReader(String iniFilename) {
        try {
            return new IniReader(iniFilename);
        } catch (OpenIniFileException | ReadIniFileException e) {
            throw new UnableGetMessageCenterException(e);
        }
    }

    @Override
    public String get(String section, String key) {
        try {
            return iniReader.getValue(section, key);
        } catch (IniValueNotFoundException e) {
            throw new MessageNotFoundException(e);
        }
    }
}
