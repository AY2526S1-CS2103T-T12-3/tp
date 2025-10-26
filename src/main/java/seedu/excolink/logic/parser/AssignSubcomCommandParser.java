package seedu.excolink.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.AssignSubcomCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignSubcomCommand object
 */
public class AssignSubcomCommandParser implements Parser<AssignSubcomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AssignSubcomCommand
     * and returns a SubcomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignSubcomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBCOM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignSubcomCommand.MESSAGE_USAGE),
                    pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBCOM);
        String subcom;
        if (argMultimap.getValue(PREFIX_SUBCOM).isPresent()) {
            subcom = argMultimap.getValue(PREFIX_SUBCOM).get();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignSubcomCommand.MESSAGE_USAGE));
        }

        return new AssignSubcomCommand(index, subcom);
    }
}
