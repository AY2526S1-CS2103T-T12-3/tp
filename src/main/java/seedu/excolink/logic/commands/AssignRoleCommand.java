package seedu.excolink.logic.commands;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.model.Model;
import seedu.excolink.model.role.Role;

/**
 * Assigns a role to a person in ExcoLink.
 */
public class AssignRoleCommand extends Command {

    public static final String COMMAND_WORD = "assign-role";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a role to a member by index.\n"
            + "Parameters: INDEX (must be a positive integer) r/ROLE\n"
            + "Example: " + COMMAND_WORD + " 1 r/Treasurer";

    private final Index index;
    private final Role role;

    /**
     * Creates an AssignRoleCommand to assign a role to the person.
     */
    public AssignRoleCommand(Index index, Role role) {
        this.index = index;
        this.role = role;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello world!");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AssignRoleCommand)) {
            return false;
        }
        AssignRoleCommand otherCommand = (AssignRoleCommand) other;
        return index.equals(otherCommand.index) && role.equals(otherCommand.role);
    }

    @Override
    public int hashCode() {
        return index.hashCode() * 31 + role.hashCode();
    }

}
