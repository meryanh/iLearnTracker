package cs246.ilearntracker;

import java.sql.Time;
import java.util.Date;

/**
 * The cs246.ilearntracker.Assignment class holds the title of the assignment and any comments made on it. It also
 * contains a due date and a due time as well as an indicator as to whether the assignment is
 * completed.  Lastly it has the getters and setters for all of that.
 * Created by Jbeag_000 on 2/23/2015.
 */
public class Assignment {
    private String title;
    private String comments;
    private Date dueDate;
    private Time dueTime;
    private boolean isComplete;
    //private boolean isFromILearn;

    /**
     * The default constructor for a new Assignment
     */
    public Assignment() {
        title = "New Assignment";
        comments = "";
        //setDueDate(); //Figure out how to put in the current time plus 1 hour
        setDueTime(System.nanoTime());
        isComplete = false;
        //isFromILearn = false;
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

    /*
    public boolean getIsFromILearn() {
        return isFromILearn;
    }
    */

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setComments(String newComments) {
        comments = newComments;
    }

    public void setDueDate(Date newDate) { dueDate = newDate; }

    public void setDueTime(Long newTime) {
        dueTime = new Time(newTime);
    }

    public void toggleIsComplete() {
        if (isComplete) {
            isComplete = false;
        }
        else {
            isComplete = true;
        }
    }

    /*
    public void setIsFromILearn(){
        isFromILearn = true;
    }
    */

    /**
     * Makes an XML format for the assignment to be added information
     * @return
     */
    public String getAssignXML() {
        String xml = "";

        xml += "\n\t<Assignment>";
        xml += "\n\t\t<title>" + getTitle() + "</title>";
        xml += "\n\t\t<comments>" + getComments() + "</comments>";
        xml += "\n\t\t<dueDate>" + getDueDate() + "</dueDate>";
        xml += "\n\t\t<dueTime>" + getDueTime() + "</dueTime>";
        xml += "\n\t\t<isComplete>" + getIsComplete() + "</isComplete>";
        //xml += "\n\t\t<fromILearn>" + getIsFromILearn() + "</fromILearn>";
        xml += "\n\t</Assignment>";

        return xml;
    }

}
