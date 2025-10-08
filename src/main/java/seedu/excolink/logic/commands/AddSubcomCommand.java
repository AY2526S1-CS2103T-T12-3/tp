package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.parser.CliSyntax.*;

import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.person.Person;

public class AddSubcomCommand extends Command {
    public static final String COMMAND_WORD = "add-subcom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a subcommittee to ExcoLink. "
            + "Parameters: "
            + PREFIX_SUBCOM + "SUBCOM-NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBCOM + "Publicity";

    public static final String MESSAGE_SUCCESS = "New subcommittee added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This subcommittee already exists.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddSubcomCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

//        AddCommand otherAddCommand = (AddCommand) other;
//        return toAdd.equals(otherAddCommand.toAdd);
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
