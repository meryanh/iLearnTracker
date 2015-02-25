package cs246.ilearntracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Braden on 2/23/2015.
 */
public class Student {
    private boolean notification;
    private int refreshInterval;
    private int cleanUpInterval;
    private List<Class> classesList;

    /**
     * Default Constructor
     */
    public Student() {
        notification = true;
        refreshInterval = 86400;
        cleanUpInterval = 604800;
        classesList = new ArrayList<Class>();
    }

    public void setNotify(boolean notify) {
        notification = notify;
    }

    public void setRefresh(int refresh) {
        refreshInterval = refresh;
    }

    public void setCleanUp(int cleanUp) {
        cleanUpInterval = cleanUp;
    }

    public boolean getNotify() {
        return notification;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public int getCleanUpInterval() {
        return cleanUpInterval;
    }

    /**
     * This function will save the local variables notification, refreshInterval,
     * and cleanUpInterval into its own xml file.
     */
    public void saveSettings() {

    }

    /**
     * This function will load the previous saved variable values from the xml file.
     */
    public void loadSettings() {

    }

    /**
     * This function adds a class to the classesList
     */
    public void addClass() {

    }

    /**
     * This will read in the classes from the xml file and put them into
     * the classesList.
     */
    public void loadClasses() {

    }

    /**
     * This function will save the information from the classesList into
     * the xml file.
     */
    public void saveClasses() {

    }
}
