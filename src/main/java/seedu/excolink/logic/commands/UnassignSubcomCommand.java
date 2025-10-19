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
public class UnassignSubcomCommand extends Command {

    public static final String COMMAND_WORD = "unassign-subcom";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unassigns the member at the given INDEX from their subcommittee.\n"
            + "Parameters: INDEX (must be a positive Integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_SUCCESS = "Unassigned %1$s from subcommittee: %2$s";
    public static final String MESSAGE_MEMBER_NOT_IN_SUBCOM = "%1$s is not a member of subcommittee \"%2$s\".";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";


    private final Index targetIndex;

    /**
     * Create the UnassignSubcomCommand to unassign a member from their committee
     * @param targetIndex Index of the member to be unassigned
     */
    public UnassignSubcomCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        // Check if person is in the subcommittee
        if (!personToEdit.getSubcom().equals(Subcom.NOSUBCOM)) {
            throw new CommandException(String.format(MESSAGE_MEMBER_NOT_IN_SUBCOM,
                    personToEdit.getName().fullName));
        }

        // Create new person with no subcommittee
        Person editedPerson = personToEdit.removeFromSubcom();

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, personToEdit.getName().fullName),
                DisplayEntity.PERSON);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnassignSubcomCommand)) {
            return false;
        }

        UnassignSubcomCommand otherCommand = (UnassignSubcomCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex);
    }
}
