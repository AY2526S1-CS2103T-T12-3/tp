package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.excolink.logic.commands.UnassignSubcomCommand;

public class UnassignSubcomCommandParserTest {
    private UnassignSubcomCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new UnassignSubcomCommandParser();
    }

    @Test
    public void parse_validArgs_returnsUnassignSubcomCommand() throws Exception {
        // basic valid case
        assertParseSuccess(parser, "1", new UnassignSubcomCommand(INDEX_FIRST));
        assertParseSuccess(parser, "2", new UnassignSubcomCommand(INDEX_SECOND));
    }

    @Test
    public void parse_validArgsExtraWhitespace_returnsCommand() throws Exception {
        // whitespace should be tolerated
        assertParseSuccess(parser, "  1  ", new UnassignSubcomCommand(INDEX_FIRST));

        // Tabs and spaces
        assertParseSuccess(parser, "\t2\t", new UnassignSubcomCommand(INDEX_SECOND));
    }
    @Test
    public void parse_noArgs_failure() {
        // Empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));

        // Whitespace only
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // Zero index
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));

        // Negative index
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));

        // Non-numeric index
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));

        // Decimal index
        assertParseFailure(parser, "1.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_failure() {
        // Extra text after valid index
        assertParseFailure(parser, "1 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));

        // Multiple indices
        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignSubcomCommand.MESSAGE_USAGE));
    }
}
