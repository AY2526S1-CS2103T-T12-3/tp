package seedu.excolink.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;

import org.junit.jupiter.api.Test;

import seedu.excolink.logic.commands.ListSubcomMembersCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.subcom.Subcom;

class ListSubcomMembersCommandParserTest {

    private final ListSubcomMembersCommandParser parser = new ListSubcomMembersCommandParser();

    @Test
    void parse_validArgs_returnsListSubcomMembersCommand() throws Exception {
        String input = " " + PREFIX_SUBCOM + "Publicity";
        ListSubcomMembersCommand expectedCommand = new ListSubcomMembersCommand(new Subcom("Publicity"));
        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    void parse_missingSubcomPrefix_throwsParseException() {
        String input = "Publicity";
        assertThrows(ParseException.class, () -> parser.parse(input),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListSubcomMembersCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_extraPreamble_throwsParseException() {
        String input = "extra " + PREFIX_SUBCOM + "Publicity";
        ParseException e = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListSubcomMembersCommand.MESSAGE_USAGE),
                e.getMessage());
    }

    @Test
    void parse_emptySubcom_throwsParseException() {
        String input = " " + PREFIX_SUBCOM;
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    void parse_duplicatePrefix_throwsParseException() {
        String input = " " + PREFIX_SUBCOM + "Publicity " + PREFIX_SUBCOM + "Welfare";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
