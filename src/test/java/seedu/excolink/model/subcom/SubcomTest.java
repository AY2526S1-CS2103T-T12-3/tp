package seedu.excolink.model.subcom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.excolink.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SubcomTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subcom(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Subcom subcom = new Subcom("Tech");
        assertEquals(subcom, subcom);
    }

    @Test
    public void equals_sameName_returnsTrue() {
        Subcom subcom1 = new Subcom("Tech");
        Subcom subcom2 = new Subcom("Tech");
        assertEquals(subcom1, subcom2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Subcom subcom1 = new Subcom("Tech");
        Subcom subcom2 = new Subcom("Ops");
        assertNotEquals(subcom1, subcom2);
    }

    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        Subcom subcom = new Subcom("Tech");
        assertNotEquals(subcom, null);
        assertNotEquals(subcom, "Tech");
    }

    @Test
    public void hashCode_sameName_returnsSameHash() {
        Subcom subcom1 = new Subcom("Tech");
        Subcom subcom2 = new Subcom("Tech");
        assertEquals(subcom1.hashCode(), subcom2.hashCode());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        Subcom subcom = new Subcom("Tech");
        assertEquals("[Tech]", subcom.toString());
    }
}
