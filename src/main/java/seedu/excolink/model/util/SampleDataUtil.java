package seedu.excolink.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.person.Email;
import seedu.excolink.model.person.Name;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;

/**
 * Contains utility methods for populating {@code ExcoLink} with sample data.
 */
public class SampleDataUtil {
    private static Subcom publicity = new Subcom("Publicity");
    private static Subcom logistics = new Subcom("Logistics");
    private static Subcom relations = new Subcom("relations");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    getRoleSet("friends"),
                    publicity),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    getRoleSet("colleagues", "friends"),
                    logistics),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    getRoleSet("neighbours"),
                    relations),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    getRoleSet("family"),
                    publicity),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    getRoleSet("classmates"),
                    logistics),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    getRoleSet("colleagues"),
                    relations)
        };
    }

    public static ReadOnlyExcoLink getSampleExcoLink() {
        ExcoLink sampleAb = new ExcoLink();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

}
