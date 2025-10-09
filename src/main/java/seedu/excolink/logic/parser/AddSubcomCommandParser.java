package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import java.util.stream.Stream;

import seedu.excolink.logic.commands.AddSubcomCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.subcom.Subcom;

/**
 * Parses input arguments and creates a new AddSubcomCommand object
 */
public class AddSubcomCommandParser implements Parser<AddSubcomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSubcomCommand
     * and returns an AddSubcomCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubcomCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBCOM);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBCOM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubcomCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBCOM);
        Subcom subcom = ParserUtil.parseSubcom(argMultimap.getValue(PREFIX_SUBCOM).get());

        return new AddSubcomCommand(subcom);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
