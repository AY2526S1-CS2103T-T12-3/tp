package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.testutil.TypicalExcoLink.getTypicalExcoLink;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.UserPrefs;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.testutil.TypicalIndexes;
import seedu.excolink.ui.DisplayEntity;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for {@code DeleteSubcomCommand}.
 */
public class DeleteSubcomCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExcoLink(), new UserPrefs());
        model.setDisplayEntity(DisplayEntity.SUBCOM);
        expectedModel = new ModelManager(model.getExcoLink(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() throws CommandException {
        List<Subcom> subcomList = model.getSubcomList();
        Subcom subcomToDelete = subcomList.get(INDEX_FIRST.getZeroBased());

        DeleteSubcomCommand command = new DeleteSubcomCommand(INDEX_FIRST);
        String expectedMessage = String.format(DeleteSubcomCommand.MESSAGE_DELETE_SUBCOM_SUCCESS, subcomToDelete);

        expectedModel.deleteSubcom(subcomToDelete);

        assertCommandSuccess(command, model, new CommandResult(expectedMessage), expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSubcomList().size() + 1);
        DeleteSubcomCommand command = new DeleteSubcomCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_SUBCOM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_wrongDisplayEntity_throwsCommandException() {
        model.setDisplayEntity(DisplayEntity.PERSON);
        DeleteSubcomCommand command = new DeleteSubcomCommand(INDEX_FIRST);

        try {
            command.execute(model);
        } catch (CommandException e) {
            assertEquals(
                    Messages.MESSAGE_WRONG_DISPLAY_ENTITY_FOR_SUBCOM_COMMAND,
                    e.getMessage()
            );
        }
    }

    @Test
    public void equals() {
        DeleteSubcomCommand deleteFirstCommand = new DeleteSubcomCommand(INDEX_FIRST);
        DeleteSubcomCommand deleteSecondCommand = new DeleteSubcomCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteSubcomCommand deleteFirstCommandCopy = new DeleteSubcomCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = INDEX_FIRST;
        DeleteSubcomCommand command = new DeleteSubcomCommand(targetIndex);
        String expected = DeleteSubcomCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }
}
