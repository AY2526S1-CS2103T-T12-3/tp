package seedu.excolink.testutil;

import seedu.excolink.model.ExcoLink;
import seedu.excolink.model.subcom.Subcom;

/**
 * A utility class containing a list of {@code Subcom} objects to be used in tests.
 */
public class TypicalSubcoms {

    public static final Subcom TECH = new Subcom("Tech");
    public static final Subcom UI_UX = new Subcom("UI/UX");
    public static final Subcom BRANDING_AND_MARKETING = new Subcom("Branding & Marketing");

    private TypicalSubcoms() {} // prevents instantiation

    /**
     * Returns an {@code ExcoLink} with all the typical subcoms.
     */
    public static ExcoLink getTypicalExcoLink() {
        ExcoLink excoLink = new ExcoLink();
        excoLink.addSubcom(TECH);
        excoLink.addSubcom(UI_UX);
        excoLink.addSubcom(BRANDING_AND_MARKETING);
        return excoLink;
    }
}
