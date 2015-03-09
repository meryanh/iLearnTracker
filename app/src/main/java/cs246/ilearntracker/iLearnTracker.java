package cs246.ilearntracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class iLearnTracker extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "com.jbjust.FavoriteScripture.MESSAGE";
    static Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_learn_tracker);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void clickClassButton(){

    }

    public void share(View view) {

        Intent intent = new Intent(this, AddClass.class);

        String message = "testing";
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);


        //Intent intent = new Intent(this, Output.class);
        //EditText book = (EditText) findViewById(R.id.bookEnter);
       //EditText chapter = (EditText) findViewById(R.id.chapterEnter);
        //EditText verse = (EditText) findViewById(R.id.verseEnter);
        //String scripture = book.getText().toString() + " " + chapter.getText().toString() + ":" + verse.getText().toString() + "\n";
        //intent.putExtra(EXTRA_MESSAGE, scripture);
        //startActivity(intent);
    }


}
