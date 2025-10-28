package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.model.subcom.exceptions.SubcomNotFoundException;
import seedu.excolink.ui.DisplayEntity;

/**
 * Lists members in a subcommittee.
 */
public class ListSubcomMembersCommand extends Command {
    public static final String COMMAND_WORD = "list-sc-members";
    public static final String COMMAND_WORD_ALIAS = "list-scm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " Lists all persons who are members of "
            + "the specified Subcom (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: SUBCOM_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SUBCOM + " Publicity\n"
            + "Alternative format example: " + COMMAND_WORD_ALIAS + " " + PREFIX_SUBCOM + " Publicity";;

    public static final String MESSAGE_SUCCESS = "Listed all persons in subcommittee: %s";

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

        try {
            Subcom originalSubcom = model.findSubcom(subcomToList);
            model.updateFilteredPersonList(person -> person.getSubcom().equals(subcomToList));
            return new CommandResult(String.format(MESSAGE_SUCCESS, originalSubcom.toString()), DisplayEntity.PERSON);
        } catch (SubcomNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBCOM_NAME);
        }
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
