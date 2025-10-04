package seedu.excolink.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.excolink.commons.exceptions.IllegalValueException;
import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.person.Person;

/**
 * An Immutable ExcoLink that is serializable to JSON format.
 */
@JsonRootName(value = "excoLink")
class JsonSerializableExcoLink {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExcoLink} with the given persons.
     */
    @JsonCreator
    public JsonSerializableExcoLink(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyExcoLink} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExcoLink}.
     */
    public JsonSerializableExcoLink(ReadOnlyExcoLink source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code ExcoLink} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExcoLink toModelType() throws IllegalValueException {
        ExcoLink excoLink = new ExcoLink();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (excoLink.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            excoLink.addPerson(person);
        }
        return excoLink;
    }

}
