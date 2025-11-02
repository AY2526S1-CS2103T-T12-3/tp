package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.excolink.logic.Messages.MESSAGE_WRONG_DISPLAY_ENTITY_FOR_PERSON_COMMAND;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.Messages;
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
            + ": Deletes one or more roles from the member identified by the index.\n"
            + "Parameters: INDEX (must be a positive integer) r/ROLE...\n"
            + "Example: " + COMMAND_WORD + " 1 r/Team Lead"
            + "Example: " + COMMAND_WORD + " 1 r/Team Lead r/Treasurer";

    public static final String MESSAGE_SUCCESS = "%1$s unassigned role: %2$s";
    public static final String MESSAGE_ROLE_NOT_FOUND = "Member does not have the role '%1$s'";

    private final Index index;
    private final Set<Role> rolesToDelete;

    /**
     * Creates a DeleteRoleCommand to remove the specified {@code Role} of a member
     */
    public DeleteRoleCommand(Index index, Role role) {
        requireNonNull(index);
        requireNonNull(role);
        this.index = index;
        this.rolesToDelete = Set.of(role);
    }

    /**
     * Creates a DeleteRoleCommand to remove the specified set of {@code Role} of a member
     */
    public DeleteRoleCommand(Index index, Set<Role> roles) {
        requireNonNull(index);
        requireNonNull(roles);
        this.index = index;
        this.rolesToDelete = roles;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getDisplayEntity() != DisplayEntity.PERSON) {
            throw new CommandException(MESSAGE_WRONG_DISPLAY_ENTITY_FOR_PERSON_COMMAND);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Set<Role> existingRoles = personToEdit.getRoles();
        Set<Role> updatedRoles = new HashSet<>(existingRoles);
        List<String> missingRolesString = new ArrayList<>();

        for (Role roleToDelete: rolesToDelete) {
            if (!existingRoles.contains(roleToDelete)) {
                missingRolesString.add(roleToDelete.toString());
            }
            updatedRoles.remove(roleToDelete);
        }

        if (!missingRolesString.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_ROLE_NOT_FOUND,
                    String.join(", ", missingRolesString)));
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                updatedRoles,
                personToEdit.getSubcom()
        );

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(),
                Messages.formatRoles(rolesToDelete)));
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
                && rolesToDelete.equals(otherCommand.rolesToDelete);
    }

    @Override
    public int hashCode() {
        return index.hashCode() * 31 + rolesToDelete.hashCode();
    }
}
