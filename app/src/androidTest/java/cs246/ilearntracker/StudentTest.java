package cs246.ilearntracker;

import android.test.InstrumentationTestCase;

import junit.framework.Test;
import junit.framework.TestResult;

/**
 * Created by Braden on 2/25/2015.
 */
public class StudentTest extends InstrumentationTestCase {

    public void test() throws Exception {
        Student me = new Student();
        me.setNotify(false);
        assertEquals(me.getNotify(), false);

        testInterval(me);
        testSaveNLoadSettings(me);
    }

    public void testInterval(Student me) {
        me.setCleanUp(604800);
        assertEquals(me.getCleanUpInterval(), 604800);

        me.setRefresh(604800);
        assertEquals(me.getRefreshInterval(), 86400);
    }

    public void testSaveNLoadSettings(Student me) {
        me.saveSettings();
        me.loadSettings();
        assertEquals(me.getCleanUpInterval(), 604800);
        assertEquals(me.getRefreshInterval(), 604800);
        assertEquals(me.getNotify(), false);
    }
}
