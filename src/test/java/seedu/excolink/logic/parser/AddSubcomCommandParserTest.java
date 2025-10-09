package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_SUBCOM_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.excolink.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.excolink.logic.commands.CommandTestUtil.SUBCOM_DESC_PUBLICITY;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_SUBCOM_PUBLICITY;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.AddSubcomCommand;
import seedu.excolink.model.subcom.Subcom;

public class AddSubcomCommandParserTest {

    private final AddSubcomCommandParser parser = new AddSubcomCommandParser();

    @Test
    public void parse_validArgs_success() {
        Subcom expectedSubcom = new Subcom(VALID_SUBCOM_PUBLICITY);

        // whitespace preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SUBCOM_DESC_PUBLICITY,
                new AddSubcomCommand(expectedSubcom));

        // another valid subcom
        Subcom anotherSubcom = new Subcom(VALID_SUBCOM_PUBLICITY);
        assertParseSuccess(parser, SUBCOM_DESC_PUBLICITY,
                new AddSubcomCommand(anotherSubcom));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validCommand = SUBCOM_DESC_PUBLICITY;

        // multiple subcom prefixes
        assertParseFailure(parser, SUBCOM_DESC_PUBLICITY + SUBCOM_DESC_PUBLICITY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBCOM));

        // invalid followed by valid
        assertParseFailure(parser, INVALID_SUBCOM_DESC + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBCOM));

        // valid followed by invalid
        assertParseFailure(parser, validCommand + INVALID_SUBCOM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBCOM));
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubcomCommand.MESSAGE_USAGE);

        // missing subcom prefix
        assertParseFailure(parser, VALID_SUBCOM_PUBLICITY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid (empty) subcom name
        assertParseFailure(parser, INVALID_SUBCOM_DESC, Subcom.MESSAGE_CONSTRAINTS);

        // preamble not empty
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + SUBCOM_DESC_PUBLICITY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubcomCommand.MESSAGE_USAGE));
    }
}
