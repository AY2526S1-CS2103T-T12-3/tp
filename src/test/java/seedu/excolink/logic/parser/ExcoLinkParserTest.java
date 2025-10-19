package seedu.excolink.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.excolink.testutil.Assert.assertThrows;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.excolink.logic.commands.AddCommand;
import seedu.excolink.logic.commands.AssignSubcomCommand;
import seedu.excolink.logic.commands.ClearCommand;
import seedu.excolink.logic.commands.DeleteCommand;
import seedu.excolink.logic.commands.DeleteSubcomCommand;
import seedu.excolink.logic.commands.EditCommand;
import seedu.excolink.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.excolink.logic.commands.ExitCommand;
import seedu.excolink.logic.commands.FindCommand;
import seedu.excolink.logic.commands.HelpCommand;
import seedu.excolink.logic.commands.ListCommand;
import seedu.excolink.logic.commands.ListSubcomMembersCommand;
import seedu.excolink.logic.commands.ListSubcomsCommand;
import seedu.excolink.logic.commands.UnassignSubcomCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.person.NameContainsKeywordsPredicate;
import seedu.excolink.model.person.Person;
import seedu.excolink.testutil.EditPersonDescriptorBuilder;
import seedu.excolink.testutil.PersonBuilder;
import seedu.excolink.testutil.PersonUtil;

public class ExcoLinkParserTest {

    private final ExcoLinkParser parser = new ExcoLinkParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        System.out.println(PersonUtil.getAddCommand(person));
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listSubcoms() throws Exception {
        assertTrue(parser.parseCommand(ListSubcomsCommand.COMMAND_WORD) instanceof ListSubcomsCommand);
        assertTrue(parser.parseCommand(ListSubcomsCommand.COMMAND_WORD + " 3") instanceof ListSubcomsCommand);
    }

    @Test
    public void parseCommand_assignSubcom() throws Exception {
        assertTrue((parser.parseCommand(AssignSubcomCommand.COMMAND_WORD + " 1 sc/SUBCOMMITTEE")
                instanceof AssignSubcomCommand));
    }

    @Test
    public void parseCommand_removeSubcomMember() throws Exception {
        UnassignSubcomCommand command = (UnassignSubcomCommand) parser.parseCommand(
                UnassignSubcomCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " sc/Finance");
        assertEquals(new UnassignSubcomCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_listSubcomMembers() throws Exception {
        assertTrue(parser.parseCommand(ListSubcomMembersCommand.COMMAND_WORD + " sc/SUBCOMMITEE")
                instanceof ListSubcomMembersCommand);
    }

    @Test
    public void parseCommand_deleteSubcom() throws Exception {
        DeleteSubcomCommand command = (DeleteSubcomCommand) parser.parseCommand(
                DeleteSubcomCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteSubcomCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
