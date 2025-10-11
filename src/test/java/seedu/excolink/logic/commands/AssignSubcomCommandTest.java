package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.testutil.TypicalPersons.getTypicalExcoLink;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.UserPrefs;

/**
 * Contains unit and integration tests for {@code AssignSubcomCommand}.
 */
public class AssignSubcomCommandTest {

    private Model model = new ModelManager(getTypicalExcoLink(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalExcoLink(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Index index = Index.fromOneBased(1);
        String subcom = "publicity";

        AssignSubcomCommand command = new AssignSubcomCommand(index, subcom);
        String expectedMessage = AssignSubcomCommand.MESSAGE_SUCCESS;

        // Since the current execute() just returns a CommandResult with MESSAGE_SUCCESS,
        // we simply verify that behavior here.
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);
        String publicity = "publicity";
        String logistics = "logistics";

        AssignSubcomCommand assignFirstCommand = new AssignSubcomCommand(firstIndex, publicity);
        AssignSubcomCommand assignSecondCommand = new AssignSubcomCommand(secondIndex, logistics);

        // same values -> returns true
        AssignSubcomCommand assignFirstCommandCopy = new AssignSubcomCommand(firstIndex, publicity);
        assertEquals(assignFirstCommand, assignFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, assignFirstCommand);

        // null -> returns false
        assertNotEquals(null, assignFirstCommand);

        // different index -> returns false
        assertNotEquals(assignFirstCommand, assignSecondCommand);
    }
}
