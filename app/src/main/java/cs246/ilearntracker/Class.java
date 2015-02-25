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
    private Color classColor;

    /**
     * Class default constructor.
     */
    public Class(){
        className = null;
        classColor = null;
        assignmentList = new ArrayList<Assignment>();
        isActive = false;
    }

    /**
     * Complete constructor for the Class class.
     * @param name
     * @param color
     */
    public Class(String name, Color color){
        className = name;
        classColor = color;
        assignmentList = new ArrayList<Assignment>();
        isActive = true;
    }

    public void addAssignment(Assignment assignment){ assignmentList.add(assignment); }

    public void setClassName(String name) { className = name; }

    public void setClassColor(Color color) { classColor = color; }

}
