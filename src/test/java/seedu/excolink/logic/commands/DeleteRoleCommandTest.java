package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.role.Role;

public class DeleteRoleCommandTest {

    @Test
    public void execute_success() throws CommandException {
        Index index = Index.fromOneBased(1);
        Role role = new Role("Team Lead");

        DeleteRoleCommand command = new DeleteRoleCommand(index, role);
        Model model = new ModelManager(); // dummy model

        String expectedMessage = String.format(DeleteRoleCommand.MESSAGE_SUCCESS, index.getOneBased(), role.roleName);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
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
        DeleteRoleCommand command2 = new DeleteRoleCommand(index, new Role("Developer"));

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
