package seedu.excolink.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.excolink.testutil.TypicalPersons.getTypicalExcoLink;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.excolink.commons.core.GuiSettings;
import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonExcoLinkStorage excoLinkStorage = new JsonExcoLinkStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(excoLinkStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void excoLinkReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonExcoLinkStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonExcoLinkStorageTest} class.
         */
        ExcoLink original = getTypicalExcoLink();
        storageManager.saveExcoLink(original);
        ReadOnlyExcoLink retrieved = storageManager.readExcoLink().get();
        assertEquals(original, new ExcoLink(retrieved));
    }

    @Test
    public void getExcoLinkFilePath() {
        assertNotNull(storageManager.getExcoLinkFilePath());
    }

}
