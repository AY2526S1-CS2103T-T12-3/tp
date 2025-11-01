package seedu.excolink.model.subcom;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.commons.util.AppUtil.checkArgument;

/**
 * Represents a subcommittee in ExcoLink.
 * Guarantees: immutable
 */
public class Subcom {
    public static final String MESSAGE_CONSTRAINTS = "Subcommittee names should only contain letters, numbers, spaces, "
            + "hyphens, ampersands, and parentheses, and cannot be blank";
    public static final String VALIDATION_REGEX = "^[A-Za-z0-9&()\\- ]+$";
    public static final Subcom NOSUBCOM = new NoSubcom();
    public static final String NOSUBCOM_STRING = "No subcommittee";

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

    public String getName() {
        return subcomName;
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
        return subcomName.equalsIgnoreCase(otherSubcom.subcomName);
    }

    @Override
    public int hashCode() {
        return subcomName.toLowerCase().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return subcomName;
    }

    /**
     * Returns true if Subcom is NoSubcom
     */
    public boolean isNone() {
        return false;
    }

    /**
     * Represents having no subcommittee
     */
    private static class NoSubcom extends Subcom {
        public NoSubcom() {
            super(NOSUBCOM_STRING);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof NoSubcom;
        }

        @Override
        public String toString() {
            return NOSUBCOM_STRING;
        }

        @Override
        public boolean isNone() {
            return true;
        }
    }
}
