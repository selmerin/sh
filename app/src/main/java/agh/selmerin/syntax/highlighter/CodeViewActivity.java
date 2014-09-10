package agh.selmerin.syntax.highlighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;


public class CodeViewActivity extends Activity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);

        Intent intent = getIntent();
        String filePath = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
        System.out.println("CodeView "+filePath);
        System.out.println("CodeView "+filePath.endsWith(".jpg"));
        String extension = "";
        String fileName = "";

        int i = filePath.lastIndexOf('.');
        int p = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));

        if (i > p) {
            extension = filePath.substring(i+1);
            fileName = filePath.substring(p+1);
        }
        System.out.println("CodeView "+extension);
        System.out.println("CodeView "+fileName);
        TextView textViewName = (TextView)findViewById(R.id.textViewFileName);
        System.out.println(textViewName);
        textViewName.setText(fileName);

        webView = (WebView)findViewById(R.id.webView);
        

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.code_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
