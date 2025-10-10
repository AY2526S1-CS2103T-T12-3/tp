package seedu.excolink.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.excolink.logic.commands.RemoveSubcomMemberCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveSubcomMemberCommand object
 */
public class RemoveSubcomMemberCommandParser implements Parser<RemoveSubcomMemberCommand>{
    public RemoveSubcomMemberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        
    }
}
