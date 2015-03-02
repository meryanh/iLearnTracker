package cs246.ilearntracker;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan Hildreth on 2/25/2015.
 */
public class Class {
    private List<Assignment> assignmentList;
    private boolean isActive;
    private String className;
    private Integer classColor;

    /**
     * Class default constructor.
     */
    public Class(){
        className = null;
        classColor = 0xffffff;
        assignmentList = new ArrayList();
        isActive = false;
    }

    /**
     * Complete constructor for the Class class.
     * @param name
     * @param color
     */
    public Class(String name, int color){
        className = name;
        classColor = color;
        assignmentList = new ArrayList();
        isActive = true;
    }

    public void addAssignment(Assignment assignment){ assignmentList.add(assignment); }

    public void setClassName(String name){ className = name; }

    public void setClassColor(int color){ classColor = color; }

    public String getClassName(){ return className; }

    public int getClassColor(){ return classColor; }

    public List<Assignment> getAssignmentList(){ return assignmentList; }

    public boolean getIsActive(){ return isActive; }

    public void toggleIsActive(){ isActive = (!isActive); }

    /**
     * Removes each Assignment in the Class that is marked as complete.
     */
    public void cleanUpAssignments(){
        for(Assignment assignment : assignmentList){
            if(assignment.getIsComplete()){
                // remove assignment from assignmentList
                assignmentList.remove(assignment);
            }
        }
    }

    /**
     * Returns the entire content of the Class class and all of the associated Assignment classes
     * as an XML formatted string.
     */
    public String getXMLContent(){

        String XMLContent;

        XMLContent = "<Class>\n";
        XMLContent += "\n\t<className>" + className + "</className>";
        XMLContent += "\n\t<classColor>" + classColor.toString() + "</classColor>";
        XMLContent += "\n\t<isActive>" + isActive + "</isActive>";

        for(Assignment assignment : assignmentList){
            XMLContent += "\n\t<Assignment>";
            XMLContent += "\n\t\t<title>" + assignment.getTitle() + "</title>";
            XMLContent += "\n\t\t<comments>" + assignment.getComments() + "</comments>";
            XMLContent += "\n\t\t<dueDate>" + assignment.getDueDate() + "</dueDate>";
            XMLContent += "\n\t\t<dueTime>" + assignment.getDueTime() + "</dueTime>";
            XMLContent += "\n\t\t<isComplete>" + assignment.getIsComplete() + "</isComplete>";
            XMLContent += "\n\t<Assignment>";
        }

        XMLContent += "\n<Class>";

        return XMLContent;
    }
}
