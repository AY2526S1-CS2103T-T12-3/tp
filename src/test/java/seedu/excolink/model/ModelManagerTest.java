package seedu.excolink.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.excolink.testutil.Assert.assertThrows;
import static seedu.excolink.testutil.TypicalPersons.ALICE;
import static seedu.excolink.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.excolink.commons.core.GuiSettings;
import seedu.excolink.model.person.NameContainsKeywordsPredicate;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.testutil.ExcoLinkBuilder;
import seedu.excolink.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ExcoLink(), new ExcoLink(modelManager.getExcoLink()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setExcoLinkFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setExcoLinkFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setExcoLinkFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setExcoLinkFilePath(null));
    }

    @Test
    public void setExcoLinkFilePath_validPath_setsExcoLinkFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setExcoLinkFilePath(path);
        assertEquals(path, modelManager.getExcoLinkFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInExcoLink_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInExcoLink_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasSubcom_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSubcom(null));
    }

    @Test
    public void hasSubcom_subcomNotInExcoLink_returnsFalse() {
        Subcom subcom = new Subcom("Finance");
        assertFalse(modelManager.hasSubcom(subcom));
    }

    @Test
    public void hasSubcom_subcomInExcoLink_returnsTrue() {
        Subcom subcom = new Subcom("Finance");
        modelManager.addSubcom(subcom);
        assertTrue(modelManager.hasSubcom(subcom));
    }

    @Test
    public void getSubcomList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getSubcomList().remove(0));
    }

    @Test
    public void getPersonList_returnsAllPersons() {
        ObservableList<Person> allPersons = modelManager.getPersonList();

        assertEquals(modelManager.getPersonList(), allPersons);
        assertThrows(UnsupportedOperationException.class, () -> allPersons.add(new PersonBuilder().build()));
    }


    @Test
    public void deleteSubcom_removesSubcomAndClearsMembers() {
        Subcom publicity = new Subcom("Publicity");
        modelManager.addSubcom(publicity);

        // Add person in subcom
        Person personInSubcom = new PersonBuilder().withSubcom("Publicity").build();
        modelManager.addPerson(personInSubcom);

        assertTrue(modelManager.hasSubcom(publicity));
        assertTrue(modelManager.hasPerson(personInSubcom));

        modelManager.deleteSubcom(publicity);

        // Subcom removed
        assertFalse(modelManager.hasSubcom(publicity));

        // Member should have been removed from the subcom
        Person updatedPerson = modelManager.getFilteredPersonList().get(0);
        assertFalse(updatedPerson.getSubcom().equals(publicity));
    }

    @Test
    public void addSubcom_addsSuccessfully() {
        Subcom welfare = new Subcom("Welfare");
        modelManager.addSubcom(welfare);
        assertTrue(modelManager.hasSubcom(welfare));
    }


    @Test
    public void equals() {
        ExcoLink excoLink = new ExcoLinkBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ExcoLink differentExcoLink = new ExcoLink();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(excoLink, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(excoLink, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different excoLink -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentExcoLink, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(excoLink, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setExcoLinkFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(excoLink, differentUserPrefs)));
    }
}
