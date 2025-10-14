package seedu.excolink.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.ui.DisplayEntity;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** What kind of entity should be displayed */
    private final DisplayEntity displayEntity;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, DisplayEntity displayEntity) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayEntity = displayEntity;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields, displayEntity defaulted to PERSON.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayEntity = DisplayEntity.PERSON;
    }

        /**
         * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code DisplayEntity},
         * and other fields set to their default value.
         */
    public CommandResult(String feedbackToUser, DisplayEntity displayEntity) {
        this(feedbackToUser, false, false, displayEntity);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, DisplayEntity.PERSON);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public DisplayEntity displayEntity() {
        return displayEntity;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
