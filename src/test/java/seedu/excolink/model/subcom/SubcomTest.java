package seedu.excolink.model.subcom;

import static seedu.excolink.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.excolink.model.tag.Tag;

public class SubcomTest {
        @Test
        public void constructor_null_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () -> new Subcom(null));
        }
}
