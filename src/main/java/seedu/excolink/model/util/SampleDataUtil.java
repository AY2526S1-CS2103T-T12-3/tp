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
    private static Subcom tech = new Subcom("Tech");
    private static Subcom uiUx = new Subcom("UI/UX");
    private static Subcom brandingAndMarketing = new Subcom("Branding & Marketing");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    getRoleSet("team lead"),
                    tech),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    getRoleSet("UI designer"),
                    uiUx),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    getRoleSet("branding", "marketing"),
                    brandingAndMarketing),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    getRoleSet("developer"),
                    tech),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    getRoleSet("designer"),
                    uiUx),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    getRoleSet("marketing"),
                    brandingAndMarketing)
        };
    }

    public static ReadOnlyExcoLink getSampleExcoLink() {
        ExcoLink sampleExcoLink = new ExcoLink();
        for (Person samplePerson : getSamplePersons()) {
            sampleExcoLink.addPerson(samplePerson);
        }
        sampleExcoLink.addSubcom(tech);
        sampleExcoLink.addSubcom(uiUx);
        sampleExcoLink.addSubcom(brandingAndMarketing);
        return sampleExcoLink;
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
