package seedu.excolink.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.excolink.logic.Messages;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.model.ModelStub;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.model.subcom.exceptions.SubcomNotFoundException;
import seedu.excolink.ui.DisplayEntity;

class ListSubcomMembersCommandTest {

    private final Subcom publicity = new Subcom("Publicity");
    private final Subcom welfare = new Subcom("Welfare");

    @Test
    void constructor_nullSubcom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ListSubcomMembersCommand(null));
    }

    @Test
    void execute_validSubcom_success() throws Exception {
        ModelStubWithSubcom modelStub = new ModelStubWithSubcom(publicity);
        ListSubcomMembersCommand command = new ListSubcomMembersCommand(publicity);

        CommandResult result = command.execute(modelStub);

        Subcom subcomToList = modelStub.findSubcom(publicity);
        assertEquals(String.format(ListSubcomMembersCommand.MESSAGE_SUCCESS, subcomToList.toString()),
                result.getFeedbackToUser());
        assertTrue(modelStub.isFilteredListUpdated);
    }

    @Test
    void execute_invalidSubcom_throwsCommandException() {
        ModelStubWithSubcom modelStub = new ModelStubWithSubcom(publicity);
        ListSubcomMembersCommand command = new ListSubcomMembersCommand(welfare);

        assertThrows(CommandException.class, () -> command.execute(modelStub),
                Messages.MESSAGE_INVALID_SUBCOM_NAME);
    }

    @Test
    void equals() {
        ListSubcomMembersCommand publicityCommand = new ListSubcomMembersCommand(publicity);
        ListSubcomMembersCommand welfareCommand = new ListSubcomMembersCommand(welfare);

        assertTrue(publicityCommand.equals(publicityCommand));

        ListSubcomMembersCommand publicityCommandCopy = new ListSubcomMembersCommand(publicity);
        assertTrue(publicityCommand.equals(publicityCommandCopy));

        assertFalse(publicityCommand.equals(1));

        assertFalse(publicityCommand.equals(null));

        assertFalse(publicityCommand.equals(welfareCommand));
    }

    @Test
    void toStringMethod() {
        ListSubcomMembersCommand command = new ListSubcomMembersCommand(publicity);
        String expected = ListSubcomMembersCommand.class.getCanonicalName() + "{subcomToList=" + publicity + "}";
        assertEquals(expected, command.toString());
    }

    private static class ModelStubWithSubcom extends ModelStub {
        private final List<Subcom> subcoms = new ArrayList<>();
        private boolean isFilteredListUpdated = false;

        ModelStubWithSubcom(Subcom subcom) {
            subcoms.add(subcom);
        }

        @Override
        public DisplayEntity getDisplayEntity() {
            return DisplayEntity.PERSON;
        }

        @Override
        public void setDisplayEntity(DisplayEntity displayEntity) {
        }

        @Override
        public boolean hasSubcom(Subcom subcom) {
            return subcoms.stream().anyMatch(s -> s.equals(subcom));
        }

        @Override
        public Subcom findSubcom(Subcom subcom) {
            return subcoms.stream()
                    .filter(s -> s.equals(subcom))
                    .findFirst()
                    .orElseThrow(SubcomNotFoundException::new);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            isFilteredListUpdated = true;
        }
    }
}
