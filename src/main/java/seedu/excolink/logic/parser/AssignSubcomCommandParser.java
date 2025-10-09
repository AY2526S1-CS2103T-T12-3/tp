package seedu.excolink.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOMMITTEE;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.AssignSubcomCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

public class AssignSubcomCommandParser implements Parser<AssignSubcomCommand> {

    public AssignSubcomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBCOMMITTEE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignSubcomCommand.MESSAGE_USAGE),
                    pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBCOMMITTEE);
        return new AssignSubcomCommand(index, argMultimap.getValue(PREFIX_SUBCOMMITTEE).get());
    }
}
