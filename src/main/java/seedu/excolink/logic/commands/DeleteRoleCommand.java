package seedu.excolink.logic.commands;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.role.Role;

/**
 * Removes a role of an existing member identified using its displayed index.
 */
public class DeleteRoleCommand extends Command {

    public static final String COMMAND_WORD = "delete-role";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a role from the member identified by the index.\n"
            + "Parameters: INDEX (must be a positive integer) r/ROLE\n"
            + "Example: " + COMMAND_WORD + " 1 r/Team Lead";

    public static final String MESSAGE_SUCCESS = "DeleteRoleCommand executed for index %1$d, role: %2$s";

    private final Index index;
    private final Role role;

    /**
     * Creates an DeleteRoleCommand to remove the specified {@code Role} of a member
     */
    public DeleteRoleCommand(Index index, Role role) {
        this.index = index;
        this.role = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        System.out.println("Hello world from DeleteRoleCommand!");
        return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased(), role.roleName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteRoleCommand)) {
            return false;
        }
        DeleteRoleCommand otherCommand = (DeleteRoleCommand) other;
        return index.equals(otherCommand.index)
                && role.equals(otherCommand.role);
    }

    @Override
    public int hashCode() {
        return index.hashCode() * 31 + role.hashCode();
    }
}
