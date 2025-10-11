package seedu.excolink.model.subcom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.excolink.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.excolink.model.subcom.exceptions.DuplicateSubcomException;
import seedu.excolink.model.subcom.exceptions.SubcomNotFoundException;

public class SubcomListTest {

    private final SubcomList subcomList = new SubcomList();
    private final Subcom FINANCE = new Subcom("Finance");
    private final Subcom LOGISTICS = new Subcom("Logistics");

    @Test
    public void contains_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.contains(null));
    }

    @Test
    public void contains_subcomNotInList_returnsFalse() {
        assertFalse(subcomList.contains(FINANCE));
    }

    @Test
    public void contains_subcomInList_returnsTrue() {
        subcomList.add(FINANCE);
        assertTrue(subcomList.contains(FINANCE));
    }

    @Test
    public void add_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.add(null));
    }

    @Test
    public void add_duplicateSubcom_throwsDuplicateSubcomException() {
        subcomList.add(FINANCE);
        assertThrows(DuplicateSubcomException.class, () -> subcomList.add(FINANCE));
    }

    @Test
    public void remove_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.remove(null));
    }

    @Test
    public void remove_subcomDoesNotExist_throwsSubcomNotFoundException() {
        assertThrows(SubcomNotFoundException.class, () -> subcomList.remove(FINANCE));
    }

    @Test
    public void remove_existingSubcom_removesSubcom() {
        subcomList.add(FINANCE);
        subcomList.remove(FINANCE);
        SubcomList expectedList = new SubcomList();
        assertEquals(expectedList, subcomList);
    }

    @Test
    public void setSubcoms_nullSubcomList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.setSubcoms((SubcomList) null));
    }

    @Test
    public void setSubcoms_validSubcomList_replacesOwnListWithProvidedSubcomList() {
        subcomList.add(FINANCE);
        SubcomList expectedList = new SubcomList();
        expectedList.add(LOGISTICS);
        subcomList.setSubcoms(expectedList);
        assertEquals(expectedList, subcomList);
    }

    @Test
    public void setSubcoms_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.setSubcoms((List<Subcom>) null));
    }

    @Test
    public void setSubcoms_list_replacesOwnListWithProvidedList() {
        subcomList.add(FINANCE);
        List<Subcom> list = Collections.singletonList(LOGISTICS);
        subcomList.setSubcoms(list);
        SubcomList expectedList = new SubcomList();
        expectedList.add(LOGISTICS);
        assertEquals(expectedList, subcomList);
    }

    @Test
    public void setSubcoms_listWithDuplicateSubcoms_throwsDuplicateSubcomException() {
        List<Subcom> listWithDuplicates = Arrays.asList(FINANCE, FINANCE);
        assertThrows(DuplicateSubcomException.class, () -> subcomList.setSubcoms(listWithDuplicates));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> subcomList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(subcomList.asUnmodifiableObservableList().toString(), subcomList.toString());
    }
}
