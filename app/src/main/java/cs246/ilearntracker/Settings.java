package cs246.ilearntracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.NumberPicker;


public class Settings extends ActionBarActivity {
    //public static final String PREFS_NAME = "myPrefsFile";
    static Student student;
    private static final String ACTIVITY_VARS = "Settings Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_settings);
        NumberPicker np1 = (NumberPicker) findViewById(R.id.notifyInt);
        np1.setMinValue(1);
        np1.setMaxValue(24);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.refreshInt);
        np2.setMinValue(1);
        np2.setMaxValue(24);
        NumberPicker np3 = (NumberPicker) findViewById(R.id.cleanUpInt);
        np3.setMinValue(1);
        np3.setMaxValue(14);
        Log.i(ACTIVITY_VARS, "Initialized Activity settings.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void save() {

        CheckBox notify = (CheckBox) findViewById(R.id.notify);
        NumberPicker notifyInteger = (NumberPicker) findViewById(R.id.notifyInt);
        NumberPicker refreshInteger = (NumberPicker) findViewById(R.id.refreshInt);
        NumberPicker cleanInteger = (NumberPicker) findViewById(R.id.cleanUpInt);
        Log.i(ACTIVITY_VARS, "pulled variables from the activity");

        student.setNotify(notify.equals(true));
        student.setNotifyInt(notifyInteger.getValue() * 3600);
        student.setRefresh(refreshInteger.getValue() * 1);
        student.setCleanUp(cleanInteger.getValue() * 1);

        student.saveSettings();

        Intent intent = new Intent(this, iLearnTracker.class);
        startActivity(intent);
    }
}
