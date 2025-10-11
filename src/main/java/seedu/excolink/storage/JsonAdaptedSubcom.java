package seedu.excolink.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.excolink.commons.exceptions.IllegalValueException;
import seedu.excolink.model.subcom.Subcom;

/**
 * Jackson-friendly version of {@link Subcom}.
 */
class JsonAdaptedSubcom {

    private final String subcomName;

    /**
     * Constructs a {@code JsonAdaptedSubcom} with the given {@code subcomName}.
     */
    @JsonCreator
    public JsonAdaptedSubcom(String subcomName) {
        this.subcomName = subcomName;
    }

    /**
     * Converts a given {@code Subcom} into this class for Jackson use.
     */
    public JsonAdaptedSubcom(Subcom source) {
        subcomName = source.subcomName;
    }

    @JsonValue
    public String getSubcomName() {
        return subcomName;
    }

    /**
     * Converts this Jackson-friendly adapted subcom object into the model's {@code Subcom} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted subcom.
     */
    public Subcom toModelType() throws IllegalValueException {
        if (!Subcom.isValidSubcom(subcomName)) {
            throw new IllegalValueException(Subcom.MESSAGE_CONSTRAINTS);
        }
        return new Subcom(subcomName);
    }

}
