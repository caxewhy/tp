package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.search.SearchContext;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PreferenceCard extends UiPart<Region> {

    private static final String FXML = "PreferenceListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final PropertyPreference propertyPreference;

    @FXML
    private Label id;

    @FXML
    private Label priceRange;

    @FXML
    private FlowPane tags;

    @FXML
    private StackPane preferenceListPanelPlaceholder;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PreferenceCard(PropertyPreference propertyPreference, int displayedIndex, SearchContext searchContext) {
        super(FXML);

        this.propertyPreference = propertyPreference;
        id.setText(displayedIndex + ". ");
        priceRange.setText("  --------------  " + propertyPreference.getPriceRange().toString());
        priceRange.setWrapText(true);
        priceRange.setTextAlignment(TextAlignment.RIGHT);

        if (searchContext.isPriceInRangeForPerson(propertyPreference.getPriceRange())) {
            priceRange.getStyleClass().add("active");
        }

        propertyPreference.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label label = new Label(tag.tagName);
                    if (searchContext.isTagActiveForPerson(tag)) {
                        label.getStyleClass().add("active");
                    }
                    tags.getChildren().add(label);
                });
    }
}
