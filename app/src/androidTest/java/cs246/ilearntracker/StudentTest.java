package cs246.ilearntracker;

import junit.framework.Test;
import junit.framework.TestResult;

/**
 * Created by Braden on 2/25/2015.
 */
public class StudentTest implements Test {

    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {
        Student me = new Student();
        me.setNotify(false);
        assert me.getNotify() == false;

        testInterval(me);
    }

    public void testInterval(Student me) {
        me.setCleanUp(604800);
        assert me.getCleanUpInterval() == 604800;

        me.setRefresh(604800);
        assert me.getRefreshInterval() == 86400;
    }
}
