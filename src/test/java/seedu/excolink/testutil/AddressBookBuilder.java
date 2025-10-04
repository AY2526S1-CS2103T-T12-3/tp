package seedu.excolink.testutil;

import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.person.Person;

/**
 * A utility class to help with building ExcoLink objects.
 * Example usage: <br>
 *     {@code ExcoLink ab = new ExcoLinkBuilder().withPerson("John", "Doe").build();}
 */
public class ExcoLinkBuilder {

    private ExcoLink excoLink;

    public ExcoLinkBuilder() {
        excoLink = new ExcoLink();
    }

    public ExcoLinkBuilder(ExcoLink excoLink) {
        this.excoLink = excoLink;
    }

    /**
     * Adds a new {@code Person} to the {@code ExcoLink} that we are building.
     */
    public ExcoLinkBuilder withPerson(Person person) {
        excoLink.addPerson(person);
        return this;
    }

    public ExcoLink build() {
        return excoLink;
    }
}
