package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.testutil.TypicalExcoLink.getTypicalExcoLink;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.excolink.testutil.TypicalSubcoms.TECH;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.Messages;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.UserPrefs;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;

/**
 * Contains unit and integration tests for {@code AssignSubcomCommand}.
 */
public class AssignSubcomCommandTest {

    private Model model = new ModelManager(getTypicalExcoLink(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalExcoLink(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Subcom subcom = TECH;
        AssignSubcomCommand command = new AssignSubcomCommand(INDEX_FIRST, subcom);
        String expectedMessage = String.format(AssignSubcomCommand.MESSAGE_SUCCESS, personToAssign.getName(),
                subcom.getName());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignSubcomCommand command = new AssignSubcomCommand(outOfBoundsIndex, TECH);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSubcom_throwsCommandException() {
        Index validIndex = Index.fromOneBased(1);
        Subcom invalidSubcom = new Subcom("NonExistentSubcom");
        AssignSubcomCommand command = new AssignSubcomCommand(validIndex, invalidSubcom);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_SUBCOM_NAME);
    }

    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);
        Subcom publicity = new Subcom("publicity");
        Subcom logistics = new Subcom("logistics");

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
