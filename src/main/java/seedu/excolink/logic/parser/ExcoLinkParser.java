package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.excolink.commons.core.LogsCenter;
import seedu.excolink.logic.commands.AddCommand;
import seedu.excolink.logic.commands.AddSubcomCommand;
import seedu.excolink.logic.commands.AssignRoleCommand;
import seedu.excolink.logic.commands.AssignSubcomCommand;
import seedu.excolink.logic.commands.ClearCommand;
import seedu.excolink.logic.commands.Command;
import seedu.excolink.logic.commands.DeleteCommand;
import seedu.excolink.logic.commands.EditCommand;
import seedu.excolink.logic.commands.ExitCommand;
import seedu.excolink.logic.commands.FindCommand;
import seedu.excolink.logic.commands.HelpCommand;
import seedu.excolink.logic.commands.ListCommand;
import seedu.excolink.logic.commands.RemoveSubcomMemberCommand;
import seedu.excolink.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ExcoLinkParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ExcoLinkParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AssignSubcomCommand.COMMAND_WORD:
            return new AssignSubcomCommandParser().parse(arguments);

        case AddSubcomCommand.COMMAND_WORD:
            return new AddSubcomCommandParser().parse(arguments);

        case AssignRoleCommand.COMMAND_WORD:
            return new AssignRoleCommandParser().parse(arguments);

        case RemoveSubcomMemberCommand.COMMAND_WORD:
            return new RemoveSubcomMemberCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
