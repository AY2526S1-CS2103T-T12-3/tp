package seedu.excolink.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.excolink.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.exceptions.IllegalValueException;
import seedu.excolink.model.subcom.Subcom;

public class JsonAdaptedSubcomTest {

    private static final String INVALID_SUBCOM_NAME = " ";
    private static final String VALID_SUBCOM_NAME = "Finance";

    @Test
    public void toModelType_validSubcom_returnsSubcom() throws Exception {
        JsonAdaptedSubcom adaptedSubcom = new JsonAdaptedSubcom(VALID_SUBCOM_NAME);
        Subcom expectedSubcom = new Subcom(VALID_SUBCOM_NAME);
        assertEquals(expectedSubcom, adaptedSubcom.toModelType());
    }

    @Test
    public void toModelType_invalidSubcom_throwsIllegalValueException() {
        JsonAdaptedSubcom adaptedSubcom = new JsonAdaptedSubcom(INVALID_SUBCOM_NAME);
        String expectedMessage = Subcom.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedSubcom::toModelType);
    }

    @Test
    public void toModelType_nullSubcom_throwsIllegalValueException() {
        JsonAdaptedSubcom adaptedSubcom = new JsonAdaptedSubcom((String) null);
        String expectedMessage = Subcom.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedSubcom::toModelType);
    }

    @Test
    public void getSubcomName_returnsCorrectName() {
        JsonAdaptedSubcom adaptedSubcom = new JsonAdaptedSubcom(VALID_SUBCOM_NAME);
        assertEquals(VALID_SUBCOM_NAME, adaptedSubcom.getSubcomName());
    }

    @Test
    public void constructor_fromSubcom_returnsCorrectAdaptedObject() {
        Subcom subcom = new Subcom(VALID_SUBCOM_NAME);
        JsonAdaptedSubcom adaptedSubcom = new JsonAdaptedSubcom(subcom);
        assertEquals(subcom.subcomName, adaptedSubcom.getSubcomName());
    }
}
