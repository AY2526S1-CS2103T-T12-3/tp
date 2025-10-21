package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

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
 * Removes a role of an existing member identified using its displayed index.
 */
public class DeleteRoleCommand extends Command {

    public static final String COMMAND_WORD = "delete-role";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a role from the member identified by the index.\n"
            + "Parameters: INDEX (must be a positive integer) r/ROLE\n"
            + "Example: " + COMMAND_WORD + " 1 r/Team Lead";

    public static final String MESSAGE_SUCCESS = "%1$s unassigned role: %2$s";
    public static final String MESSAGE_ROLE_NOT_FOUND = "Member does not have the role '%1$s'";

    private final Index index;
    private final Role role;

    /**
     * Creates a DeleteRoleCommand to remove the specified {@code Role} of a member
     */
    public DeleteRoleCommand(Index index, Role role) {
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
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Loop through roles to find exact match
        boolean roleFound = false;
        Set<Role> updatedRoles = new HashSet<>();
        for (Role existingRole : personToEdit.getRoles()) {
            if (existingRole.equals(role)) {
                roleFound = true;
            } else {
                updatedRoles.add(existingRole);
            }
        }

        if (!roleFound) {
            throw new CommandException(String.format(MESSAGE_ROLE_NOT_FOUND, role.roleName));
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                updatedRoles,
                personToEdit.getSubcom()
        );

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, personToEdit.getName(), role.roleName),
                DisplayEntity.PERSON
        );
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
