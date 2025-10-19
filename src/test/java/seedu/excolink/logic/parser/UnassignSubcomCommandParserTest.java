package seedu.excolink.logic.parser;

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

}
