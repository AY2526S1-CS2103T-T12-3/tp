package seedu.excolink.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.RemoveSubcomMemberCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

public class RemoveSubcomMemberCommandParserTest {
    private RemoveSubcomMemberCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new RemoveSubcomMemberCommandParser();
    }

    @Test
    public void parse_validArgs_returnsRemoveSubcomMemberCommand() throws Exception {
        // basic valid case
        RemoveSubcomMemberCommand expected =
                new RemoveSubcomMemberCommand(Index.fromOneBased(1), "Finance");
        assertEquals(expected, parser.parse("1 sc/Finance"));
    }

    @Test
    public void parse_validArgsExtraWhitespace_returnsCommand() throws Exception {
        // whitespace should be tolerated
        RemoveSubcomMemberCommand expected =
                new RemoveSubcomMemberCommand(Index.fromOneBased(2), "Events");
        assertEquals(expected, parser.parse("   2    sc/Events   "));
    }

    @Test
    public void parse_missingSubcomPrefix_throwsParseException() {
        // missing sc/ prefix
        assertThrows(ParseException.class, () -> parser.parse("1 Finance"),
                "Expected parse to fail when subcommittee prefix is missing");
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        // missing index
        assertThrows(ParseException.class, () -> parser.parse("sc/Finance"),
                "Expected parse to fail when index is missing");
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // index must be positive 1-based
        assertThrows(ParseException.class, () -> parser.parse("0 sc/Finance"),
                "Expected parse to fail when index is zero (invalid)");
        assertThrows(ParseException.class, () -> parser.parse("-1 sc/Finance"),
                "Expected parse to fail when index is negative (invalid)");
        assertThrows(ParseException.class, () -> parser.parse("abc sc/Finance"),
                "Expected parse to fail when index is non-numeric");
    }

    @Test
    public void parse_emptySubcomName_throwsParseException() {
        // empty subcommittee name after prefix
        assertThrows(ParseException.class, () -> parser.parse("1 sc/"),
                "Expected parse to fail when subcommittee name is empty");
        assertThrows(ParseException.class, () -> parser.parse("1 sc/   "),
                "Expected parse to fail when subcommittee name is only whitespace");
    }
}
