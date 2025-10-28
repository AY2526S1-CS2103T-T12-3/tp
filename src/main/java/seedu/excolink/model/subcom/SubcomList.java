package seedu.excolink.model.subcom;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.excolink.model.subcom.exceptions.DuplicateSubcomException;
import seedu.excolink.model.subcom.exceptions.SubcomNotFoundException;

/**
 * A list of subcoms that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class SubcomList implements Iterable<Subcom> {
    private final ObservableList<Subcom> internalList = FXCollections.observableArrayList();
    private final ObservableList<Subcom> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent subcom as the given argument.
     */
    public boolean contains(Subcom toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a subcom to the list.
     * The subcom must not already exist in the list.
     */
    public void add(Subcom toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSubcomException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent subcom from the list.
     * The subcom must exist in the list.
     */
    public void remove(Subcom toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SubcomNotFoundException();
        }
    }

    public void setSubcoms(SubcomList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code subcoms}.
     * {@code subcoms} must not contain duplicate subcoms.
     */
    public void setSubcoms(List<Subcom> subcoms) {
        requireAllNonNull(subcoms);
        if (!subcomsAreUnique(subcoms)) {
            throw new DuplicateSubcomException();
        }

        internalList.setAll(subcoms);
    }

    public Subcom getSubcomByName(String name) throws SubcomNotFoundException {
        for (Subcom subcom : internalList) {
            if (subcom.getName().toString().equals(name)) {
                return subcom;
            }
        }
        throw new SubcomNotFoundException();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Subcom> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Subcom> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SubcomList)) {
            return false;
        }

        SubcomList otherSubcomList = (SubcomList) other;
        return internalList.equals(otherSubcomList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code subcoms} contains only unique subcoms.
     */
    private boolean subcomsAreUnique(List<Subcom> subcoms) {
        for (int i = 0; i < subcoms.size() - 1; i++) {
            for (int j = i + 1; j < subcoms.size(); j++) {
                if (subcoms.get(i).equals(subcoms.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
