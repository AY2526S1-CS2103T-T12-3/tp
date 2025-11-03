package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_WRONG_DISPLAY_ENTITY_FOR_PERSON_COMMAND;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import java.util.List;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.model.subcom.exceptions.SubcomNotFoundException;
import seedu.excolink.ui.DisplayEntity;

/**
 * Clears the address book.
 */
public class AssignSubcomCommand extends Command {

    public static final String COMMAND_WORD = "assign-sc";
    public static final String MESSAGE_SUCCESS = "Assigned %1$s to subcommittee: %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign a subcommittee for a member "
            + "by the index number used in the displayed member list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_SUBCOM + "SUBCOMMITTEE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SUBCOM + "publicity";

    private Index index;
    private Subcom subcom;

    /**
     * @param index      of the person in the filtered person list to edit
     * @param subcom subcom that the person is assigned to
     */
    public AssignSubcomCommand(Index index, Subcom subcom) {
        requireNonNull(index);
        this.index = index;
        this.subcom = subcom;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getDisplayEntity() != DisplayEntity.PERSON) {
            throw new CommandException(MESSAGE_WRONG_DISPLAY_ENTITY_FOR_PERSON_COMMAND);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Subcom subcom;
        try {
            subcom = model.findSubcom(this.subcom);
        } catch (SubcomNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBCOM_NAME);
        }

        Person editedPerson = personToEdit.assignSubcom(subcom);
        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.getName(), subcom));
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
