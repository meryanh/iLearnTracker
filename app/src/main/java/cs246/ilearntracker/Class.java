package cs246.ilearntracker;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan Hildreth on 2/25/2015.
 */
public class Class {
    public List<Assignment> assignmentList;
    private boolean isActive;
    private String className;
    private Integer classColor;

    private static final String TAG_CLASS = "Class";

    /**
     * Class default constructor.
     */
    public Class(){
        className = null;
        classColor = 0xffffff;
        assignmentList = new ArrayList();
        isActive = true;
        Log.w(TAG_CLASS,"Class successfully constructed, but values are not correctly set.");
    }

    /**
     * Complete constructor for the Class class.
     * @param name The name of the class
     * @param color The color associated with the class
     */
    public Class(String name, int color){
        className = name;
        classColor = color;
        assignmentList = new ArrayList();
        isActive = true;
        Log.i(TAG_CLASS,"Class successfully constructed with content.");
    }

    public void addAssignment(Assignment assignment){ assignmentList.add(assignment); }

    public void removeAssignment(int loc){ assignmentList.remove(loc); }

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

        XMLContent = "\n\t<Class>";
        XMLContent += "\n\t\t<className>" + className + "</className>";
        XMLContent += "\n\t\t<classColor>" + classColor.toString() + "</classColor>";
        XMLContent += "\n\t\t<isActive>" + isActive + "</isActive>";

        for(Assignment assignment : assignmentList){
            if(assignment == null){
                Log.e(TAG_CLASS,"Assignment does not exist in class.");
            } else {
                XMLContent += assignment.getAssignXML();
            }
        }

        XMLContent += "\n\t</Class>";
        Log.i(TAG_CLASS,"Class XML content successfully created.");
        return XMLContent;
    }
}
