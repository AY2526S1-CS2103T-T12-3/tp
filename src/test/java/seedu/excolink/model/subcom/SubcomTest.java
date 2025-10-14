package seedu.excolink.model.subcom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SubcomTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subcom(null));
    }

    @Test
    public void constructor_invalidSubcom_throwsIllegalArgumentException() {
        String invalidName = " ";
        assertThrows(IllegalArgumentException.class, () -> new Subcom(invalidName));
    }

    @Test
    public void isValidSubcom() {
        // null
        assertThrows(NullPointerException.class, () -> Subcom.isValidSubcomName(null));

        // invalid
        assertFalse(Subcom.isValidSubcomName(" "));
        assertFalse(Subcom.isValidSubcomName("\t"));

        // valid
        assertTrue(Subcom.isValidSubcomName("Publicity"));
        assertTrue(Subcom.isValidSubcomName("Logistics Committee"));
        assertTrue(Subcom.isValidSubcomName("123"));
        assertTrue(Subcom.isValidSubcomName("!@#$%"));
    }

    @Test
    public void equals() {
        Subcom publicity1 = new Subcom("Publicity");
        Subcom publicity2 = new Subcom("Publicity");
        Subcom logistics = new Subcom("Logistics");

        // same object
        assertTrue(publicity1.equals(publicity1));

        // same values
        assertTrue(publicity1.equals(publicity2));

        // different types
        assertFalse(publicity1.equals("Publicity"));

        // null
        assertFalse(publicity1.equals(null));

        // different subcom
        assertFalse(publicity1.equals(logistics));
    }

    @Test
    public void hashCode_sameForEqualObjects() {
        Subcom publicity1 = new Subcom("Publicity");
        Subcom publicity2 = new Subcom("Publicity");
        assertEquals(publicity1.hashCode(), publicity2.hashCode());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        Subcom subcom = new Subcom("Publicity");
        assertEquals("Publicity", subcom.toString());
    }

    /**
     * Tests that {@link Subcom#NOSUBCOM} behaves correctly.
     * Ensures it is equal to another instance of {@link Subcom.NoSubcom}
     * and its string representation matches {@link Subcom#NOSUBCOM_STRING}.
     */
    @Test
    public void noSubcom_behavesAsExpected() {
        Subcom noSubcom = Subcom.NOSUBCOM;

        // toString() should return "None"
        assertEquals(Subcom.NOSUBCOM_STRING, noSubcom.toString(),
                "NOSUBCOM toString() should return 'None'.");

        // equals() should return true when comparing to another NoSubcom instance
        Subcom anotherNoSubcom = Subcom.NOSUBCOM;
        assertTrue(noSubcom.equals(anotherNoSubcom),
                "Two NoSubcom instances should be equal.");

        // equals() should return false for normal Subcom
        Subcom publicity = new Subcom("Publicity");
        assertFalse(noSubcom.equals(publicity),
                "NoSubcom should not be equal to a normal Subcom instance.");
    }

    @Test
    public void getName_returnsSubcomName() {
        Subcom subcom = new Subcom("Publicity");
        assertEquals("Publicity", subcom.getName());

        Subcom noSubcom = Subcom.NOSUBCOM;
        assertEquals(Subcom.NOSUBCOM_STRING, noSubcom.getName(),
                "getName() for NOSUBCOM should return NOSUBCOM_STRING.");
    }
}
