package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.excolink.logic.commands.AssignSubcomCommand;

/**
 * Unit tests for {@code AssignSubcomCommandParser}.
 * <p>
 * This class verifies the correctness of {@link AssignSubcomCommandParser}
 * by ensuring that valid inputs are parsed successfully into
 * {@link AssignSubcomCommand} objects, while invalid inputs are properly
 * rejected with appropriate error messages.
 */
public class AssignSubcomCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignSubcomCommand.MESSAGE_USAGE);

    private static final String VALID_SUBCOM = "sc/Marketing";
    private static final String INVALID_INDEX = "-1";
    private static final String DUPLICATE_SUBCOM = "sc/Marketing sc/Finance";

    private final AssignSubcomCommandParser parser = new AssignSubcomCommandParser();

    /**
     * Tests that a valid command string with a valid index and subcommittee
     * parses successfully into an {@link AssignSubcomCommand}.
     */
    @Test
    public void parse_validArgs_returnsAssignSubcomCommand() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + VALID_SUBCOM;
        AssignSubcomCommand expectedCommand =
                new AssignSubcomCommand(INDEX_FIRST_PERSON, "Marketing");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests that the parser fails when the index is missing.
     * Expected: {@code MESSAGE_INVALID_COMMAND_FORMAT}.
     */
    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, VALID_SUBCOM, MESSAGE_INVALID_FORMAT);
    }

    /**
     * Tests that the parser fails when the subcommittee prefix/value is missing.
     * Expected: {@code MESSAGE_INVALID_COMMAND_FORMAT}.
     */
    @Test
    public void parse_missingSubcom_failure() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    /**
     * Tests that the parser fails when the index provided is invalid.
     * Includes cases with negative, zero, and non-numeric indices.
     */
    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, INVALID_INDEX + " " + VALID_SUBCOM, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 " + VALID_SUBCOM, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "one " + VALID_SUBCOM, MESSAGE_INVALID_FORMAT);
    }

    /**
     * Tests that the parser fails when multiple {@code sc/} prefixes are provided.
     * This helps ensure only one subcommittee value is allowed per command.
     */
    @Test
    public void parse_duplicateSubcomPrefix_failure() {
        assertParseFailure(parser, "1 " + DUPLICATE_SUBCOM,
                "Multiple values specified for the following single-valued field(s): sc/");
    }

    /**
     * Tests that the parser fails when there is extraneous text before
     * the valid arguments (invalid preamble).
     */
    @Test
    public void parse_extraPreamble_failure() {
        assertParseFailure(parser, "1 randomtext " + VALID_SUBCOM, MESSAGE_INVALID_FORMAT);
    }
}
