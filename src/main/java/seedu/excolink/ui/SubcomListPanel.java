package seedu.excolink.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.excolink.commons.core.LogsCenter;
import seedu.excolink.model.person.Person;
import seedu.excolink.model.subcom.Subcom;

/**
 * Panel containing the list of subcoms.
 */
public class SubcomListPanel extends UiPart<Region> {
    private static final String FXML = "SubcomListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SubcomListPanel.class);
    private ObservableList<Person> personList;

    @FXML
    private ListView<Subcom> subcomListView;

    /**
     * Creates a {@code SubcomListPanel} with the given {@code ObservableList}.
     */
    public SubcomListPanel(ObservableList<Subcom> subcomList, ObservableList<Person> personList) {
        super(FXML);
        subcomListView.setItems(subcomList);
        subcomListView.setCellFactory(listView -> new SubcomListViewCell());
        this.personList = personList;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Subcom} using a {@code SubcomCard}.
     */
    class SubcomListViewCell extends ListCell<Subcom> {
        @Override
        protected void updateItem(Subcom subcom, boolean empty) {
            super.updateItem(subcom, empty);

            if (empty || subcom == null) {
                setGraphic(null);
                setText(null);
            } else {
                int count = (int) personList.stream()
                        .filter(p -> p.getSubcom().equals(subcom))
                        .count();
                setGraphic(new SubcomCard(subcom, getIndex() + 1, count).getRoot());
            }
        }
    }

}
