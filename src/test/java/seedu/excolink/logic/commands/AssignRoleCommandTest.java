package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.person.Email;
import seedu.excolink.model.person.Name;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.testutil.PersonBuilder;

public class AssignRoleCommandTest {

    private Model model;
    private Person testPerson;

    @BeforeEach
    public void setUp() {
        // Create a model with one test person
        testPerson = new Person(
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new HashSet<>(),
                new Subcom("Publicity")
        );
        model = new ModelManager();
        model.addPerson(testPerson);
    }

    @Test
    public void execute_validIndex_success() throws CommandException {
        Index index = Index.fromOneBased(1);
        Role role = new Role("Treasurer");

        AssignRoleCommand command = new AssignRoleCommand(index, role);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(AssignRoleCommand.MESSAGE_ASSIGN_ROLE_SUCCESS,
                testPerson.getName(), role.roleName);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify that the role was actually added
        Person updatedPerson = model.getFilteredPersonList().get(0);
        assertEquals(Collections.singleton(role), updatedPerson.getRoles());
    }
    @Test
    public void execute_duplicateRole_throwsCommandException() {
        Index index = Index.fromOneBased(1);
        Role role = new Role("lead");

        // Create a person with an existing role
        Person personWithRole = new PersonBuilder().withRoles("lead").build();

        model.setPerson(model.getFilteredPersonList().get(0), personWithRole);

        AssignRoleCommand command = new AssignRoleCommand(index, role);

        try {
            command.execute(model);
        } catch (CommandException e) {
            String expectedMessage = String.format(
                    AssignRoleCommand.MESSAGE_DUPLICATE_ROLE,
                    personWithRole.getName(),
                    role
            );
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(99);
        Role role = new Role("Treasurer");
        AssignRoleCommand command = new AssignRoleCommand(invalidIndex, role);

        try {
            command.execute(model);
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Index index = Index.fromOneBased(1);
        Role role = new Role("Treasurer");

        AssignRoleCommand command1 = new AssignRoleCommand(index, role);
        AssignRoleCommand command2 = new AssignRoleCommand(index, role);

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        Role role = new Role("Treasurer");

        AssignRoleCommand command1 = new AssignRoleCommand(Index.fromOneBased(1), role);
        AssignRoleCommand command2 = new AssignRoleCommand(Index.fromOneBased(2), role);

        assertNotEquals(command1, command2);
    }

    @Test
    public void equals_differentRole_returnsFalse() {
        Index index = Index.fromOneBased(1);

        AssignRoleCommand command1 = new AssignRoleCommand(index, new Role("Treasurer"));
        AssignRoleCommand command2 = new AssignRoleCommand(index, new Role("Secretary"));

        assertNotEquals(command1, command2);
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        Index index = Index.fromOneBased(1);
        Role role = new Role("Treasurer");

        AssignRoleCommand command1 = new AssignRoleCommand(index, role);
        AssignRoleCommand command2 = new AssignRoleCommand(index, role);

        assertEquals(command1.hashCode(), command2.hashCode());
    }
}
