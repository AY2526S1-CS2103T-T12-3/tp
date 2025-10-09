package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.AssignRoleCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.role.Role;

/**
 * Parses input arguments and creates a new AssignRoleCommand object.
 */
public class AssignRoleCommandParser implements Parser<AssignRoleCommand> {

    @Override
    public AssignRoleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE);

        if (argMultimap.getValue(PREFIX_ROLE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignRoleCommand.MESSAGE_USAGE));
        }

        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignRoleCommand.MESSAGE_USAGE));
        }
        
        Index index;
        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignRoleCommand.MESSAGE_USAGE), pe);
        }

        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());

        return new AssignRoleCommand(index, role);
    }
}
