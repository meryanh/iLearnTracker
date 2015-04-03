package cs246.ilearntracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The user interface for when they are adding a class to there tracker
 */
public class AddClass extends ActionBarActivity {
    private static final String ADD_CLASS = "Add a Class Activity";
    private Student student = Student.getInstance();
    private int color = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ADD_CLASS, "You have entered the class interface");

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.byui);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff326ba9));
        //Spinner dropdown = (Spinner)findViewById(R.id.colorSelect);
        //String[] items = new String[]{"Red", "Yellow", "Blue", "Orange", "Purple", "Green", "Pink", "Brown"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        //dropdown.setAdapter(adapter);

        // Set up color picker:
        Button colorPickerItem = (Button)findViewById(R.id.color1);
        colorPickerItem.setBackgroundColor(Color.rgb(0,255,0));        //Green
        colorPickerItem = (Button)findViewById(R.id.color2);
        colorPickerItem.setBackgroundColor(Color.rgb(255,0,0));        //Red
        colorPickerItem = (Button)findViewById(R.id.color3);
        colorPickerItem.setBackgroundColor(Color.rgb(0,0,255));        //Blue
        colorPickerItem = (Button)findViewById(R.id.color4);
        colorPickerItem.setBackgroundColor(Color.rgb(0,255,255));      //Cyan
        colorPickerItem = (Button)findViewById(R.id.color5);
        colorPickerItem.setBackgroundColor(Color.rgb(255,0,255));      //Purple
        colorPickerItem = (Button)findViewById(R.id.color6);
        colorPickerItem.setBackgroundColor(Color.rgb(255,100,0));      //Orange
        colorPickerItem = (Button)findViewById(R.id.color7);
        colorPickerItem.setBackgroundColor(Color.rgb(255,255,0));      //Yellow
        colorPickerItem = (Button)findViewById(R.id.color8);
        colorPickerItem.setBackgroundColor(Color.rgb(100,100,100));    //Gray
        colorPickerItem = (Button)findViewById(R.id.color9);
        colorPickerItem.setBackgroundColor(Color.rgb(1,1,1));          //Black
    }

    /**
     * set the "color" variable of this to calling view.
     * @param view the clicked colorpicker item
     */
    public void getColor(View view){
        ColorDrawable theColor = (ColorDrawable)view.getBackground();
        color = theColor.getColor();
        add(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_class, menu);
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
     * This occurs when the user selects to add a new class and saves this class to the list
     * @param view The area you have clicked on
     */
    public void add(View view) {
        Log.i(ADD_CLASS, "You are adding a class!");
        if(color == 0){
            Log.w(ADD_CLASS, "Attempted to create a class without selecting a color.");
        }
        Class newClass;

        EditText titleGetter = (EditText) findViewById(R.id.nameEnter);
        String titleStr = titleGetter.getText().toString();

        if(titleStr.equals("")){
            Toast.makeText(getApplicationContext(), "Enter a class name first.",
                    Toast.LENGTH_SHORT).show();
            Log.i(ADD_CLASS, "Attempted to create a class without adding a name.");
            return;
        }

        newClass = new Class(titleStr, color);
        student.addToList(newClass);

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
