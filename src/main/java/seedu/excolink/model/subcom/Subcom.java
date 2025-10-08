package seedu.excolink.model.subcom;

import static java.util.Objects.requireNonNull;

public class Subcom {
    public final String subcomName;

    /**
     * Constructs a {@code Subcom}.
     *
     * @param subcomName A valid subcommittee name.
     */
    public Subcom(String subcomName) {
        requireNonNull(subcomName);
        this.subcomName = subcomName;
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
