package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.DeleteRoleCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.role.Role;

/**
 * Parses input arguments and creates a new DeleteRoleCommand object.
 */
public class DeleteRoleCommandParser implements Parser<DeleteRoleCommand> {

    @Override
    public DeleteRoleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE);

        if (argMultimap.getValue(PREFIX_ROLE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoleCommand.MESSAGE_USAGE));
        }

        try {
            String preamble = argMultimap.getPreamble().trim();
            Index index = ParserUtil.parseIndex(preamble);

            Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());

            return new DeleteRoleCommand(index, role);

        } catch (ParseException pe) {
            if (pe.getMessage().contains("Index")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteRoleCommand.MESSAGE_USAGE));
            } else {
                throw pe;
            }
        }
    }
}
