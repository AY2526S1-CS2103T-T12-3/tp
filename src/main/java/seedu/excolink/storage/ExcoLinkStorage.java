package seedu.excolink.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.excolink.commons.exceptions.DataLoadingException;
import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.ReadOnlyExcoLink;

/**
 * Represents a storage for {@link ExcoLink}.
 */
public interface ExcoLinkStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExcoLinkFilePath();

    /**
     * Returns ExcoLink data as a {@link ReadOnlyExcoLink}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyExcoLink> readExcoLink() throws DataLoadingException;

    /**
     * @see #getExcoLinkFilePath()
     */
    Optional<ReadOnlyExcoLink> readExcoLink(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyExcoLink} to the storage.
     * @param excoLink cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExcoLink(ReadOnlyExcoLink excoLink) throws IOException;

    /**
     * @see #saveExcoLink(ReadOnlyExcoLink)
     */
    void saveExcoLink(ReadOnlyExcoLink excoLink, Path filePath) throws IOException;

}
