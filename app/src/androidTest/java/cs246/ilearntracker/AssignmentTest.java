package cs246.ilearntracker;

import android.test.InstrumentationTestCase;

/**
 * Created by Jbeag_000 on 2/28/2015.
 */
public class AssignmentTest extends InstrumentationTestCase {

    public void test() throws Exception {
        Assignment tester = new Assignment();
        tester.setTitle("Test Assignment");
        assertEquals(tester.getTitle(), "Test Assignment");

    }
}
