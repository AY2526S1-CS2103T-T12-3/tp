package seedu.excolink.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.excolink.model.subcom.Subcom;

/**
 * A UI component that displays information of a {@code Subcom}.
 */
public class SubcomCard extends UiPart<Region> {

    private static final String FXML = "SubcomListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/excoLink-level4/issues/336">The issue on ExcoLink level 4</a>
     */

    public final Subcom subcom;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    /**
     * Creates a {@code SubcomCode} with the given {@code Subcom} and index to display.
     */
    public SubcomCard(Subcom subcom, int displayedIndex) {
        super(FXML);
        this.subcom = subcom;
        id.setText(displayedIndex + ". ");
        name.setText(subcom.getName());
    }
}
