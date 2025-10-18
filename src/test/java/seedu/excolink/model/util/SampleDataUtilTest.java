package seedu.excolink.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;

public class SampleDataUtilTest {

    /**
     * Verifies that {@link SampleDataUtil#getSamplePersons()} returns
     * a non-empty array of valid {@link Person} objects with non-null fields.
     */
    @Test
    public void getSamplePersons_nonEmptyAndValid() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        assertNotNull(persons, "Sample persons array should not be null.");
        assertTrue(persons.length > 0, "Sample persons array should not be empty.");

        for (Person p : persons) {
            assertNotNull(p.getName(), "Person name should not be null.");
            assertNotNull(p.getPhone(), "Person phone should not be null.");
            assertNotNull(p.getEmail(), "Person email should not be null.");
            assertNotNull(p.getSubcom(), "Person subcom should not be null.");
            assertNotNull(p.getRoles(), "Person roles should not be null.");
            assertFalse(p.getRoles().isEmpty(), "Each person should have at least one role.");
        }
    }

    /**
     * Verifies that {@link SampleDataUtil#getSampleExcoLink()} returns
     * a {@link ReadOnlyExcoLink} containing all the sample persons.
     */
    @Test
    public void getSampleExcoLink_containsAllSamplePersons() {
        ReadOnlyExcoLink sampleExcoLink = SampleDataUtil.getSampleExcoLink();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        for (Person person : samplePersons) {
            assertTrue(sampleExcoLink.getPersonList().contains(person),
                    "Sample ExcoLink should contain all sample persons.");
        }
    }

    /**
     * Verifies that {@link SampleDataUtil#getRoleSet(String...)} correctly converts
     * string inputs into unique {@link Role} objects.
     */
    @Test
    public void getRoleSet_uniqueRoles() {
        Set<Role> roles = SampleDataUtil.getRoleSet("friends", "friends", "colleagues");

        assertEquals(2, roles.size(), "Duplicate role names should not be added twice.");
        assertTrue(roles.contains(new Role("friends")), "Role set should contain 'friends'.");
        assertTrue(roles.contains(new Role("colleagues")), "Role set should contain 'colleagues'.");
    }

    /**
     * Verifies that {@link SampleDataUtil#getRoleSet(String...)} returns
     * an empty set when no strings are provided.
     */
    @Test
    public void getRoleSet_emptyInput_returnsEmptySet() {
        Set<Role> roles = SampleDataUtil.getRoleSet();
        assertTrue(roles.isEmpty(), "Empty input should result in an empty role set.");
    }

    /**
     * Ensures that the static {@link Subcom} instances in {@link SampleDataUtil}
     * (publicity, logistics, relations) are shared across all sample persons.
     */
    @Test
    public void subcoms_areSharedStaticInstances() {
        Person[] persons = SampleDataUtil.getSamplePersons();

        Set<Subcom> uniqueSubcoms = Arrays.stream(persons)
                .map(Person::getSubcom)
                .collect(Collectors.toSet());

        assertEquals(3, uniqueSubcoms.size(),
                "There should be exactly 3 unique Subcom instances among all sample persons.");
    }

    /**
     * Confirms that {@link SampleDataUtil#getSamplePersons()} contains
     * the expected sample names defined in the class.
     */
    @Test
    public void getSamplePersons_containsExpectedNames() {
        List<String> expectedNames = List.of(
                "Alex Yeoh", "Bernice Yu", "Charlotte Oliveiro",
                "David Li", "Irfan Ibrahim", "Roy Balakrishnan"
        );

        Set<String> actualNames = Arrays.stream(SampleDataUtil.getSamplePersons())
                .map(p -> p.getName().fullName)
                .collect(Collectors.toSet());

        assertTrue(actualNames.containsAll(expectedNames),
                "Sample persons should include all expected names.");
    }
}
