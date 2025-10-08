package seedu.excolink.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.excolink.commons.core.LogsCenter;
import seedu.excolink.commons.exceptions.DataLoadingException;
import seedu.excolink.commons.exceptions.IllegalValueException;
import seedu.excolink.commons.util.FileUtil;
import seedu.excolink.commons.util.JsonUtil;
import seedu.excolink.model.ReadOnlyExcoLink;

/**
 * A class to access ExcoLink data stored as a json file on the hard disk.
 */
public class JsonExcoLinkStorage implements ExcoLinkStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExcoLinkStorage.class);

    private Path filePath;

    public JsonExcoLinkStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExcoLinkFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExcoLink> readExcoLink() throws DataLoadingException {
        return readExcoLink(filePath);
    }

    /**
     * Similar to {@link #readExcoLink()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyExcoLink> readExcoLink(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableExcoLink> jsonExcoLink = JsonUtil.readJsonFile(
                filePath, JsonSerializableExcoLink.class);
        if (!jsonExcoLink.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExcoLink.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveExcoLink(ReadOnlyExcoLink excoLink) throws IOException {
        saveExcoLink(excoLink, filePath);
    }

    /**
     * Similar to {@link #saveExcoLink(ReadOnlyExcoLink)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExcoLink(ReadOnlyExcoLink excoLink, Path filePath) throws IOException {
        requireNonNull(excoLink);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExcoLink(excoLink), filePath);
    }

}
