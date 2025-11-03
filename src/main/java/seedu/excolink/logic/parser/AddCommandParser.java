package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.Messages.MESSAGE_MISSING_EMAIL;
import static seedu.excolink.logic.Messages.MESSAGE_MISSING_NAME;
import static seedu.excolink.logic.Messages.MESSAGE_MISSING_PHONE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import java.util.Set;
import java.util.stream.Stream;

import seedu.excolink.logic.commands.AddCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.person.Email;
import seedu.excolink.model.person.Name;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ROLE, PREFIX_SUBCOM);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_NAME, AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_PHONE, AddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_EMAIL, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_SUBCOM);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Role> roleList = ParserUtil.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));
        Person person;
        if (argMultimap.getValue(PREFIX_SUBCOM).isPresent()) {
            person = new Person(name, phone, email, roleList,
                    ParserUtil.parseSubcom(argMultimap.getValue(PREFIX_SUBCOM).get()));
        } else {
            person = new Person(name, phone, email, roleList, Subcom.NOSUBCOM);
        }
        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
