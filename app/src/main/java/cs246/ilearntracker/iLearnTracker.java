package cs246.ilearntracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class iLearnTracker extends ActionBarActivity {
    private Student student = Student.getInstance();

    public static String HTMLData = null;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_i_learn_tracker);
        //student.loadSettings();
        //student.loadClasses();
        //setupClassButtons();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);


        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                // Do whatever is needed with the clicked info.
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        //setupClassButtons();
        loadClassButtons();
    }

    /*
     * Preparing the list data for assignment data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Change this to use assignments within the student's class objects
        //
        // First sort by date,
        // then make a new header for each date,
        // then add each assignment along with due dates.

        listDataHeader.add("Today");
        listDataHeader.add("Tomorrow");
        listDataHeader.add("Even later...");

        List<String> Today = new ArrayList<String>();
        Today.add("Assignment 1");
        Today.add("Assignment 2");
        Today.add("Exam #1");

        List<String> Tomorrow = new ArrayList<String>();
        Tomorrow.add("Assignment 3");
        Tomorrow.add("Reading 1");
        Tomorrow.add("Study guide");

        List<String> later = new ArrayList<String>();
        later.add("More stuff");

        listDataChild.put(listDataHeader.get(0), Today); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Tomorrow);
        listDataChild.put(listDataHeader.get(2), later);
    }

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

    public void loadWebActivity(View view){
        System.out.println("///////////////////////////////////////////");
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }

    public void refresh(View view) {
        //Student stu = new Student();
        //int siz = stu.classesList.size();
        //System.out.println(siz);
        loadClassButtons();
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

    public void loadClassButtons() {
        ClassListAdapter2 adapter = new ClassListAdapter2(student.classesList, this);
        ListView lView = (ListView) findViewById(R.id.classList);
        lView.setAdapter(adapter);
    }
}
