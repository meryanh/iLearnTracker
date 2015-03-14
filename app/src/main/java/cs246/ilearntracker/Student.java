package cs246.ilearntracker;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Braden on 2/23/2015.
 */
public class Student extends ActionBarActivity {
    private static Student instance = new Student();
    public static final String PREFS_NAME = "myPrefsFile";

    private boolean notification;
    private Integer notifyInterval;
    private Integer refreshInterval;
    private Integer cleanUpInterval;
    public List<Class> classesList;
    private static final String TAG_STUDENT = "Student Activity";

    /**
     * Default Constructor
     */

    private Student() {
        notification = true;
        refreshInterval = 86400;
        cleanUpInterval = 604800;
        classesList = new ArrayList<Class>();
    }
    public static Student getInstance() {
        return instance;
    }
    public void setNotify(boolean notify) { notification = notify; }

    public void setRefresh(int refresh) {
        refreshInterval = refresh;
    }

    public void setCleanUp(int cleanUp) {
        cleanUpInterval = cleanUp;
    }

    public boolean getNotify() {
        return notification;
    }

    public int getNotifyInt() { return notifyInterval; }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public int getCleanUpInterval() {
        return cleanUpInterval;
    }

    public Class getClass(Integer i) { return classesList.get(i); }

    public List<Class> getClassList() { return classesList; }

    /**
     * This function will save the local variables notification, refreshInterval,
     * and cleanUpInterval into its own xml file.
     */
    public void saveSettings() {
        String fileName = "mySettings.xml";
        String settings = "<settings>\n";
        settings += "\t<notification>" + notification + "</notification>\n";
        settings += "\t<notifyInterval>" + notifyInterval + "</notifyInterval>\n";
        settings += "\t<refreshInterval>" + refreshInterval + "</refreshInterval>\n";
        settings += "\t<cleanUpInterval>" + cleanUpInterval + "</cleanUpInterval>\n";
        settings += "</settings>\n";

        Document mySettings = convertStringToDocument(settings);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(fileName));
            Source input = new DOMSource(mySettings);

            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param xmlStr
     * @return
     */
    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This function will load the previous saved variable values from the xml file.
     */
    public void loadSettings() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);
        notifyInterval = pref.getInt("beforeDue", 1);
        refreshInterval = pref.getInt("refresh", 1);
        cleanUpInterval = pref.getInt("cleanUp", 1);

/*
        try {


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("mySettings.xml"));

            NodeList setting = doc.getElementsByTagName("settings");
            Element sets = (Element) setting.item(0);

            if (sets.getAttribute("notification") == "true")
                setNotify(true);
            else
                setNotify(false);
            setRefresh(Integer.parseInt(sets.getAttribute("refreshInterval")));
            setCleanUp(Integer.parseInt(sets.getAttribute("cleanUpInterval")));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }*/
    }

    public void addToList(Class newClass) {
        classesList.add(newClass);
    }
    /**
     * This function adds a class to the classesList
     */
    public void addClass() {
        //get input from user through separate activity.
        //not done yet.
        Class myClass = new Class();

        myClass.setClassName("CS246"); //This line for testing purposes.

        //add the class
        classesList.add(myClass);
    }

    /**
     *  This function is for testing purposes.
     */
    public void showClasses() {
        for (Class thisClass: classesList) {
            System.out.println(thisClass.getClassName());
        }
    }

    /**
     * This will read in the classes from the xml file and put them into
     * the classesList.
     */
    public void loadClasses() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("mySemester.xml"));

            NodeList classes = doc.getElementsByTagName("Class");

            for (int i = 0; i < classes.getLength(); i++) {
                Element thisClass = (Element) classes.item(i);
                Class myClass = new Class(thisClass.getAttribute("className"), Integer.valueOf(thisClass.getAttribute("classColor")));
                if (thisClass.getAttribute("isActive").equals("false"))
                    myClass.toggleIsActive();

                NodeList assignments = doc.getElementsByTagName("Assignment");
                for (int j = 0; j < assignments.getLength(); j++) {
                    Element thisAssignment = (Element) assignments.item(j);
                    Assignment myAssignment = new Assignment(thisAssignment.getAttribute("title"),
                            thisAssignment.getAttribute("comments"));
                    //myAssignment.setDueDate(Long.valueOf(thisAssignment.getAttribute("dueDate"))); //This is not finished yet.
                    myAssignment.setDueTime(Long.valueOf(thisAssignment.getAttribute("dueTime"))); //Same for this line.
                    if (thisAssignment.getAttribute("isComplete").equals("true"))
                        myAssignment.toggleIsComplete();
                            /* Uncomment this block for connection to I-Learn
                            if (thisAssignment.getAttribute("fromILearn").equals("true"))
                                myAssignment.setIsFromILearn();
                             */
                    myClass.addAssignment(myAssignment);
                }

                classesList.add(myClass);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function will save the information from the classesList into
     * the xml file.
     */
    public void saveClasses() {
        String info = "<Student>";
        for (Class thisClass: classesList) {
            info += thisClass.getXMLContent();
        }
        info += "</Student>";

        Document myClasses = convertStringToDocument(info);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("mySemester.xml"));
            Source input = new DOMSource(myClasses);

            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
