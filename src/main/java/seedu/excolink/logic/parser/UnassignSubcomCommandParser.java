package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.UnassignSubcomCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveSubcomMemberCommand object
 */
public class UnassignSubcomCommandParser implements Parser<UnassignSubcomCommand> {

    @Override
    public UnassignSubcomCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args.trim());
            return new UnassignSubcomCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE), pe);
        }
    }
}
