package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_WRONG_DISPLAY_ENTITY_FOR_PERSON_COMMAND;

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
 * Assigns a role to an existing member identified using its displayed index.
 */
public class AssignRoleCommand extends Command {

    public static final String COMMAND_WORD = "assign-role";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a role to the member identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) r/ROLE...\n"
            + "Example: " + COMMAND_WORD + " 1 r/Treasurer"
            + "Example: " + COMMAND_WORD + " 1 r/Team Lead r/Treasurer";

    public static final String MESSAGE_ASSIGN_ROLE_SUCCESS = "%1$s assigned role(s): %2$s";
    public static final String MESSAGE_DUPLICATE_ROLE = "%1$s has already been assigned role: %2$s";
    private final Index index;
    private final Set<Role> rolesToAdd;

    /**
     * Creates an AssignRoleCommand to assign the specified set of {@code Role} to a member
     */
    public AssignRoleCommand(Index index, Set<Role> roles) {
        requireNonNull(index);
        requireNonNull(roles);
        this.index = index;
        this.rolesToAdd = roles;
    }

    /**
     * Creates an AssignRoleCommand to assign the specified {@code Role} to a member
     */
    public AssignRoleCommand(Index index, Role role) {
        requireNonNull(index);
        requireNonNull(role);
        this.index = index;
        this.rolesToAdd = Set.of(role);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getDisplayEntity() != DisplayEntity.PERSON) {
            throw new CommandException(MESSAGE_WRONG_DISPLAY_ENTITY_FOR_PERSON_COMMAND);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());


        Set<Role> personRoles = new HashSet<>(personToEdit.getRoles());

        for (Role role: rolesToAdd) {
            if (!personRoles.add(role)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_ROLE, personToEdit.getName(), role));
            }
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personRoles,
                personToEdit.getSubcom()
        );

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_ASSIGN_ROLE_SUCCESS,
                personToEdit.getName(), Messages.formatRoles(rolesToAdd)));
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
                && rolesToAdd.equals(otherCommand.rolesToAdd);
    }

    @Override
    public int hashCode() {
        return index.hashCode() * 31 + rolesToAdd.hashCode();
    }
}
