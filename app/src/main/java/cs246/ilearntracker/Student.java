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

    /**
     * This function sets the notification variable.
     * @param notify
     */
    public void setNotify(boolean notify) {
        notification = notify;
    }

    /**
     * This function sets the refreshInterval variable.
     * @param refresh
     */
    public void setRefresh(int refresh) {
        refreshInterval = refresh;
    }

    /**
     * This function sets the cleanUpInterval variable.
     * @param cleanUp
     */
    public void setCleanUp(int cleanUp) {
        cleanUpInterval = cleanUp;
    }

    /**
     * This function returns the value in the notification variable.
     * @return
     */
    public boolean getNotify() {
        return notification;
    }

    /**
     * This function returns the value in refreshInterval.
     * @return
     */
    public int getRefreshInterval() {
        return refreshInterval;
    }

    /**
     * This function returns the value in cleanUpInterval.
     * @return
     */
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
