package cs246.ilearntracker;

import android.content.Intent;
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

import java.util.Date;


public class AddAssignment extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_assignment);
        Spinner dropdown = (Spinner)findViewById(R.id.classSelect);
        String[] items = new String[]{"CS124", "CS165", "FDSCI108", "CIT160"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
        assignment.setDueTime(time);
        Spinner classGetter = (Spinner) findViewById(R.id.classSelect);
        String assignClassName = classGetter.getSelectedItem().toString();
        /*Something to add this to the list of assignments in the class*/
        Intent intent = new Intent(this, iLearnTracker.class);
        startActivity(intent);

    }

    public long convertTime(long hour, long minute) {
        long time = (minute + (hour * 60)) * 60 * 1000;

        return time;
    }
}
