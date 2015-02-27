package cs246.ilearntracker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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
public class Student {
    private boolean notification;
    private Integer refreshInterval;
    private Integer cleanUpInterval;
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

    public void setNotify(String notify) { if (notify == "true") notification = true; else notification = false; }

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
        String fileName = "mySettings.xml";
        String settings = "<settings>\n";
        settings += "\t<notification>" + notification + "</notification>\n";
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

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("mySettings.xml"));

            NodeList setting = doc.getElementsByTagName("settings");
            Element sets = (Element) setting.item(0);

            setNotify(sets.getAttribute("notification"));
            setRefresh(Integer.parseInt(sets.getAttribute("refreshInterval")));
            setCleanUp(Integer.parseInt(sets.getAttribute("cleanUpInterval")));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
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
