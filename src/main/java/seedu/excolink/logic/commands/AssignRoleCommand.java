package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.role.Role;
import seedu.excolink.ui.DisplayEntity;

/**
 * Assigns a role to an existing member identified using its displayed index.
 */
public class AssignRoleCommand extends Command {

    public static final String COMMAND_WORD = "assign-role";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a role to the member identified by the index.\n"
            + "Parameters: INDEX (must be a positive integer) r/ROLE\n"
            + "Example: " + COMMAND_WORD + " 1 r/Treasurer";

    public static final String MESSAGE_ASSIGN_ROLE_SUCCESS = "Member %1$s assigned role: %2$s";
    public static final String MESSAGE_INVALID_INDEX = "Error: Invalid member index.";

    private final Index index;
    private final Role role;

    /**
     * Creates an AssignRoleCommand to assign the specified {@code Role} to a member
     */
    public AssignRoleCommand(Index index, Role role) {
        requireNonNull(index);
        requireNonNull(role);
        this.index = index;
        this.role = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<Role> updatedRoles = new HashSet<>(personToEdit.getRoles());
        updatedRoles.add(role);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                updatedRoles,
                personToEdit.getSubcom()
        );

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_ASSIGN_ROLE_SUCCESS,
                personToEdit.getName(), role.roleName), DisplayEntity.PERSON);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AssignRoleCommand)) {
            return false;
        }
        AssignRoleCommand otherCommand = (AssignRoleCommand) other;
        return index.equals(otherCommand.index)
                && role.equals(otherCommand.role);
    }

    @Override
    public int hashCode() {
        return index.hashCode() * 31 + role.hashCode();
    }
}
