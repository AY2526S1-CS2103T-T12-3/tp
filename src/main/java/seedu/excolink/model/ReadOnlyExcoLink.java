package seedu.excolink.model;

import javafx.collections.ObservableList;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyExcoLink {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the subcoms list.
     * This list will not contain any duplicate subcoms.
     */
    ObservableList<Subcom> getSubcomList();
}
