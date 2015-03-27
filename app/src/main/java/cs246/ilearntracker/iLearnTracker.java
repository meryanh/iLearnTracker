package cs246.ilearntracker;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class iLearnTracker extends ActionBarActivity {
    private Student student = Student.getInstance();

    public static String HTMLData = null;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<ListDataHolder>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_i_learn_tracker);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.byui);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff326ba9));
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

        loadClassButtons();
    }

    /**
     * Prepare data list for printing.
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ListDataHolder>>();

        /**
         *
         */
        class Node implements Comparable<Node>{
            public Assignment assignment;
            public int color;
            public Node(Assignment newAssignment, int newColor){
                assignment = newAssignment;
                color = newColor;
            }


            @Override
            public int compareTo(Node another) {
                if ((( this.assignment.getDueTime().toMillis(false)) <
                                (another).assignment.getDueTime().toMillis(false))){
                    return -1;

                }else{
                    return 1;
                }            }
        }

        List<Node> sortedNodes = new ArrayList<>();
        List<Node> nodeList = new ArrayList<>();

        /* Get all assignments and tag colors from the student */
        for(Class theClass : Student.getInstance().getClassList()){
            if(theClass.getIsActive()) {
                for (Assignment theAssignment : theClass.getAssignmentList()) {
                    nodeList.add(new Node(theAssignment, theClass.getClassColor()));
                }
            }
        }

        Collections.sort(nodeList);
        /*Node least;
        while (nodeList.size() > 0) {
            least = nodeList.get(0);
            for (int j = 1; j < nodeList.size(); j++ ) {
                if ()
            }
        }*/

        class ListHolder{
            public String dataHeader = null;
            public List<ListDataHolder> subList = new ArrayList<>();

            public ListHolder(String data, String subData, String subExtra, int color){
                dataHeader = data;
                subList.add(new ListDataHolder(subData, subExtra, color));
            }
        }

        List<ListHolder> listHolderList = new ArrayList<>();
        boolean isAdded;


        String tmp;
        for(Node node : nodeList){
            isAdded = false;
            for(ListHolder theItem : listHolderList) {
                if (theItem.dataHeader.equals(DateFormat.getDateInstance().format(node.assignment.getDueDate()))) {
                    int hour = node.assignment.getDueTime().hour;
                    String ampm = "AM";
                    if (hour > 12) {
                        hour -= 12;
                        ampm = "PM";
                    }
                    theItem.subList.add(new ListDataHolder(node.assignment.getTitle(),
                            node.assignment.getDueTime().format(hour + ":%M " + ampm),
                            node.color));
                    isAdded = true;
                    break;
                }
            }
                if(!isAdded) {
                    int hour = node.assignment.getDueTime().hour;
                    String ampm = "AM";
                    if (hour > 12) {
                        hour -= 12;
                        ampm = "PM";
                    } listHolderList.add(new ListHolder(DateFormat.getDateInstance().
                            format(node.assignment.getDueDate()),
                            node.assignment.getTitle(),
                            node.assignment.getDueTime().format(hour + ":%M " + ampm),
                            node.color));
                }
        }

        for (ListHolder node : listHolderList){
            for(ListDataHolder theItem : node.subList)
                System.out.println(theItem.subTitle);
        }


 /*     listDataHeader.add("Today");
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
   */

        for(int i = 0; i < listHolderList.size(); i++){
            listDataHeader.add(listHolderList.get(i).dataHeader);
            listDataChild.put(listHolderList.get(i).dataHeader, listHolderList.get(i).subList);
        }

/*
        listDataChild.put(listDataHeader.get(0), Today); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Tomorrow);
        listDataChild.put(listDataHeader.get(2), later);
        */
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
        else if (id == R.id.action_addAssign) {
            addAssignment(null);
        }
        else if (id == R.id.action_addClass) {
            goToAddClass(null);
        }
        else if (id == R.id.action_goToILearn) {
            loadWebActivity(null);
        }

        return super.onOptionsItemSelected(item);
    }

    public void classButtonClick(View view){
        if(view.getVisibility() != View.VISIBLE){
            return; // Do nothing if hidden button was clicked
        }
        String tag = view.getTag().toString();
        Integer i = Integer.parseInt(tag.substring(tag.length() - 1));

        student.getClass(i).toggleIsActive();

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

    }

    /**
     * Sends the user to add an Assignment
     * @param view The button pushed
     */
    public void addAssignment(View view) {
        Intent intent = new Intent(this, AddAssignment.class);
        startActivity(intent);
    }

    /**
     * Send the user to the settings activity
     * @param view The button pushed
     */
    public void goToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    /**
     * Sends the user to add a class
     * @param view The button pushed
     */
    public void goToAddClass(View view) {
        Intent intent = new Intent(this, AddClass.class);
        startActivity(intent);
    }

    /**
     * Sends the user to log in to ILearn
     * @param view The button pushed
     */
    public void loadWebActivity(View view){
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }

    /**
     * Updates the list of classes displayed
     */
    public void loadClassButtons() {
        ClassListAdapter2 adapter = new ClassListAdapter2(student.classesList, this);
        ListView lView = (ListView) findViewById(R.id.classList);
        lView.setAdapter(adapter);
    }
}
