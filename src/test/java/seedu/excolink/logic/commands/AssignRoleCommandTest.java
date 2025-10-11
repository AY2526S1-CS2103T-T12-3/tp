package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.role.Role;

public class AssignRoleCommandTest {

    @Test
    public void execute_returnsHelloWorldMessage() {
        Model model = new ModelManager(); // basic model stub
        Index index = Index.fromOneBased(1);
        Role role = new Role("Treasurer");

        AssignRoleCommand command = new AssignRoleCommand(index, role);
        CommandResult result = command.execute(model);

        assertEquals("Hello world!", result.getFeedbackToUser());
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
