package cs246.ilearntracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class WebActivity extends ActionBarActivity {

    private WebView mWebView;
    final Context myApp = this;
    private String HTMLData;

    @JavascriptInterface
    public void processHTML(String html) {
        HTMLData = html;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.byui);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff326ba9));
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(this, "HTMLOUT");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'"+
                        "+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });

        /* load iLearn login page */
        mWebView.loadUrl("https://secure.byui.edu/cas/login?service="+
                "https://web.byui.edu/Services/Login/"+
                "?RedirectURL=https%253a%252f%252filearn.byui.edu%252f");
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    /**
     * Saves the HTML from the specified page
     * @param view The place pressed to save the HTML
     */
    public void saveHTML(View view){
        if(HTMLData == null){
            Toast.makeText(getApplicationContext(),
                    "Unable to locate assignments on current page.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if(!(HTMLData.contains("<div due-soon-list="))){
            Toast.makeText(getApplicationContext(),
                    "Unable to locate assignments on current page.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Student.getInstance().setHTMLData(HTMLData);
        Student.getInstance().parseHTML();
        //super.onBackPressed();
        Intent intent = new Intent(this, iLearnTracker.class);
        startActivity(intent);
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
}
