package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.logic.commands.AddSubcomCommand.MESSAGE_DUPLICATE_SUBCOM;
import static seedu.excolink.logic.commands.AddSubcomCommand.MESSAGE_SUCCESS;
import static seedu.excolink.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.excolink.commons.core.GuiSettings;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.Model;
import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.ReadOnlyUserPrefs;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;

class AddSubcomCommandTest {
    private final Subcom publicity = new Subcom("Publicity");
    private final Subcom logistics = new Subcom("Logistics");

    @Test
    void constructor_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSubcomCommand(null));
    }

    @Test
    void execute_subcomAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSubcomAdded modelStub = new ModelStubAcceptingSubcomAdded();
        AddSubcomCommand command = new AddSubcomCommand(publicity);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MESSAGE_SUCCESS, publicity), result.getFeedbackToUser());
        assertTrue(modelStub.subcomsAdded.contains(publicity));
    }

    @Test
    void execute_duplicateSubcom_throwsCommandException() {
        ModelStubWithSubcom modelStub = new ModelStubWithSubcom(publicity);
        AddSubcomCommand command = new AddSubcomCommand(publicity);

        assertThrows(CommandException.class, () -> command.execute(modelStub), MESSAGE_DUPLICATE_SUBCOM);
    }

    @Test
    void equals() {
        AddSubcomCommand addPublicityCommand = new AddSubcomCommand(publicity);
        AddSubcomCommand addLogisticsCommand = new AddSubcomCommand(logistics);

        // same object
        assertTrue(addPublicityCommand.equals(addPublicityCommand));

        // same values
        AddSubcomCommand addPublicityCommandCopy = new AddSubcomCommand(publicity);
        assertTrue(addPublicityCommand.equals(addPublicityCommandCopy));

        // different types
        assertFalse(addPublicityCommand.equals(1));

        // null
        assertFalse(addPublicityCommand.equals(null));

        // different subcom
        assertFalse(addPublicityCommand.equals(addLogisticsCommand));
    }

    @Test
    void toStringMethod () {
        AddSubcomCommand command = new AddSubcomCommand(publicity);
        String expected = AddSubcomCommand.class.getCanonicalName() + "{toAdd=" + publicity + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * A default model stub that has all the methods failing.
     */
    private static class ModelStub implements Model {
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
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addSubcom(Subcom subcom) {
            throw new AssertionError("This method should not be called");
        }
    }

    private static class ModelStubWithSubcom extends ModelStub {
        private final Subcom subcom;

        ModelStubWithSubcom(Subcom subcom) {
            this.subcom = subcom;
        }

        @Override
        public boolean hasSubcom(Subcom subcom) {
            return this.subcom.equals(subcom);
        }
    }

    private static class ModelStubAcceptingSubcomAdded extends ModelStub {
        final List<Subcom> subcomsAdded = new ArrayList<>();

        @Override
        public boolean hasSubcom(Subcom subcom) {
            return subcomsAdded.stream().anyMatch(s -> s.equals(subcom));
        }

        @Override
        public void addSubcom(Subcom subcom) {
            subcomsAdded.add(subcom);
        }
    }
}
