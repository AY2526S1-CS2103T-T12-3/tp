package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.excolink.testutil.TypicalPersons.getTypicalExcoLink;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.UserPrefs;
import seedu.excolink.model.person.Person;


/**
 * This test uses a temporary placeholder for the model call. Need to change it
 * after model is implemented
 */
public class RemoveSubcomMemberCommandTest {
    private Model model = new ModelManager(getTypicalExcoLink(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToRemove = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");

        String expectedMessage = String.format(RemoveSubcomMemberCommand.MESSAGE_REMOVE_SUCCESS,
                personToRemove.getName().fullName, "Finance");

        Model expectedModel = new ModelManager(model.getExcoLink(), new UserPrefs());
        // If the model later mutates the person/subcom membership, update expectedModel

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(outOfBoundIndex, "Finance");

        assertCommandFailure(command, model, RemoveSubcomMemberCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // ensures that outOfBoundIndex is still in bounds of the full address book list
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExcoLink().getPersonList().size());

        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(outOfBoundIndex, "Finance");

        assertCommandFailure(command, model, RemoveSubcomMemberCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
