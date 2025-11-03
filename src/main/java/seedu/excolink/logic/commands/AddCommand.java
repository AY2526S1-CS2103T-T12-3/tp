package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_SUBCOM_NAME;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.model.subcom.exceptions.SubcomNotFoundException;
import seedu.excolink.ui.DisplayEntity;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to ExcoLink. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "[" + PREFIX_SUBCOM + "SUBCOMMITTEE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ROLE + "Team Lead";

    public static final String MESSAGE_SUCCESS = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This member already exists in ExcoLink";

    private final Person toEdit;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toEdit = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toEdit)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        Subcom modelSubcom;
        try {
            modelSubcom = model.findSubcom(toEdit.getSubcom());
        } catch (SubcomNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_SUBCOM_NAME);
        }

        Person toAdd = toEdit.assignSubcom(modelSubcom);

        model.addPerson(toAdd);
        model.setDisplayEntity(DisplayEntity.PERSON);
        String successMessage = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        if (toAdd.getPhone().hasNonNumericCharacters()) {
            successMessage += "\n" + Phone.MESSAGE_NON_NUMERIC_WARNING;
        }

        return new CommandResult(successMessage);
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

        AddCommand otherAddCommand = (AddCommand) other;
        return toEdit.equals(otherAddCommand.toEdit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toEdit)
                .toString();
    }
}
