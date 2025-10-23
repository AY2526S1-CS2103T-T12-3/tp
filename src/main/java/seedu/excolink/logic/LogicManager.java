package seedu.excolink.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.excolink.commons.core.GuiSettings;
import seedu.excolink.commons.core.LogsCenter;
import seedu.excolink.logic.commands.Command;
import seedu.excolink.logic.commands.CommandResult;
import seedu.excolink.logic.commands.exceptions.CommandException;
import seedu.excolink.logic.parser.ExcoLinkParser;
import seedu.excolink.logic.parser.exceptions.ParseException;
import seedu.excolink.model.Model;
import seedu.excolink.model.ReadOnlyExcoLink;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;
import seedu.excolink.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ExcoLinkParser excoLinkParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        excoLinkParser = new ExcoLinkParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = excoLinkParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveExcoLink(model.getExcoLink());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyExcoLink getExcoLink() {
        return model.getExcoLink();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Subcom> getSubcomList() {
        return model.getSubcomList();
    }

    @Override
    public int getSubcomMemberCount(Subcom subcom) {
        return model.getSubcomMemberCount(subcom);
    }

    @Override
    public Path getExcoLinkFilePath() {
        return model.getExcoLinkFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
