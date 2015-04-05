package cs246.ilearntracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;

/**
 * The user interface for the user when they are manually adding an assignment to there homework
 */
public class AddAssignment extends ActionBarActivity {

    int notify;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static AddAssignment inst;
    private Student student = Student.getInstance();
    public String alarmMessage;

    public static AddAssignment instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        notify = 0;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_assignment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.byui);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff326ba9));
        ArrayList<String> items = new ArrayList<String>();
        int sizeList = student.classesList.size();
        items.add("Select a Class...");
        for (int i = 0; i < sizeList; i++) {
            Class testClass = student.classesList.get(i);
            String name = testClass.getClassName();
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
        assignment.setIsFromILearn(false);
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
        System.out.println(year);
        due = new Date(year - 1900, month, day);
        System.out.println(due);
        assignment.setDueDate(due);
        TimePicker notifyTime = ((TimePicker) findViewById(R.id.timePicker));

        long time = convertTime(notifyTime.getCurrentHour(), notifyTime.getCurrentMinute());

        assignment.setDueTime(time);
        System.out.println(assignment.getDueTime().toString() + "\n" + assignment.getDueDate().toString());

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


        String dueWhen;
        if (assignment.getDueTime().hour <= 12) {
            if (assignment.getDueTime().minute < 10) {
                dueWhen = assignment.getDueTime().hour + ":0" + assignment.getDueTime().minute + " AM";
                //setNotify(titleStr, dueWhen);
            } else {
                dueWhen = assignment.getDueTime().hour + ":" + assignment.getDueTime().minute + " AM";
            }
        } else {
            if (assignment.getDueTime().minute < 10) {
                dueWhen = (assignment.getDueTime().hour - 12) + ":0" + assignment.getDueTime().minute + " PM";
            } else {
                dueWhen = (assignment.getDueTime().hour - 12) + ":" + assignment.getDueTime().minute + " PM";
                //setNotify(titleStr, dueWhen);
            }
        } student.setIsChanged(true);

        alarmMessage = "Due: " + dueWhen + "\t\t" + assignment.getTitle();
        Log.d("MyActivity", "Alarm On");
        TimePicker alarmTimePicker = notifyTime;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
        Intent myIntent = new Intent(AddAssignment.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(AddAssignment.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

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
        long time = (minute + ((hour + 7) * 60)) * 60 * 1000;
        return time;
    }

    public long convertDate(long day, long month, long year) {
        long time = (day * 24 * 60 * 60);
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
