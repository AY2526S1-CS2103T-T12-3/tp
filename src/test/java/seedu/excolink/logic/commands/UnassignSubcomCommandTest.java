package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.testutil.TypicalExcoLink.getTypicalExcoLink;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.UserPrefs;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.testutil.PersonBuilder;


/**
 * This test uses a temporary placeholder for the model call. Need to change it
 * after model is implemented
 */
public class UnassignSubcomCommandTest {
    private Model model = new ModelManager(getTypicalExcoLink(), new UserPrefs());

    @Test
    public void execute_validIndexAndSubMemberInSubcom_success() {
        Subcom financeSubcom = new Subcom("Finance");
        model.addSubcom(financeSubcom);
        Person personInSubcom = new PersonBuilder()
                .withName("John Doe")
                .withSubcom("Finance")
                .build();
        model.addPerson(personInSubcom);

        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UnassignSubcomCommand command = new UnassignSubcomCommand(lastIndex);

        String expectedMessage = String.format(UnassignSubcomCommand.MESSAGE_REMOVE_SUCCESS,
                personInSubcom.getName().fullName, financeSubcom.toString());

        Model expectedModel = new ModelManager(model.getExcoLink(), new UserPrefs());
        Person editedPerson = new PersonBuilder(personInSubcom).withSubcom(Subcom.NOSUBCOM_STRING).build();
        expectedModel.setPerson(personInSubcom, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignSubcomCommand command = new UnassignSubcomCommand(outOfBoundIndex);

        assertCommandFailure(command, model, UnassignSubcomCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        // ensures that outOfBoundIndex is still in bounds of the full address book list
        Index outOfBoundIndex = INDEX_SECOND;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExcoLink().getPersonList().size());

        UnassignSubcomCommand command = new UnassignSubcomCommand(outOfBoundIndex);

        assertCommandFailure(command, model, UnassignSubcomCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_memberNotInSubcom_throwsComandException() {
        Person personWithNoSubcom = new PersonBuilder()
                .withName("Jane Doe")
                .withSubcom(Subcom.NOSUBCOM_STRING)
                .build();
        model.addPerson(personWithNoSubcom);

        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        UnassignSubcomCommand command = new UnassignSubcomCommand(lastIndex);

        assertCommandFailure(command, model,
                String.format(UnassignSubcomCommand.MESSAGE_MEMBER_NOT_IN_SUBCOM,
                        personWithNoSubcom.getName().fullName));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UnassignSubcomCommand command = new UnassignSubcomCommand(INDEX_FIRST);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        UnassignSubcomCommand commandA = new UnassignSubcomCommand(INDEX_FIRST);
        UnassignSubcomCommand commandB = new UnassignSubcomCommand(INDEX_FIRST);
        assertTrue(commandA.equals(commandB));
        assertEquals(commandA.hashCode(), commandB.hashCode());
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        UnassignSubcomCommand commandA = new UnassignSubcomCommand(INDEX_FIRST);
        UnassignSubcomCommand commandB = new UnassignSubcomCommand(INDEX_SECOND);
        assertFalse(commandA.equals(commandB));
    }

    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        UnassignSubcomCommand command = new UnassignSubcomCommand(INDEX_FIRST);
        assertFalse(command.equals(null));
        assertFalse(command.equals(5)); // different type
        assertFalse(command.equals("unassign")); // different type
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex}.
     */
    private void showPersonAtIndex(Model model, Index targetIndex) {
        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(p -> p.equals(person));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        UnassignSubcomCommand commandA = new UnassignSubcomCommand(INDEX_FIRST);
        UnassignSubcomCommand commandB = new UnassignSubcomCommand(INDEX_FIRST);
        assertEquals(commandA.hashCode(), commandB.hashCode());
    }
}
