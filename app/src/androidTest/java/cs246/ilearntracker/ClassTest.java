package cs246.ilearntracker;

import android.graphics.Color;
import android.test.InstrumentationTestCase;

import junit.framework.Test;
import junit.framework.TestResult;

/**
 * Created by Ryan Hildreth on 2/28/2015.
 */
public class ClassTest extends InstrumentationTestCase {
    Class theClass;

    public void testConstructor(){
        theClass = new Class("test", Color.BLACK);
        assertEquals(theClass.getClassName(), "test");
        assertEquals(theClass.getClassColor(), Color.BLACK);
    }

    public void testAddAssignment(){
        theClass = new Class();
        Assignment assign = new Assignment();
        theClass.addAssignment(assign);
        assert theClass.getAssignmentList().size() == 1;
    }
}
