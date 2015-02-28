package cs246.ilearntracker;

import android.graphics.Color;

import junit.framework.Test;
import junit.framework.TestResult;

/**
 * Created by Ryan Hildreth on 2/28/2015.
 */
public class ClassTest implements Test {
    Class theClass;
    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {
        theClass = new Class();
        testConstructor();
        testAddAssignment();
    }

    public void testConstructor(){
        theClass = new Class("test", Color.BLACK);
        assert theClass.getClassName().equals("test");
        assert theClass.getClassColor() == Color.BLACK;
    }

    public void testAddAssignment(){
        theClass.addAssignment(new Assignment());
        assert theClass.getAssignmentList().size() == 1;
    }
}
