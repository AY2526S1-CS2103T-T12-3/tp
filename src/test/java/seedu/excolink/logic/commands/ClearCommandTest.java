package seedu.excolink.logic.commands;

import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.testutil.TypicalExcoLink.getTypicalExcoLink;

import org.junit.jupiter.api.Test;

import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyExcoLink_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyExcoLink_success() {
        Model model = new ModelManager(getTypicalExcoLink(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalExcoLink(), new UserPrefs());
        expectedModel.setExcoLink(new ExcoLink());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
