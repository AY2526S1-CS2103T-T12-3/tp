package seedu.excolink.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.excolink.commons.core.GuiSettings;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;

/**
 * A default model stub that has all the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getExcoLinkFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExcoLinkFilePath(Path excoLinkFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExcoLink(ReadOnlyExcoLink newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyExcoLink getExcoLink() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSubcom(Subcom subcom) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addSubcom(Subcom subcom) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSubcom(Subcom target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Subcom> getSubcomList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getSubcomMemberCount(Subcom subcom) {
        throw new AssertionError("This method should not be called.");
    }
}
