package seedu.excolink.model.subcom;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.commons.util.AppUtil.checkArgument;

/**
 * Represents a subcommittee in ExcoLink.
 * Guarantees: immutable
 */
public class Subcom {
    public static final String MESSAGE_CONSTRAINTS = "Subcommittee names can take any values, "
            + "and it should not be blank";

    /*
     * The first character of the subcommittee name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\S.*";
    public static final Subcom NOSUBCOM = new NoSubcom();
    public static final String NOSUBCOM_STRING = "None";

    public final String subcomName;
    public final String value;


    /**
     * Constructs a {@code Subcom}.
     *
     * @param subcomName A valid subcommittee name.
     */
    public Subcom(String subcomName) {
        requireNonNull(subcomName);
        checkArgument(isValidSubcomName(subcomName), MESSAGE_CONSTRAINTS);
        this.subcomName = subcomName;
        this.value = subcomName;
    }

    /**
     * Returns true if a given string is a valid subcommittee name.
     */
    public static boolean isValidSubcomName(String test) {
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
        return subcomName;
    }

    /**
     * Represents having no subcommittee
     */
    private static class NoSubcom extends Subcom {
        public NoSubcom() {
            super("null");
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof NoSubcom;
        }

        @Override
        public String toString() {
            return NOSUBCOM_STRING;
        }
    }
}
