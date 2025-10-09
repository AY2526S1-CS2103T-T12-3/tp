package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.commands.AddSubcomCommand.MESSAGE_USAGE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.excolink.logic.commands.AddSubcomCommand;
import seedu.excolink.model.subcom.Subcom;

public class AddSubcomCommandParserTest {

    private final AddSubcomCommandParser parser = new AddSubcomCommandParser();

    @Test
    public void parse_validArgs_returnsAddSubcomCommand() {
        Subcom expectedSubcom = new Subcom("Publicity");
        String userInput = " " + PREFIX_SUBCOM + "Publicity";

        AddSubcomCommand expectedCommand = new AddSubcomCommand(expectedSubcom);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing PREFIX_SUBCOM
        String userInput = "Publicity";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyValue_throwsParseException() {
        String userInput = " " + PREFIX_SUBCOM; // prefix but no value
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        String userInput = "extra " + PREFIX_SUBCOM + "Publicity";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = " " + PREFIX_SUBCOM + "Publicity " + PREFIX_SUBCOM + "Logistics";
        // The parser internally calls verifyNoDuplicatePrefixesFor(), which throws ParseException.
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
