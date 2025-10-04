package seedu.excolink.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.excolink.commons.exceptions.DataLoadingException;
import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.ReadOnlyUserPrefs;
import seedu.excolink.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ExcoLinkStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getExcoLinkFilePath();

    @Override
    Optional<ReadOnlyExcoLink> readExcoLink() throws DataLoadingException;

    @Override
    void saveExcoLink(ReadOnlyExcoLink excoLink) throws IOException;

}
