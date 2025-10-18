package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import java.util.List;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.ui.DisplayEntity;

/**
 * Clears the address book.
 */
public class AssignSubcomCommand extends Command {

    public static final String COMMAND_WORD = "assign-subcom";
    public static final String MESSAGE_SUCCESS = "Assigned %1$s to subcommittee: %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign a subcommittee for a member "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_SUBCOM + "SUBCOMMITTEE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBCOM + "publicity";

    private Index index;
    private Subcom subcom;
    /**
     * @param index of the person in the filtered person list to edit
     * @param subcom subcomm that the person is assigned to
     */
    public AssignSubcomCommand(Index index, Subcom subcom) {
        requireNonNull(index);
        requireNonNull(subcom);
        this.index = index;
        this.subcom = subcom;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (!model.hasSubcom(subcom)) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBCOM_NAME);
        }
        Person editedPerson = createNewPerson(personToEdit, subcom);
        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.getName(), subcom.getName()),
                DisplayEntity.PERSON);
    }

    private Person createNewPerson(Person personToEdit, Subcom subcom) {
        return new Person(personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getRoles(),
                subcom);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AssignSubcomCommand) {
            AssignSubcomCommand command = (AssignSubcomCommand) o;
            return command.index.equals(this.index) && command.subcom.equals(this.subcom);
        } else {
            return false;
        }
    }
}
