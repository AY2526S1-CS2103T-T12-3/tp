package seedu.excolink.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.excolink.commons.core.LogsCenter;
import seedu.excolink.commons.exceptions.DataLoadingException;
import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.ReadOnlyUserPrefs;
import seedu.excolink.model.UserPrefs;

/**
 * Manages storage of ExcoLink data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExcoLinkStorage excoLinkStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ExcoLinkStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ExcoLinkStorage excoLinkStorage, UserPrefsStorage userPrefsStorage) {
        this.excoLinkStorage = excoLinkStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ExcoLink methods ==============================

    @Override
    public Path getExcoLinkFilePath() {
        return excoLinkStorage.getExcoLinkFilePath();
    }

    @Override
    public Optional<ReadOnlyExcoLink> readExcoLink() throws DataLoadingException {
        return readExcoLink(excoLinkStorage.getExcoLinkFilePath());
    }

    @Override
    public Optional<ReadOnlyExcoLink> readExcoLink(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return excoLinkStorage.readExcoLink(filePath);
    }

    @Override
    public void saveExcoLink(ReadOnlyExcoLink excoLink) throws IOException {
        saveExcoLink(excoLink, excoLinkStorage.getExcoLinkFilePath());
    }

    @Override
    public void saveExcoLink(ReadOnlyExcoLink excoLink, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        excoLinkStorage.saveExcoLink(excoLink, filePath);
    }

}
