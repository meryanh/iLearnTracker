package cs246.ilearntracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * Shared class for containing all of the Class and Assignment data
 * for this application.
 */
public class Student extends ActionBarActivity{
    private static Student instance = new Student();
    public static final String PREFS_NAME = "iTracker.conf";

    private String HTMLData = null;
    private boolean notification;
    private Integer notifyInterval;
    private Integer refreshInterval;
    private Integer cleanUpInterval;
    public List<Class> classesList;
    private Context context;
    public boolean initialized;
    private static final String TAG_STUDENT = "Student Activity";

    /**
     * Default Constructor
     */

    private Student() {
        initialized = false;
        notification = true;
        refreshInterval = 86400;
        cleanUpInterval = 604800;
        classesList = new ArrayList<Class>();
        context = null;
    }

    public static Student getInstance() {
        return instance;
    }

    public boolean init() {
        if(!initialized){
            initialized = true;
            loadClasses();
            return false;
        }
        return true;
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

    public Context getContext() { return context; }

    public void setContext(Context newContext) { context = newContext; }

    public int getCleanUpInterval() {
        return cleanUpInterval;
    }

    public Class getClass(Integer i) { return classesList.get(i); }

    public List<Class> getClassList() { return classesList; }

    public String getHTMLData(){ return HTMLData; }

    public void setHTMLData(String html) {HTMLData = html; }

    public void toggleClassIsActive(int loc) {classesList.get(loc).toggleIsActive();};

    /**
     * This function will save the local variables notification, refreshInterval,
     * and cleanUpInterval into its own xml file.
     */
    public void saveSettings() {
        String fileName = "studentData.xml";
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
        if(null == null){
            return;
        }

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        notifyInterval = pref.getInt("beforeDue", 1);
        refreshInterval = pref.getInt("refresh", 1);
        cleanUpInterval = pref.getInt("cleanUp", 1);

    }

    /**
     * Remove all past-due assignments from the Student's class list
     */
    public void clean(){
        for(int i = 0; i < classesList.size(); i++)
        {
            for(int j = 0; j < classesList.get(i).getAssignmentList().size(); j++){
                if(classesList.get(i).getAssignmentList().get(j).getDueDate().getTime()
                        + classesList.get(i).getAssignmentList().get(j).getDueTime().toMillis(false)
                        < System.currentTimeMillis()){

                    classesList.get(i).removeAssignment(j);
                }
            }
        }
    }

    /**
     * This function adds a class to the classesList
     */
    public void addToList(Class newClass) {
        classesList.add(newClass);
    }

    /**
     * This will read in the classes from the xml file and put them into
     * the classesList.
     */
    public void loadClasses() {
        String data = null;

        BufferedReader in = null;
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    context.openFileInput("studentData.xml")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }
            data = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class newClass = null;
        Assignment newAssignment = null;
        String tmp = null;
        boolean firstClass = true;
        for(String classString : data.split("<Class>")){
            if(firstClass){
                firstClass = false;
                continue;
            }

            newClass = new Class();

            tmp = classString.split("<className>")[1];
            tmp = tmp.split("</className>")[0];
            newClass.setClassName(tmp);

            tmp = classString.split("<classColor>")[1];
            tmp = tmp.split("</classColor>")[0];
            newClass.setClassColor(Integer.parseInt(tmp));

            boolean firstAssignment = true;
            for(String assignmentString : classString.split("<Assignment>")){
                if(firstAssignment){
                    firstAssignment = false;
                    continue;
                }

                tmp = assignmentString.split("<title>")[1];
                tmp = tmp.split("</title>")[0];
                newAssignment = new Assignment(tmp, null);

                tmp = assignmentString.split("<comments>")[1];
                tmp = tmp.split("</comments>")[0];
                newAssignment.setComments(tmp);

                tmp = assignmentString.split("<dueDate>")[1];
                tmp = tmp.split("</dueDate>")[0];
                newAssignment.setDueDate(new Date(Long.parseLong(tmp)));

                tmp = assignmentString.split("<dueTime>")[1];
                tmp = tmp.split("</dueTime>")[0];
                newAssignment.setDueTime(Long.parseLong(tmp));

                tmp = assignmentString.split("<fromILearn>")[1];
                tmp = tmp.split("</fromILearn>")[0];
                newAssignment.setIsFromILearn(Boolean.parseBoolean(tmp));

                newClass.addAssignment(newAssignment);
            }
            classesList.add(newClass);
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
        info += "\n</Student>";

        try {
            FileOutputStream fos = context.openFileOutput("studentData.xml", Context.MODE_PRIVATE);
            fos.write(info.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the content from iLearn with the content contained in the HTMLData
     */
    public void parseHTML(){
        if(!(HTMLData.contains("<div due-soon-list="))){
            return;
        }
        // Remove all iLearn assignments before adding new ones:
       for(int i = 0; i < classesList.size(); i++){
           int size = classesList.get(i).getAssignmentList().size();
           for(int j = 0; j < size; j++){
                if(classesList.get(i).getAssignmentList().get(j).getIsFromILearn()){
                    classesList.get(i).removeAssignment(j);
                    size--;
                    j--;
                }
           }
       }

       String[] lines = HTMLData.split("\n");
        short i = 0;
        Class newClass;
        Assignment newAssignment = new Assignment();
        newAssignment.setIsFromILearn();
        String tmp = null;
        java.util.Date date = null;
        boolean first = true;
        for(String line : lines){
           if(line.contains("<div due-soon-list=")){
               for(String part : line.split("<span")){
                   if(first){
                       first = false;
                   }else{
                   if (i == 0) {
                       // Assignment title:
                       newAssignment = new Assignment();
                       newAssignment.setIsFromILearn();
                       tmp = part.split(">")[1];
                       tmp = tmp.substring(0, tmp.length() - 6);
                       newAssignment.setTitle(tmp);
                       i++;
                   } else if (i == 1) {
                       // Time and date:

                       tmp = part.split(">")[1];
                       tmp = tmp.substring(0, tmp.length() - 6);
                       if(tmp.contains("Today")){
                           newAssignment.setDueDate(Calendar.getInstance().getTime());
                           DateFormat formatter = new SimpleDateFormat("hh:mm a");
                           try {
                               date = (Date)formatter.parse(tmp.split(" ")[1] + " " +
                                       tmp.split(" ")[2]);
                               newAssignment.setDueTime(date.getTime());
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                       }else if(tmp.contains("Tomorrow")){
                           Calendar calendar = Calendar.getInstance();
                           calendar.add(Calendar.DATE, 1);
                           newAssignment.setDueDate(calendar.getTime());
                           DateFormat formatter = new SimpleDateFormat("hh:mm a");
                           try {
                               date = (Date)formatter.parse(tmp.split(" ")[1] + " " +
                                       tmp.split(" ")[2]);
                               newAssignment.setDueTime(date.getTime());
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                       }else{
                           Calendar assignmentDate = Calendar.getInstance();
                           tmp = tmp.replace("/","-");
                           newAssignment.setDueDate(new Date(assignmentDate.get(Calendar.YEAR)-1900,
                                   Integer.parseInt(tmp.split(" ")[1].split("-")[0])-1,
                                   Integer.parseInt(tmp.split(" ")[1].split("-")[1])));
                           DateFormat formatter = new SimpleDateFormat("hh:mm a");
                           try {
                               date = (Date)formatter.parse(tmp.split(" ")[2]+" "+tmp.split(" ")[3]);
                               newAssignment.setDueTime(date.getTime());
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                       }
                       i++;
                   } else if (i == 2) {
                       // Class:
                       tmp = part.split(">")[1];
                       tmp = tmp.substring(0, tmp.length() - 6);
                       boolean found = false;
                       for (int j = 0; j < classesList.size(); j++) {
                           if (classesList.get(j).getClassName().equals(tmp)) {
                               classesList.get(j).addAssignment(newAssignment);
                               found = true;
                           }
                       }
                       if (!found) {
                           newClass = new Class(tmp, Color.GRAY);
                           switch(Student.getInstance().getClassList().size()){
                               case 0:
                                   newClass.setClassColor(Color.rgb(0,255,0));
                                   break;
                               case 1:
                                   newClass.setClassColor(Color.rgb(255,0,0));
                                   break;
                               case 2:
                                   newClass.setClassColor(Color.rgb(0,0,255));
                                   break;
                               case 3:
                                   newClass.setClassColor(Color.rgb(0,255,255));
                                   break;
                               case 4:
                                   newClass.setClassColor(Color.rgb(255,0,255));
                                   break;
                               case 5:
                                   newClass.setClassColor(Color.rgb(255,100,0));
                                   break;
                               case 6:
                                   newClass.setClassColor(Color.rgb(255,255,0));
                                   break;
                               case 7:
                                   newClass.setClassColor(Color.rgb(100,100,100));
                                   break;
                               case 8:
                                   newClass.setClassColor(Color.rgb(1,1,1));
                                   break;
                               default:
                                   // Leave as-is.
                           }
                           newClass.addAssignment(newAssignment);
                           addToList(newClass);
                       }
                       i = 0;
                   }
                   }
               }
           }
       }
        saveClasses();
    }
}
