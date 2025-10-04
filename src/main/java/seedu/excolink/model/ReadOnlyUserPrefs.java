package seedu.excolink.model;

import java.nio.file.Path;

import seedu.excolink.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getExcoLinkFilePath();

}
