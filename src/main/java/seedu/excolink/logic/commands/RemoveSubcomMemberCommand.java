package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.ui.DisplayEntity;

/**
 * Remove a member from a subcom
 */
public class RemoveSubcomMemberCommand extends Command {

    public static final String COMMAND_WORD = "remove-subcom-member";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the member at the given INDEX from the specified subcommittee.\n"
            + "Parameters: INDEX (must be a positive Integer)"
            + "sc/SUBCOM_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 sc/Finance";

    public static final String MESSAGE_REMOVE_SUCCESS = "Removed %1$s from subcommittee: %2$s";
    public static final String MESSAGE_SUBCOM_NOT_FOUND = "Subcommittee named \"%1$s\" not found.";
    public static final String MESSAGE_MEMBER_NOT_IN_SUBCOM = "%1$s is not a member of subcommittee \"%2$s\".";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";


    private final Index targetIndex;
    private final String subcomName;

    /**
     * Create the RemoveSubcomMemberCommand
     * @param targetIndex Index of the member to be removed
     * @param subcomName Name of the subcommittee
     */
    public RemoveSubcomMemberCommand(Index targetIndex, String subcomName) {
        requireNonNull(targetIndex);
        requireNonNull(subcomName);
        this.targetIndex = targetIndex;
        this.subcomName = subcomName.trim();
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        // Check if the subcommittee exists
        Subcom subcomToRemoveFrom = new Subcom(subcomName);
        if (!model.hasSubcom(subcomToRemoveFrom)) {
            throw new CommandException(String.format(MESSAGE_SUBCOM_NOT_FOUND, subcomName));
        }

        // Check if person is in the subcommittee
        if (!personToEdit.getSubcom().equals(subcomToRemoveFrom)) {
            throw new CommandException(String.format(MESSAGE_MEMBER_NOT_IN_SUBCOM,
                    personToEdit.getName().fullName, subcomName));
        }

        // Create new person with no subcommittee
        Person editedPerson = personToEdit.removeFromSubcom();

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, personToEdit.getName().fullName, subcomName),
                DisplayEntity.PERSON);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveSubcomMemberCommand)) {
            return false;
        }

        RemoveSubcomMemberCommand otherCommand = (RemoveSubcomMemberCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && subcomName.equals(otherCommand.subcomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, subcomName);
    }
}
