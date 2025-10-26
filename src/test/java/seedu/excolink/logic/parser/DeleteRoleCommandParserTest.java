package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.commands.DeleteRoleCommand;
import seedu.excolink.model.role.Role;

public class DeleteRoleCommandParserTest {

    private final DeleteRoleCommandParser parser = new DeleteRoleCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRoleCommand() {
        // Single-word role
        DeleteRoleCommand expectedCommand = new DeleteRoleCommand(Index.fromOneBased(1),
                new Role("treasurer"));
        assertParseSuccess(parser, "1 r/treasurer", expectedCommand);

        // Multi-word role
        DeleteRoleCommand expectedCommand2 = new DeleteRoleCommand(Index.fromOneBased(2),
                new Role("team lead"));
        assertParseSuccess(parser, "2 r/team lead", expectedCommand2);
    }

    @Test
    public void parse_roleCaseInsensitive_convertsToLowercase() {
        // Mixed case role
        DeleteRoleCommand expectedCommand = new DeleteRoleCommand(Index.fromOneBased(1),
                new Role("treasurer"));
        assertParseSuccess(parser, "1 r/TREASURER", expectedCommand);
        assertParseSuccess(parser, "1 r/TreAsUrer", expectedCommand);

        // Role with spaces and mixed case
        DeleteRoleCommand expectedCommand2 = new DeleteRoleCommand(Index.fromOneBased(2),
                new Role("team lead"));
        assertParseSuccess(parser, "2 r/Team Lead", expectedCommand2);
    }

    @Test
    public void parse_missingRole_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1", expectedMessage);
    }

    @Test
    public void parse_missingIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "r/Treasurer", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "0 r/Treasurer", expectedMessage);
        assertParseFailure(parser, "-1 r/Treasurer", expectedMessage);
        assertParseFailure(parser, "abc r/Treasurer", expectedMessage);
    }

    @Test
    public void parse_invalidRole_failure() {
        // Expect Role.MESSAGE_CONSTRAINTS
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, "1 r/Treasurer123", expectedMessage);
        assertParseFailure(parser, "1 r/Treasurer!", expectedMessage);
    }

    @Test
    public void parse_emptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "   r/Treasurer", expectedMessage);
    }
}
