package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.excolink.ui.DisplayEntity;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", displayEntity=" + commandResult.getDisplayEntity() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void equals_withDisplayEntity() {
        CommandResult resultA = new CommandResult("feedback", DisplayEntity.SUBCOM);
        CommandResult resultB = new CommandResult("feedback", DisplayEntity.SUBCOM);
        CommandResult resultC = new CommandResult("feedback", DisplayEntity.PERSON);

        // same values -> returns true
        assertTrue(resultA.equals(resultB));

        // different display entity -> returns false
        assertFalse(resultA.equals(resultC));

        // same object -> returns true
        assertTrue(resultA.equals(resultA));

        // null -> returns false
        assertFalse(resultA.equals(null));

        // different types -> returns false
        assertFalse(resultA.equals("feedback"));
    }

    @Test
    public void hashcode_withDisplayEntity() {
        CommandResult resultA = new CommandResult("feedback", DisplayEntity.SUBCOM);
        CommandResult resultB = new CommandResult("feedback", DisplayEntity.SUBCOM);
        CommandResult resultC = new CommandResult("feedback", DisplayEntity.PERSON);

        // same values -> returns same hashcode
        assertEquals(resultA.hashCode(), resultB.hashCode());

        // different display entity -> returns different hashcode
        assertNotEquals(resultA.hashCode(), resultC.hashCode());
    }
}
