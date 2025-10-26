package seedu.excolink.model.person;

import static seedu.excolink.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Role> roles = new HashSet<>();
    private final Subcom subcom;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<Role> roles, Subcom subcom) {
        requireAllNonNull(name, phone, email, roles, subcom);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roles.addAll(roles);
        this.subcom = subcom;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable role set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public Subcom getSubcom() {
        return subcom;
    }

    /**
     * Removes this person from their associated subcommittee by setting their
     * {@code subcom} to NOSUBCOM.
     * @return The modified person.
     */
    public Person removeFromSubcom() {
        return new Person(name, phone, email, roles, Subcom.NOSUBCOM);
    }

    /**
     * Assign this person to their assigned subcommittee by setting their
     * {@code subcom} to {@param subcom}.
     *
     * @return The modified person.
     */
    public Person assignSubcom(Subcom subcom) {
        return new Person(name, phone, email, roles, subcom);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && roles.equals(otherPerson.roles);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, roles);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("roles", roles)
                .add("subcom", subcom)
                .toString();
    }

}
