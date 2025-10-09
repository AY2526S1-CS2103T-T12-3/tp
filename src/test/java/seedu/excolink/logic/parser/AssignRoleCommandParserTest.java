package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.AssignRoleCommand;
import seedu.excolink.model.role.Role;

public class AssignRoleCommandParserTest {

    private final AssignRoleCommandParser parser = new AssignRoleCommandParser();

    @Test
    public void parse_validArgs_returnsAssignRoleCommand() {
        // single role
        AssignRoleCommand expectedCommand = new AssignRoleCommand(Index.fromOneBased(1), new Role("Treasurer"));
        assertParseSuccess(parser, "1 r/Treasurer", expectedCommand);

        // role with spaces
        AssignRoleCommand expectedCommand2 = new AssignRoleCommand(Index.fromOneBased(2), new Role("Team Lead"));
        assertParseSuccess(parser, "2 r/Team Lead", expectedCommand2);
    }

    @Test
    public void parse_missingRole_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignRoleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1", expectedMessage);
    }

    @Test
    public void parse_missingIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignRoleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "r/Treasurer", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignRoleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "0 r/Treasurer", expectedMessage);
        assertParseFailure(parser, "-1 r/Treasurer", expectedMessage);
        assertParseFailure(parser, "abc r/Treasurer", expectedMessage);
    }

    @Test
    public void parse_invalidRole_failure() {
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, "1 r/Treasurer123", expectedMessage);
        assertParseFailure(parser, "1 r/Treasurer!", expectedMessage);
    }
}
