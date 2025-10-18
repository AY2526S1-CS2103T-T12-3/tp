package seedu.excolink.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.excolink.commons.exceptions.IllegalValueException;
import seedu.excolink.model.person.Email;
import seedu.excolink.model.person.Name;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final String subcom;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("roles") List<JsonAdaptedRole> roles,
            @JsonProperty("subcom") String subcom) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        this.subcom = subcom;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));

        subcom = source.getSubcom().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's
     * {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Role> personRoles = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            personRoles.add(role.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Set<Role> modelRoles = new HashSet<>(personRoles);

        if (subcom == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subcom.class.getSimpleName()));
        }
        if (!Subcom.isValidSubcomName(subcom)) {
            throw new IllegalValueException(Subcom.MESSAGE_CONSTRAINTS);
        } else {
            final Subcom modelSubcom = new Subcom(subcom);
            return new Person(modelName, modelPhone, modelEmail, modelRoles, modelSubcom);
        }
    }

}
