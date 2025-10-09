package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;


import seedu.excolink.logic.commands.ListSubcomMembersCommand;

import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.subcom.Subcom;

/**
 * Parses input arguments and creates a new ListSubcomMembersCommand object
 */
public class ListSubcomMembersCommandParser implements Parser<ListSubcomMembersCommand> {

    @Override
    public ListSubcomMembersCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBCOM);

        if (argMultimap.getValue(PREFIX_SUBCOM).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListSubcomMembersCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListSubcomMembersCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBCOM);

        Subcom subcom = ParserUtil.parseSubcom(argMultimap.getValue(PREFIX_SUBCOM).get());
        return new ListSubcomMembersCommand(subcom);

    }
}
