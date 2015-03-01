package cs246.ilearntracker;

import android.test.InstrumentationTestCase;

/**
 * Created by Jbeag_000 on 2/28/2015.
 */
public class AssignmentToggleTest extends InstrumentationTestCase {
    public void test() {
        Assignment tester = new Assignment();
        tester.toggleIsComplete();
        assertEquals(true, tester.getIsComplete());
        tester.toggleIsComplete();
        assertEquals(false, tester.getIsComplete());
        tester.toggleIsComplete();
        assertEquals(true, tester.getIsComplete());

    }
}
