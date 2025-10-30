package seedu.excolink.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.excolink.logic.parser.Prefix;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.role.Role;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_SUBCOM_DISPLAYED_INDEX = "The subcommittee index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_SUBCOM_NAME = "The subcommittee provided does not exist";
    public static final String MESSAGE_WRONG_DISPLAY_ENTITY_FOR_PERSON_COMMAND =
            "This command cannot be used in the current view. Please switch to the person list first.";
    public static final String MESSAGE_WRONG_DISPLAY_ENTITY_FOR_SUBCOM_COMMAND =
            "This command cannot be used in the current view. Please switch to the subcom list first.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Roles: ");
        person.getRoles().forEach(builder::append);
        builder.append("; Subcom: ").append(person.getSubcom());
        return builder.toString();
    }

    /**
     * Formats a set of {@code roles} for display to the user.
     */
    public static String formatRoles(Set<Role> roles) {
        final StringBuilder builder = new StringBuilder();
        roles.forEach(builder::append);
        return builder.toString();
    }
}
