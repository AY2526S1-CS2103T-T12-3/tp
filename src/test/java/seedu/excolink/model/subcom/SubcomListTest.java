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
    private final Subcom finance = new Subcom("Finance");
    private final Subcom logistics = new Subcom("Logistics");

    @Test
    public void contains_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.contains(null));
    }

    @Test
    public void contains_subcomNotInList_returnsFalse() {
        assertFalse(subcomList.contains(finance));
    }

    @Test
    public void contains_subcomInList_returnsTrue() {
        subcomList.add(finance);
        assertTrue(subcomList.contains(finance));
    }

    @Test
    public void add_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.add(null));
    }

    @Test
    public void add_duplicateSubcom_throwsDuplicateSubcomException() {
        subcomList.add(finance);
        assertThrows(DuplicateSubcomException.class, () -> subcomList.add(finance));
    }

    @Test
    public void remove_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.remove(null));
    }

    @Test
    public void remove_subcomDoesNotExist_throwsSubcomNotFoundException() {
        assertThrows(SubcomNotFoundException.class, () -> subcomList.remove(finance));
    }

    @Test
    public void remove_existingSubcom_removesSubcom() {
        subcomList.add(finance);
        subcomList.remove(finance);
        SubcomList expectedList = new SubcomList();
        assertEquals(expectedList, subcomList);
    }

    @Test
    public void setSubcoms_nullSubcomList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.setSubcoms((SubcomList) null));
    }

    @Test
    public void setSubcoms_validSubcomList_replacesOwnListWithProvidedSubcomList() {
        subcomList.add(finance);
        SubcomList expectedList = new SubcomList();
        expectedList.add(logistics);
        subcomList.setSubcoms(expectedList);
        assertEquals(expectedList, subcomList);
    }

    @Test
    public void setSubcoms_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> subcomList.setSubcoms((List<Subcom>) null));
    }

    @Test
    public void setSubcoms_list_replacesOwnListWithProvidedList() {
        subcomList.add(finance);
        List<Subcom> list = Collections.singletonList(logistics);
        subcomList.setSubcoms(list);
        SubcomList expectedList = new SubcomList();
        expectedList.add(logistics);
        assertEquals(expectedList, subcomList);
    }

    @Test
    public void setSubcoms_listWithDuplicateSubcoms_throwsDuplicateSubcomException() {
        List<Subcom> listWithDuplicates = Arrays.asList(finance, finance);
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
