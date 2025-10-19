package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.person.Address;
import seedu.excolink.model.person.Email;
import seedu.excolink.model.person.Name;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;

public class DeleteRoleCommandTest {

    private Model model;
    private Person testPerson;
    private Role existingRole;

    @BeforeEach
    public void setUp() {
        existingRole = new Role("Team Lead");

        // Create a person with one role
        testPerson = new Person(
                new Name("Alice"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123, Jurong West Ave 6"),
                new HashSet<>(Collections.singleton(existingRole)),
                new Subcom("Publicity")
        );

        model = new ModelManager();
        model.addPerson(testPerson);
        model.updateFilteredPersonList(p -> true);
    }

    @Test
    public void execute_validRole_success() throws CommandException {
        Index index = Index.fromOneBased(1);
        DeleteRoleCommand command = new DeleteRoleCommand(index, existingRole);

        CommandResult result = command.execute(model);

        String expectedMessage = String.format("Member %s deleted role: %s",
                testPerson.getName(), existingRole.roleName);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify the role was actually removed
        Person updatedPerson = model.getFilteredPersonList().get(0);
        assertEquals(Collections.emptySet(), updatedPerson.getRoles());
    }

    @Test
    public void execute_roleDoesNotExist_throwsCommandException() {
        Index index = Index.fromOneBased(1);
        Role nonExistentRole = new Role("Treasurer");
        DeleteRoleCommand command = new DeleteRoleCommand(index, nonExistentRole);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(DeleteRoleCommand.MESSAGE_ROLE_NOT_FOUND, nonExistentRole.roleName),
                exception.getMessage());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(99);
        DeleteRoleCommand command = new DeleteRoleCommand(invalidIndex, existingRole);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(DeleteRoleCommand.MESSAGE_INVALID_INDEX, exception.getMessage());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Index index = Index.fromOneBased(1);
        Role role = new Role("Team Lead");

        DeleteRoleCommand command1 = new DeleteRoleCommand(index, role);
        DeleteRoleCommand command2 = new DeleteRoleCommand(index, role);

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        Role role = new Role("Team Lead");

        DeleteRoleCommand command1 = new DeleteRoleCommand(Index.fromOneBased(1), role);
        DeleteRoleCommand command2 = new DeleteRoleCommand(Index.fromOneBased(2), role);

        assertNotEquals(command1, command2);
    }

    @Test
    public void equals_differentRole_returnsFalse() {
        Index index = Index.fromOneBased(1);

        DeleteRoleCommand command1 = new DeleteRoleCommand(index, new Role("Team Lead"));
        DeleteRoleCommand command2 = new DeleteRoleCommand(index, new Role("Secretary"));

        assertNotEquals(command1, command2);
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        Index index = Index.fromOneBased(1);
        Role role = new Role("Team Lead");

        DeleteRoleCommand command1 = new DeleteRoleCommand(index, role);
        DeleteRoleCommand command2 = new DeleteRoleCommand(index, role);

        assertEquals(command1.hashCode(), command2.hashCode());
    }
}
