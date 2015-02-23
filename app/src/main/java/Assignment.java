import java.sql.Time;
import java.util.Date;

/**
 * The Assignment class holds the title of the assignment and any comments made on it. It also
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

    public void setDueDate(Date newDate) {
        dueDate = newDate;
    }

    public void setDueTime(Time newTime) {
        dueTime = newTime;
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

}
