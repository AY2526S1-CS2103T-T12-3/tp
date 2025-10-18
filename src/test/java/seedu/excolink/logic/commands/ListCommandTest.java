package seedu.excolink.logic.commands;

import static seedu.excolink.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.excolink.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.excolink.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.excolink.testutil.TypicalExcoLink.getTypicalExcoLink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.excolink.model.Model;
import seedu.excolink.model.ModelManager;
import seedu.excolink.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExcoLink(), new UserPrefs());
        expectedModel = new ModelManager(model.getExcoLink(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
