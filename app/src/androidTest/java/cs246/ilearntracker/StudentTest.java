package cs246.ilearntracker;

import android.test.InstrumentationTestCase;

import junit.framework.Test;
import junit.framework.TestResult;

/**
 * Created by Braden on 2/25/2015.
 */
public class StudentTest extends InstrumentationTestCase {

    /*public void test() throws Exception {
        Student me = new Student();
        me.setNotify(false);
        assertEquals(me.getNotify(), false);

        testInterval(me);
        testSaveNLoadSettings(me);
        //addAClass(me);
    }*/

    public void testInterval() {

        Student me = new Student();
        me.setNotify(false);
        assertEquals(me.getNotify(), false);
        me.setCleanUp(604800);
        assertEquals(me.getCleanUpInterval(), 604800);

        me.setRefresh(604800);
        //assertEquals(me.getRefreshInterval(), 86400); //Uncomment this to fail the test.
    }

    public void testSaveNLoadSettings() {
        Student me = new Student();
        me.saveSettings();
        me.loadSettings();
        assertEquals(me.getCleanUpInterval(), 604800);
        assertEquals(me.getRefreshInterval(), 86400);
        assertEquals(me.getNotify(), true);
    }

    public void addAClass() {
        Student me = new Student();
        me.addClass();
        me.addClass();
        me.showClasses();
    }
}
