package seedu.excolink.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.excolink.commons.util.ToStringBuilder;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.UniquePersonList;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.model.subcom.SubcomList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ExcoLink implements ReadOnlyExcoLink {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    private final SubcomList subcoms = new SubcomList();

    public ExcoLink() {}

    /**
     * Creates an ExcoLink using the Persons in the {@code toBeCopied}
     */
    public ExcoLink(ReadOnlyExcoLink toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the subcom list with {@code subcoms}.
     * {@code subcoms} must not contain duplicate subcoms.
     */
    public void setSubcoms(List<Subcom> subcoms) { this.subcoms.setSubcoms(subcoms); }

    /**
     * Resets the existing data of this {@code ExcoLink} with {@code newData}.
     */
    public void resetData(ReadOnlyExcoLink newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setSubcoms(newData.getSubcomList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code ExcoLink}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// subcom-level operations

    /**
     * Returns true if a subcom with the same identity as {@code subcom} exists in the app.
     */
    public boolean hasSubcom(Subcom subcom) {
        requireNonNull(subcom);
        return subcoms.contains(subcom);
    }

    /**
     * Adds a subcom to the app.
     * The subcom must not already exist in the app.
     */
    public void addSubcom(Subcom subcom) {
        subcoms.add(subcom);
    }

    /**
     * Removes {@code key} from this {@code ExcoLink}.
     * {@code key} must exist in the app.
     */
    public void removeSubcom(Subcom key) {
        subcoms.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Subcom> getSubcomList() { return subcoms.asUnmodifiableObservableList(); }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExcoLink)) {
            return false;
        }

        ExcoLink otherExcoLink = (ExcoLink) other;
        return persons.equals(otherExcoLink.persons) && subcoms.equals(otherExcoLink.subcoms);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
