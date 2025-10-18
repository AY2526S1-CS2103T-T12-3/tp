package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.ui.DisplayEntity;

/**
 * Deletes a subcom identified using its displayed index from the app.
 */
public class DeleteSubcomCommand extends Command {

    public static final String COMMAND_WORD = "delete-subcom";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the subcommittee identified by the index number used in the displayed subcommittee list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SUBCOM_SUCCESS = "Deleted Subcommittee: %1$s";

    private final Index targetIndex;

    public DeleteSubcomCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Subcom> subcomList = model.getSubcomList();

        if (targetIndex.getZeroBased() >= subcomList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBCOM_DISPLAYED_INDEX);
        }

        Subcom subcomToDelete = subcomList.get(targetIndex.getZeroBased());
        // Delete the subcommittee and remove all members from the deleted subcommittee
        model.deleteSubcom(subcomToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_SUBCOM_SUCCESS, subcomToDelete),
                DisplayEntity.SUBCOM);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteSubcomCommand)) {
            return false;
        }

        DeleteSubcomCommand otherDeleteSubcomCommand = (DeleteSubcomCommand) other;
        return targetIndex.equals(otherDeleteSubcomCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
