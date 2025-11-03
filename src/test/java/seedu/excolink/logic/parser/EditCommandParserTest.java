package seedu.excolink.logic.parser;

import static seedu.excolink.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.excolink.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.INVALID_SUBCOM_DESC;
import static seedu.excolink.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.ROLE_DESC_MEMBER;
import static seedu.excolink.logic.commands.CommandTestUtil.ROLE_DESC_TEAM_LEAD;
import static seedu.excolink.logic.commands.CommandTestUtil.SUBCOM_DESC_PUBLICITY;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_ROLE_MEMBER;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_ROLE_TEAM_LEAD;
import static seedu.excolink.logic.commands.CommandTestUtil.VALID_SUBCOM_PUBLICITY;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.excolink.logic.parser.CliSyntax.PREFIX_SUBCOM;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.excolink.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.excolink.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.excolink.testutil.Assert.assertThrows;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.excolink.commons.core.index.Index;
import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.EditCommand;
import seedu.excolink.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.excolink.model.person.Email;
import seedu.excolink.model.person.Name;
import seedu.excolink.model.person.Phone;
import seedu.excolink.model.role.Role;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String ROLE_EMPTY = " " + PREFIX_ROLE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_INDEX);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_ROLE} alone will reset the roles of the {@code Person} being edited,
        // parsing it together with a valid role results in error
        assertParseFailure(parser, "1" + ROLE_DESC_MEMBER + ROLE_DESC_TEAM_LEAD + ROLE_EMPTY, Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_DESC_MEMBER + ROLE_EMPTY + ROLE_DESC_TEAM_LEAD, Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_EMPTY + ROLE_DESC_MEMBER + ROLE_DESC_TEAM_LEAD, Role.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + ROLE_DESC_TEAM_LEAD
                + EMAIL_DESC_AMY + NAME_DESC_AMY + ROLE_DESC_MEMBER;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withRoles(VALID_ROLE_TEAM_LEAD, VALID_ROLE_MEMBER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // roles
        userInput = targetIndex.getOneBased() + ROLE_DESC_MEMBER;
        descriptor = new EditPersonDescriptorBuilder().withRoles(VALID_ROLE_MEMBER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonRoleValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ROLE_DESC_MEMBER + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROLE_DESC_MEMBER
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROLE_DESC_TEAM_LEAD;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }

    @Test
    public void parse_resetRoles_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + ROLE_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withRoles().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_withSubcom_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + SUBCOM_DESC_PUBLICITY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withSubcom(VALID_SUBCOM_PUBLICITY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidSubcom_failure() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_SUBCOM_DESC;
        assertParseFailure(parser, userInput, Subcom.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_subcomWithEmptyString_returnsEmptySubcom() throws Exception {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SUBCOM + " ";
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noRolesProvided_returnsEmptyOptional() throws Exception {
        // Indirectly test private parseRolesForEdit via normal parse path
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + " "; // valid index, no fields
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_rolesWithEmptyString_returnsEmptyRolesSet() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ROLE + " ";
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withRoles().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

}
