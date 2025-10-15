package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.excolink.testutil.TypicalPersons.getTypicalExcoLink;

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
public class RemoveSubcomMemberCommandTest {
    private Model model = new ModelManager(getTypicalExcoLink(), new UserPrefs());

    @Test
    public void execute_validIndexAndSubMemberInSubcom_success() {
        Model testModel = new ModelManager(getTypicalExcoLink(), new UserPrefs());

        Subcom financeSubcom = new Subcom("Finance");
        testModel.addSubcom(financeSubcom);
        Person personInSubcom = new PersonBuilder()
                .withName("John Doe")
                .withSubcom("Finance")
                .build();
        testModel.addPerson(personInSubcom);

        Index lastIndex = Index.fromOneBased(testModel.getFilteredPersonList().size());
        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(lastIndex, "Finance");

        String expectedMessage = String.format(RemoveSubcomMemberCommand.MESSAGE_REMOVE_SUCCESS,
                personInSubcom.getName().fullName, "Finance");

        Model expectedModel = new ModelManager(testModel.getExcoLink(), new UserPrefs());
        Person editedPerson = new PersonBuilder(personInSubcom).withSubcom(Subcom.NOSUBCOM_STRING).build();
        expectedModel.setPerson(personInSubcom, editedPerson);

        assertCommandSuccess(command, testModel, expectedMessage, expectedModel);
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

    @Test
    public void execute_memberNotInSubcom_throwsComandException() {
        Model testModel = new ModelManager(getTypicalExcoLink(), new UserPrefs());

        Subcom financeSubcom = new Subcom("Finance");
        testModel.addSubcom(financeSubcom);

        Person personWithNoSubcom = new PersonBuilder()
                .withName("Jane Doe")
                .withSubcom(Subcom.NOSUBCOM_STRING)
                .build();
        testModel.addPerson(personWithNoSubcom);

        Index lastIndex = Index.fromOneBased(testModel.getFilteredPersonList().size());
        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(lastIndex, "Finance");

        assertCommandFailure(command, testModel,
                String.format(RemoveSubcomMemberCommand.MESSAGE_MEMBER_NOT_IN_SUBCOM,
                        personWithNoSubcom.getName().fullName, "Finance"));
    }

    @Test public void execute_memberInDifferentSubcom_throwsCommandException() {
        Model testModel = new ModelManager(getTypicalExcoLink(), new UserPrefs());

        Subcom financeSubcom = new Subcom("Finance");
        Subcom techSubcom = new Subcom("Tech");
        testModel.addSubcom(financeSubcom);
        testModel.addSubcom(techSubcom);

        Person personInTech = new PersonBuilder()
                .withName("Bob Smith")
                .withSubcom("Tech")
                .build();
        testModel.addPerson(personInTech);

        Index lastIndex = Index.fromOneBased(testModel.getFilteredPersonList().size());

        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(lastIndex, "Finance");

        assertCommandFailure(command, testModel,
                String.format(RemoveSubcomMemberCommand.MESSAGE_MEMBER_NOT_IN_SUBCOM,
                        personInTech.getName().fullName, "Finance"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        RemoveSubcomMemberCommand commandA = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");
        RemoveSubcomMemberCommand commandB = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");
        assertTrue(commandA.equals(commandB));
        assertEquals(commandA.hashCode(), commandB.hashCode());
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        RemoveSubcomMemberCommand commandA = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");
        RemoveSubcomMemberCommand commandB = new RemoveSubcomMemberCommand(INDEX_SECOND_PERSON, "Finance");
        assertFalse(commandA.equals(commandB));
    }

    @Test
    public void equals_differentSubcom_returnsFalse() {
        RemoveSubcomMemberCommand commandA = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");
        RemoveSubcomMemberCommand commandB = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Events");
        assertFalse(commandA.equals(commandB));
    }

    @Test
    public void equals_differentSubcomWithWhitespace_returnsFalse() {
        RemoveSubcomMemberCommand commandA = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");
        RemoveSubcomMemberCommand commandB = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance ");
        assertTrue(commandA.equals(commandB));
    }
    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        RemoveSubcomMemberCommand command = new RemoveSubcomMemberCommand(INDEX_FIRST_PERSON, "Finance");
        assertFalse(command.equals(null));
        assertFalse(command.equals(5)); // different type
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex}.
     */
    private void showPersonAtIndex(Model model, Index targetIndex) {
        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(p -> p.equals(person));
    }
}
