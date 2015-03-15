package cs246.ilearntracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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


public class iLearnTracker extends ActionBarActivity {
    private Student student = Student.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_i_learn_tracker);
        //student.loadSettings();
        //student.loadClasses();
        setupClassButtons();
    }
/*
    @Override
    public void onResume(){
        setupClassButtons();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_i_learn_tracker, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettings(null);
        }
        else if (id == R.id.action_addClass) {
            goToAddClass(null);
        }

        return super.onOptionsItemSelected(item);
    }

    // Sets the visibility, text, and color tag for all class buttons.
    // Sets all unused buttons to hidden.
    public void setupClassButtons(){
        int resID;
        Integer i = 0;
        TextView buttonView;
        TextView tagView;
        for(Class theClass : student.getClassList()){
            resID = getResources().getIdentifier("classButton0" + i.toString(), "id", "cs246.ilearntracker");
            buttonView = (TextView)findViewById(resID);
            buttonView.setText(theClass.getClassName());
            buttonView.setVisibility(View.VISIBLE);
            resID = getResources().getIdentifier("classTag0" + i.toString(), "id", "cs246.ilearntracker");
            tagView = (TextView)findViewById(resID);
            tagView.setVisibility(View.VISIBLE);
            tagView.setBackgroundColor(theClass.getClassColor());
            i++;
        }
        for(; i < 10; i++){
            resID = getResources().getIdentifier("classButton0" + i.toString(), "id", "cs246.ilearntracker");
            buttonView = (TextView)findViewById(resID);
            buttonView.setVisibility(View.GONE);
            resID = getResources().getIdentifier("classTag0" + i.toString(), "id", "cs246.ilearntracker");
            tagView = (TextView)findViewById(resID);
            tagView.setVisibility(View.GONE);
        }
    }

    public void classButtonClick(View view){
        if(view.getVisibility() != View.VISIBLE){
            return; // Do nothing if hidden button was clicked
        }
        String tag = view.getTag().toString();
        Integer i = Integer.parseInt(tag.substring(tag.length() - 1));

        student.getClass(i).toggleIsActive();

        // RELOAD ASSIGNMENT LIST NOW
    }

    public void addAssignment(View view) {
        Intent intent = new Intent(this, AddAssignment.class);
        startActivity(intent);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    public void goToAddClass(View view) {
        Intent intent = new Intent(this, AddClass.class);
        startActivity(intent);
    }
    public void refresh(View view) {
        //Student stu = new Student();
        //int siz = stu.classesList.size();
        //System.out.println(siz);
        setupClassButtons();
        return;
        /*
        int sizeList = student.classesList.size();
        System.out.println("Here!");
        for (int i = 0; i < sizeList; i++) {
            Class testClass = student.classesList.get(i);
            System.out.println(testClass.getClassName());
        }
        */
    }
}
