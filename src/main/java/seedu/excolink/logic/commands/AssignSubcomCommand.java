package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOMMITTEE;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.model.Model;

/**
 * Clears the address book.
 */
public class AssignSubcomCommand extends Command {

    public static final String COMMAND_WORD = "assign-subcom";
    public static final String MESSAGE_SUCCESS = "Subcommittee has been assigned!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign a subcommittee for a member "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_SUBCOMMITTEE + "SUBCOMMITTEE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBCOMMITTEE + "publicity";

    private Index index;
    private String subcommString;
    /**
     * @param index of the person in the filtered person list to edit
     * @param subcommString subcomm that the person is assigned to
     */
    public AssignSubcomCommand(Index index, String subcommString) {
        requireNonNull(index);
        requireNonNull(subcommString);
        this.index = index;
        this.subcommString = subcommString;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AssignSubcomCommand) {
            AssignSubcomCommand command = (AssignSubcomCommand) o;
            return command.index.equals(this.index) && command.subcommString.equals(this.subcommString);
        } else {
            return false;
        }
    }
}
