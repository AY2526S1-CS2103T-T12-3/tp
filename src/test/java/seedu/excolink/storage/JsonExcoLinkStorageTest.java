package seedu.excolink.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.excolink.testutil.Assert.assertThrows;
import static seedu.excolink.testutil.TypicalPersons.ALICE;
import static seedu.excolink.testutil.TypicalPersons.HOON;
import static seedu.excolink.testutil.TypicalPersons.IDA;
import static seedu.excolink.testutil.TypicalPersons.getTypicalExcoLink;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.excolink.commons.exceptions.DataLoadingException;
import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.ReadOnlyExcoLink;

public class JsonExcoLinkStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonExcoLinkStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExcoLink_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExcoLink(null));
    }

    private java.util.Optional<ReadOnlyExcoLink> readExcoLink(String filePath) throws Exception {
        return new JsonExcoLinkStorage(Paths.get(filePath)).readExcoLink(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExcoLink("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readExcoLink("notJsonFormatExcoLink.json"));
    }

    @Test
    public void readExcoLink_invalidPersonExcoLink_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readExcoLink("invalidPersonExcoLink.json"));
    }

    @Test
    public void readExcoLink_invalidAndValidPersonExcoLink_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readExcoLink("invalidAndValidPersonExcoLink.json"));
    }

    @Test
    public void readAndSaveExcoLink_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempExcoLink.json");
        ExcoLink original = getTypicalExcoLink();
        JsonExcoLinkStorage jsonExcoLinkStorage = new JsonExcoLinkStorage(filePath);

        // Save in new file and read back
        jsonExcoLinkStorage.saveExcoLink(original, filePath);
        ReadOnlyExcoLink readBack = jsonExcoLinkStorage.readExcoLink(filePath).get();
        assertEquals(original, new ExcoLink(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonExcoLinkStorage.saveExcoLink(original, filePath);
        readBack = jsonExcoLinkStorage.readExcoLink(filePath).get();
        assertEquals(original, new ExcoLink(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonExcoLinkStorage.saveExcoLink(original); // file path not specified
        readBack = jsonExcoLinkStorage.readExcoLink().get(); // file path not specified
        assertEquals(original, new ExcoLink(readBack));

    }

    @Test
    public void saveExcoLink_nullExcoLink_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExcoLink(null, "SomeFile.json"));
    }

    /**
     * Saves {@code excoLink} at the specified {@code filePath}.
     */
    private void saveExcoLink(ReadOnlyExcoLink excoLink, String filePath) {
        try {
            new JsonExcoLinkStorage(Paths.get(filePath))
                    .saveExcoLink(excoLink, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExcoLink_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExcoLink(new ExcoLink(), null));
    }
}
