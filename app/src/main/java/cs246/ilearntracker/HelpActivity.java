package cs246.ilearntracker;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class HelpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.byui);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff326ba9));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
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

    /**
     *
     * @param view The clicked button.
     */
    public void overviewClicked(View view)
    {
        RelativeLayout lView = (RelativeLayout) findViewById(R.id.overviewLayout);
        lView.setVisibility(View.VISIBLE);
        lView = (RelativeLayout) findViewById(R.id.toolbarLayout);
        lView.setVisibility(View.GONE);

        return;
    }

    /**
     *
     * @param view The clicked button.
     */
    public void toolbarsClicked(View view)
    {
        RelativeLayout lView = (RelativeLayout) findViewById(R.id.toolbarLayout);
        lView.setVisibility(View.VISIBLE);
        lView = (RelativeLayout) findViewById(R.id.overviewLayout);
        lView.setVisibility(View.GONE);
        return;
    }
}
