package seedu.excolink.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.excolink.commons.core.GuiSettings;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.model.subcom.exceptions.SubcomNotFoundException;
import seedu.excolink.ui.DisplayEntity;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getExcoLinkFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setExcoLinkFilePath(Path excoLinkFilePath);

    /**
     * Replaces address book data with the data in {@code excoLink}.
     */
    void setExcoLink(ReadOnlyExcoLink excoLink);

    /** Returns the ExcoLink */
    ReadOnlyExcoLink getExcoLink();

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given
     * {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a subcommittee identical to {@code subcom} exists in the app.
     */
    boolean hasSubcom(Subcom subcom);

    /**
     * Deletes the given subcom.
     * The subcom must exist in the app.
     */
    void deleteSubcom(Subcom target);

    /**
     * Adds the given subcommittee.
     * {@code subcom} must not already exist in the app.
     */
    void addSubcom(Subcom subcom);

    /** Returns an unmodifiable view of the filtered subcom list */
    ObservableList<Subcom> getSubcomList();

    /** Returns the number of members in a subcom */
    int getSubcomMemberCount(Subcom subcom);

    /** Returns the subcom corresponding to the name */
    Subcom findSubcom(Subcom subcom) throws SubcomNotFoundException;

    /** Returns the current entity list to display, either Person or Subcom */
    DisplayEntity getDisplayEntity();

    /** Sets the current entity list to display, either Person or Subcom */
    void setDisplayEntity(DisplayEntity displayEntity);
}
