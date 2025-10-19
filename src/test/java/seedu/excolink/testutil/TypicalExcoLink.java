package seedu.excolink.testutil;

import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;

/**
 * A utility class containing a list of {@code Person} and {@code Subcom} objects to be used in tests.
 */
public class TypicalExcoLink {
    /**
     * Returns an {@code ExcoLink} with all the typical persons and subcoms.
     */
    public static ExcoLink getTypicalExcoLink() {
        ExcoLink excoLink = new ExcoLink();
        for (Person person: TypicalPersons.getTypicalPersons()) {
            excoLink.addPerson(person);
        }
        for (Subcom subcom: TypicalSubcoms.getTypicalSubcoms()) {
            excoLink.addSubcom(subcom);
        }
        return excoLink;
    }
}
