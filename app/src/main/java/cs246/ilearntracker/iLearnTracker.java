package cs246.ilearntracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class iLearnTracker extends ActionBarActivity {
    private Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_i_learn_tracker);
        //student.loadSettings();
        //student.loadClasses();

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
            goToSettings();
        }
        else if (id == R.id.action_addClass) {
            goToAddClass();
        }

        return super.onOptionsItemSelected(item);
    }


    public void clickClassButton(){

    }

    public void share(View view) {
        Intent intent = new Intent(this, AddAssignment.class);
        startActivity(intent);
    }

    public void goToSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    public void goToAddClass() {
        Intent intent = new Intent(this, AddClass.class);
        startActivity(intent);
    }
    public void refresh(View view) {

        int sizeList = student.classesList.size();
        for (int i = 0; i < sizeList; i++) {
            Class testClass = student.classesList.get(i);
            System.out.println(testClass.getClassName());
        }
    }

}
