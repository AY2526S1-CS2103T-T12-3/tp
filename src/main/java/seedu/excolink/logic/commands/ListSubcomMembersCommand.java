package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.ui.DisplayEntity;

/**
 * Lists members in a  subcommittee.
 */
public class ListSubcomMembersCommand extends Command {
    public static final String COMMAND_WORD = "list-subcom-members";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " Lists all persons who are members of "
            + "the specified Subcom (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: SUBCOM_NAME\n"
            + "Example: " + COMMAND_WORD + " Publicity";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_INVALID_SUBCOM = "This subcommittee does not exist.";

    private final Subcom subcomToList;

    /**
     * Creates a ListSubcomMembersCommand to list the members in {@code Subcom}
     */
    public ListSubcomMembersCommand(Subcom subcom) {
        requireNonNull(subcom);
        this.subcomToList = subcom;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasSubcom(this.subcomToList)) {
            throw new CommandException(MESSAGE_INVALID_SUBCOM);
        }
        /* TODO: Included after addition of hasSubcom() and getSubcom()

        model.updateFilteredPersonList(person -> person.getSubcom().equals(subcomToList));

        */
        return new CommandResult(String.format(MESSAGE_SUCCESS, subcomToList.toString()), DisplayEntity.PERSON);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListSubcomMembersCommand)) {
            return false;
        }

        ListSubcomMembersCommand otherListSubcomMembersCommand = (ListSubcomMembersCommand) other;
        return subcomToList.equals(otherListSubcomMembersCommand.subcomToList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subcomToList", subcomToList)
                .toString();
    }
}

