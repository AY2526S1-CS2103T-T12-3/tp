package seedu.excolink.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.excolink.model.subcom.Subcom;

/**
 * A utility class containing a list of {@code Subcom} objects to be used in tests.
 */
public class TypicalSubcoms {

    public static final Subcom TECH = new Subcom("Tech");
    public static final Subcom UI_UX = new Subcom("UI/UX");
    public static final Subcom BRANDING_AND_MARKETING = new Subcom("Branding & Marketing");

    private TypicalSubcoms() {} // prevents instantiation

    public static List<Subcom> getTypicalSubcoms() {
        return new ArrayList<>(Arrays.asList(TECH, UI_UX, BRANDING_AND_MARKETING));
    }
}
