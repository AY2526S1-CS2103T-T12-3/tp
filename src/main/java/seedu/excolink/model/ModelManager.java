package seedu.excolink.model;

import static java.util.Objects.requireNonNull;
import static seedu.excolink.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.excolink.commons.core.GuiSettings;
import seedu.excolink.commons.core.LogsCenter;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ExcoLink excoLink;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Subcom> subcoms;

    /**
     * Initializes a ModelManager with the given excoLink and userPrefs.
     */
    public ModelManager(ReadOnlyExcoLink excoLink, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(excoLink, userPrefs);

        logger.fine("Initializing with address book: " + excoLink + " and user prefs " + userPrefs);

        this.excoLink = new ExcoLink(excoLink);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.excoLink.getPersonList());
        this.subcoms = new FilteredList<>(this.excoLink.getSubcomList());
    }

    public ModelManager() {
        this(new ExcoLink(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getExcoLinkFilePath() {
        return userPrefs.getExcoLinkFilePath();
    }

    @Override
    public void setExcoLinkFilePath(Path excoLinkFilePath) {
        requireNonNull(excoLinkFilePath);
        userPrefs.setExcoLinkFilePath(excoLinkFilePath);
    }

    //=========== ExcoLink ================================================================================

    @Override
    public void setExcoLink(ReadOnlyExcoLink excoLink) {
        this.excoLink.resetData(excoLink);
    }

    @Override
    public ReadOnlyExcoLink getExcoLink() {
        return excoLink;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return excoLink.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        excoLink.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        excoLink.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        excoLink.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasSubcom(Subcom subcom) {
        requireNonNull(subcom);
        return excoLink.hasSubcom(subcom);
    }

    @Override
    public void deleteSubcom(Subcom target) {
        excoLink.removeSubcom(target);

        // Remove all members of the deleted subcommittee
        List<Person> allPersons = new ArrayList<>(excoLink.getPersonList());
        for (Person person : allPersons) {
            if (person.getSubcom().equals(target)) {
                Person updatedPerson = person.removeFromSubcom();
                setPerson(person, updatedPerson);
            }
        }
    }

    @Override
    public void addSubcom(Subcom subcom) {
        excoLink.addSubcom(subcom);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedExcoLink}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Subcom List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Subcom} backed by the internal list of
     * {@code versionedExcoLink}
     */
    @Override
    public ObservableList<Subcom> getSubcomList() {
        return subcoms;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return excoLink.equals(otherModelManager.excoLink)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
