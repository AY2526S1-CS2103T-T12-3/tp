package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.DeleteSubcomCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSubcomCommand object
 */
public class DeleteSubcomCommandParser implements Parser<DeleteSubcomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSubcomCommand
     * and returns a DeleteSubcomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSubcomCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteSubcomCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSubcomCommand.MESSAGE_USAGE), pe);
        }
    }

}
