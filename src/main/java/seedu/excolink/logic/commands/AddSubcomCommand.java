package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.parser.CliSyntax.*;

import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.subcom.Subcom;

public class AddSubcomCommand extends Command {
    public static final String COMMAND_WORD = "add-subcom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a subcommittee to ExcoLink. "
            + "Parameters: "
            + PREFIX_SUBCOM + "SUBCOM-NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBCOM + "Publicity";

    public static final String MESSAGE_SUCCESS = "New subcommittee added: %1$s";
    public static final String MESSAGE_DUPLICATE_SUBCOM = "This subcommittee already exists.";

    private final Subcom toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddSubcomCommand(Subcom subcom) {
        requireNonNull(subcom);
        toAdd = subcom;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // TODO: Check for duplicate subcom
        // TODO: Add subcom to model

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddSubcomCommand)) {
            return false;
        }

        AddSubcomCommand otherAddSubcomCommand = (AddSubcomCommand) other;
        return toAdd.equals(otherAddSubcomCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
