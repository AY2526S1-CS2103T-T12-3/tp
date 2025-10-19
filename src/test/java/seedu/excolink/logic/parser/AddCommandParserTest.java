package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.excolink.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.excolink.logic.commands.CommandTestUtil.ROLE_DESC_MEMBER;
import static seedu.excolink.logic.commands.CommandTestUtil.ROLE_DESC_TEAM_LEAD;
import static seedu.excolink.logic.commands.CommandTestUtil.SUBCOM_DESC_PUBLICITY;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_ROLE_MEMBER;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_ROLE_TEAM_LEAD;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.excolink.testutil.TypicalPersons.AMY;
import static seedu.excolink.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.AddCommand;
import seedu.excolink.model.person.Email;
import seedu.excolink.model.person.Name;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.role.Role;
import seedu.excolink.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withRoles(VALID_ROLE_MEMBER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROLE_DESC_MEMBER + SUBCOM_DESC_PUBLICITY, new AddCommand(expectedPerson));


        // multiple roles - all accepted
        Person expectedPersonMultipleRoles = new PersonBuilder(BOB).withRoles(VALID_ROLE_MEMBER, VALID_ROLE_TEAM_LEAD)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROLE_DESC_TEAM_LEAD
                        + ROLE_DESC_MEMBER + SUBCOM_DESC_PUBLICITY,
                new AddCommand(expectedPersonMultipleRoles));
    }

    @Test
    public void parse_repeatedNonRoleValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROLE_DESC_MEMBER + SUBCOM_DESC_PUBLICITY;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));


        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE,
                        PREFIX_SUBCOM));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero roles
        Person expectedPerson = new PersonBuilder(AMY).withRoles().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + SUBCOM_DESC_PUBLICITY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROLE_DESC_TEAM_LEAD + ROLE_DESC_MEMBER + SUBCOM_DESC_PUBLICITY, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ROLE_DESC_TEAM_LEAD + ROLE_DESC_MEMBER, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ROLE_DESC_TEAM_LEAD + ROLE_DESC_MEMBER, Email.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ROLE_DESC + VALID_ROLE_MEMBER, Role.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROLE_DESC_TEAM_LEAD + ROLE_DESC_MEMBER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
