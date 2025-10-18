package seedu.excolink.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_ROLE_TEAM_LEAD;
import static seedu.excolink.testutil.Assert.assertThrows;
import static seedu.excolink.testutil.TypicalExcoLink.getTypicalExcoLink;
import static seedu.excolink.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.exceptions.DuplicatePersonException;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.testutil.PersonBuilder;

public class ExcoLinkTest {

    private final ExcoLink excoLink = new ExcoLink();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), excoLink.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> excoLink.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExcoLink_replacesData() {
        ExcoLink newData = getTypicalExcoLink();
        excoLink.resetData(newData);
        assertEquals(newData, excoLink);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withRoles(VALID_ROLE_TEAM_LEAD)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ExcoLinkStub newData = new ExcoLinkStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> excoLink.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> excoLink.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInExcoLink_returnsFalse() {
        assertFalse(excoLink.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInExcoLink_returnsTrue() {
        excoLink.addPerson(ALICE);
        assertTrue(excoLink.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInExcoLink_returnsTrue() {
        excoLink.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withRoles(VALID_ROLE_TEAM_LEAD)
                .build();
        assertTrue(excoLink.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> excoLink.getPersonList().remove(0));
    }

    @Test
    public void hasSubcom_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> excoLink.hasSubcom(null));
    }

    @Test
    public void hasSubcom_subcomNotInExcoLink_returnsFalse() {
        Subcom subcom = new Subcom("Finance");
        assertFalse(excoLink.hasSubcom(subcom));
    }

    @Test
    public void hasSubcom_subcomInExcoLink_returnsTrue() {
        Subcom subcom = new Subcom("Finance");
        excoLink.addSubcom(subcom);
        assertTrue(excoLink.hasSubcom(subcom));
    }

    @Test
    public void getSubcomList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> excoLink.getSubcomList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ExcoLink.class.getCanonicalName() + "{persons=" + excoLink.getPersonList() + "}";
        assertEquals(expected, excoLink.toString());
    }

    /**
     * A stub ReadOnlyExcoLink whose persons list can violate interface constraints.
     */
    private static class ExcoLinkStub implements ReadOnlyExcoLink {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Subcom> subcoms = FXCollections.observableArrayList();

        ExcoLinkStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Subcom> getSubcomList() {
            return subcoms;
        }
    }

}
