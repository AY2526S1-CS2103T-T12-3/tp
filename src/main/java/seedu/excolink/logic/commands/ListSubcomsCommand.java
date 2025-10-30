package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.excolink.model.Model;
import seedu.excolink.ui.DisplayEntity;

/**
 * Lists all subcoms in the address book to the user.
 */
public class ListSubcomsCommand extends Command {

    public static final String COMMAND_WORD = "list-sc";

    public static final String MESSAGE_SUCCESS = "Listed all subcoms";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setDisplayEntity(DisplayEntity.SUBCOM);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
