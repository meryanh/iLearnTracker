package cs246.ilearntracker;

//import java.sql.Time;
import android.text.format.Time;


import java.util.Calendar;
import java.util.Date;

/**
 * The cs246.ilearntracker.Assignment class holds the title of the assignment and any comments made on it. It also
 * contains a due date and a due time as well as an indicator as to whether the assignment is
 * completed.  Lastly it has the getters and setters for all of that.
 * Created by Jbeag_000 on 2/23/2015.
 */
public class Assignment implements Comparable<Assignment>{
    private String title;
    private String comments;
    private Date dueDate;
    private Time dueTime;
    private boolean isComplete;
    private boolean isFromILearn;

    /**
     * The default constructor for a new Assignment
     */
    public Assignment() {
        title = "New Assignment";
        comments = "";
        setDueTime(System.nanoTime());
        isComplete = false;
        isFromILearn = true;
    }

    /**
     * A constructor for a new Assignment
     * @param newTitle The name of the assignment
     * @param comment The comments made to give more detail of the assignment
     */
    public Assignment(String newTitle, String comment) {
        title = newTitle;
        comments = comment;
        isComplete = false;
        isFromILearn = false;
    }

    /**
     * Complete constructor for Assignment
     * @param newTitle The name of the assignment
     * @param comment The comments made to give more detail of the assignment
     * @param fromIlearn Whether or not the assignment was synced from iLearn
     */
    public Assignment(String newTitle,
                      String comment,
                      boolean fromIlearn,
                      Date date,
                      Time time) {
        title = newTitle;
        comments = comment;
        isComplete = false;
        isFromILearn = fromIlearn;
        dueTime = time;
        dueDate = date;
    }

    public String getTitle() {
        return title;
    }

    public String getComments() {
        return comments;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public boolean getIsFromILearn() {
        return isFromILearn;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setComments(String newComments) {
        comments = newComments;
    }

    public void setDueDate(Date newDate) { dueDate = newDate; }

    public void setDueTime(Long newTime) {
        dueTime = new Time();
        dueTime.set(newTime);
        dueTime.switchTimezone(dueTime.getCurrentTimezone());
    }

    public void toggleIsComplete() {
        if (isComplete) {
            isComplete = false;
        }
        else {
            isComplete = true;
        }
    }

    public void setIsFromILearn(){
        isFromILearn = true;
    }

    public void setIsFromILearn(boolean value){ isFromILearn = value; }

    /**
     * Makes an XML format for the assignment to be added information
     * @return
     */
    public String getAssignXML() {
        String xml = "";

        xml += "\n\t\t<Assignment>";
        xml += "\n\t\t\t<title>" + getTitle() + "</title>";
        xml += "\n\t\t\t<comments>" + getComments() + "</comments>";
        xml += "\n\t\t\t<dueDate>" + getDueDate().getTime() + "</dueDate>";
        xml += "\n\t\t\t<dueTime>" + getDueTime().toMillis(false) + "</dueTime>";
        xml += "\n\t\t\t<isComplete>" + getIsComplete() + "</isComplete>";
        xml += "\n\t\t<fromILearn>" + getIsFromILearn() + "</fromILearn>";
        xml += "\n\t\t</Assignment>";

        return xml;
    }

    @Override
    public int compareTo(Assignment another) {
        if (((this.dueDate.getTime() + this.dueTime.toMillis(false)) <
                (((Assignment)another).getDueDate().getTime() +
                        ((Assignment)another).getDueTime().toMillis(false)))){
            return -1;
        }else{
            return 1;
        }
    }
}
