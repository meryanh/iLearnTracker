package cs246.ilearntracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;


public class Settings extends ActionBarActivity {
    //private static Student student;
    private static final String ACTIVITY_VARS = "Settings Activity";
    public static final String PREFS_NAME = "iTracker.conf";
    private int hoursNotBefore;
    private int refreshTime;
    private int cleanUp;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.byui);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff326ba9));


        NumberPicker np1 = (NumberPicker) findViewById(R.id.notifyInt);
        np1.setMinValue(1);
        np1.setMaxValue(24);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.refreshInt);
        np2.setMinValue(1);
        np2.setMaxValue(24);
        NumberPicker np3 = (NumberPicker) findViewById(R.id.cleanUpInt);
        np3.setMinValue(1);
        np3.setMaxValue(14);
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);
        hoursNotBefore = pref.getInt("beforeDue", 1);
        np1.setValue(hoursNotBefore);
        refreshTime = pref.getInt("refresh", 1);
        np2.setValue(refreshTime);
        cleanUp = pref.getInt("cleanUp", 1);
        np3.setValue(cleanUp);

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
     * To save the settings specified by the user
     * @param view the button pressed to save the settings
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void save(View view) {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();

        /* Save how many hours before to notify */
        NumberPicker notifyInteger = (NumberPicker) findViewById(R.id.notifyInt);
        hoursNotBefore = notifyInteger.getValue();
        editor.putInt("beforeDue", hoursNotBefore);

        /* Save how often to refresh the homework */
        NumberPicker refreshInteger = (NumberPicker) findViewById(R.id.refreshInt);
        refreshTime = refreshInteger.getValue();
        editor.putInt("refresh", refreshTime);

        /* Save how often to erase finished assignments */
        NumberPicker cleanInteger = (NumberPicker) findViewById(R.id.cleanUpInt);
        cleanUp = cleanInteger.getValue();
        editor.putInt("cleanUp", cleanUp);
        editor.commit();

        Intent intent = new Intent(this, iLearnTracker.class);
        startActivity(intent);
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
