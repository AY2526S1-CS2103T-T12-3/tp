package seedu.excolink.model.subcom;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.commons.util.AppUtil.checkArgument;

/**
 * Represents a subcommittee in ExcoLink.
 * Guarantees: immutable
 */
public class Subcom {
    public static final String MESSAGE_CONSTRAINTS = "Subcommitee names can take any values, and it should not be blank";

    /*
     * The first character of the subcommittee name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String subcomName;

    /**
     * Constructs a {@code Subcom}.
     *
     * @param subcomName A valid subcommittee name.
     */
    public Subcom(String subcomName) {
        requireNonNull(subcomName);
        checkArgument(isValidSubcom(subcomName), MESSAGE_CONSTRAINTS);
        this.subcomName = subcomName;
    }

    /**
     * Returns true if a given string is a valid subcommittee name.
     */
    public static boolean isValidSubcom(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Subcom)) {
            return false;
        }

        Subcom otherSubcom = (Subcom) other;
        return subcomName.equals(otherSubcom.subcomName);
    }

    @Override
    public int hashCode() {
        return subcomName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + subcomName + ']';
    }
}
