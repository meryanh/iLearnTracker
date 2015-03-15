package cs246.ilearntracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class AddClass extends ActionBarActivity {
    private static final String ADD_CLASS = "Add a Class Activity";
    private Student student = Student.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ADD_CLASS, "You have entered the class interface");

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_class);
        Spinner dropdown = (Spinner)findViewById(R.id.colorSelect);
        String[] items = new String[]{"Red", "Yellow", "Blue", "Orange", "Purple", "Green", "Pink", "Brown"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void add(View view) {
        Log.i(ADD_CLASS, "You are adding a class!");
        Class newClass;
        Integer col = 0x000000;
        EditText titleGetter = (EditText) findViewById(R.id.nameEnter);
        String titleStr = titleGetter.getText().toString();
        Spinner colorGetter = (Spinner) findViewById(R.id.colorSelect);
        String color = colorGetter.getSelectedItem().toString();

        if (color == "Red") {
            col = Color.RED;//0xff0000;
        }
        else if (color == "Yellow") {
            col = Color.YELLOW;//0xffff00;
        }
        else if (color == "Blue") {
            col = Color.BLUE;//0x0000ff;
        }
        else if (color == "Orange") {
            col = Color.YELLOW;//0xff8000;
        }
        else if (color == "Purple") {
            col = Color.CYAN;//0x660066;
        }
        else if (color == "Green") {
            col = Color.GREEN;//0x00ff00;
        }
        else if (color == "Pink") {
            col = Color.MAGENTA;//0xff33ff;
        }
        else if (color == "Brown") {
            col = Color.DKGRAY;//0x663300;
        }
        else {
            Log.e(ADD_CLASS, "You are adding a color that is not an option!");
        }
        newClass = new Class(titleStr, col);
        student.addToList(newClass);
        Intent intent = new Intent(this, iLearnTracker.class);
        startActivity(intent);
    }
}
