package seedu.excolink.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.RemoveSubcomMemberCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveSubcomMemberCommand object
 */
public class RemoveSubcomMemberCommandParser implements Parser<RemoveSubcomMemberCommand>{
    public RemoveSubcomMemberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBCOM);

        String preamble = argMultimap.getPreamble();
        if (preamble.isEmpty() || !argMultimap.getValue(PREFIX_SUBCOM).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSubcomMemberCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSubcomMemberCommand.MESSAGE_USAGE, pe));
        }

        String subcomName = argMultimap.getValue(PREFIX_SUBCOM).get().trim();
        if (subcomName.isEmpty()) {
            throw new ParseException("Subcommittee name cannot be empty");
        }

        return new RemoveSubcomMemberCommand(index, subcomName);
    }
}
