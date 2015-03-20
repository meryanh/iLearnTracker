package cs246.ilearntracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * The user interface for the user when they are manually adding an assignment to there homework
 */
public class AddAssignment extends ActionBarActivity {

    private Student student = Student.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_assignment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.byui);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff326ba9));
        //Student nStudent = new Student();
        ArrayList<String> items = new ArrayList<String>();
        int sizeList = student.classesList.size();
        items.add("Select a Class...");
        for (int i = 0; i < sizeList; i++) {
            Class testClass = student.classesList.get(i);
            String name = testClass.getClassName();
            System.out.println(name);
            items.add(name);
        }
        Spinner dropdown = (Spinner)findViewById(R.id.classSelect);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_assignment, menu);
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

    /**
     * Adds the new assignment to the list of assignments for the specified class and sends you back
     * to the home page
     * @param view The add button that is clicked
     */
    public void add(View view) {
        Assignment assignment = new Assignment();
        EditText titleGetter = (EditText) findViewById(R.id.titleEdit);
        String titleStr = titleGetter.getText().toString();
        assignment.setTitle(titleStr);
        EditText commentGetter = (EditText) findViewById(R.id.commentsEdit);
        String commentStr = commentGetter.getText().toString();
        assignment.setComments(commentStr);
        Date due;
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        due = new Date(year, month, day);
        assignment.setDueDate(due);
        TimePicker notifyTime = ((TimePicker) findViewById(R.id.timePicker));

        long time = convertTime(notifyTime.getCurrentHour(), notifyTime.getCurrentMinute());

        System.out.println(notifyTime.getCurrentHour());
        System.out.println(notifyTime.getCurrentMinute());
        assignment.setDueTime(time);

        Spinner classGetter = (Spinner) findViewById(R.id.classSelect);
        String assignClassName = classGetter.getSelectedItem().toString();
        Class addToClass = new Class();
        boolean classFound = false;
        for (int i = 0; i < student.classesList.size(); i++) {
            Class checkClass = student.classesList.get(i);
            if (checkClass.getClassName().equals(assignClassName)){
                addToClass = checkClass;
                classFound = true;
            }
        } if (!classFound) {
            Context context = getApplicationContext();
            CharSequence text = "Please select a valid class...";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        addToClass.assignmentList.add(assignment);
        System.out.println(assignment.getDueDate());
        System.out.println(assignment.getDueTime());
        Intent intent = new Intent(this, iLearnTracker.class);
        startActivity(intent);

    }

    /**
     * Converts the time to miliseconds
     * @param hour The hour used of the time
     * @param minute The minute used of the time
     * @return The time in miliseconds
     */
    public long convertTime(long hour, long minute) {
        System.out.println(hour + " " + minute);

        long time = (minute + ((hour + 7) * 60)) * 60 * 1000;
        System.out.println(time);

        return time;
    }

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
}
