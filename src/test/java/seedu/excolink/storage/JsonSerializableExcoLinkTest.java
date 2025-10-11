package seedu.excolink.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.excolink.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.exceptions.IllegalValueException;
import seedu.excolink.commons.util.JsonUtil;
import seedu.excolink.model.ExcoLink;
import seedu.excolink.testutil.TypicalPersons;

public class JsonSerializableExcoLinkTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data"
            , "JsonSerializableExcoLinkTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsExcoLink.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonExcoLink.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonExcoLink.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableExcoLink dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableExcoLink.class).get();
        ExcoLink excoLinkFromFile = dataFromFile.toModelType();
        ExcoLink typicalPersonsExcoLink = TypicalPersons.getTypicalExcoLink();
        assertEquals(excoLinkFromFile, typicalPersonsExcoLink);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableExcoLink dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableExcoLink.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableExcoLink dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableExcoLink.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableExcoLink.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
