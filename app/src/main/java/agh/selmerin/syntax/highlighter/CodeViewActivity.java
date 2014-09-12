package agh.selmerin.syntax.highlighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class CodeViewActivity extends Activity {

    WebView webView;
    String extension;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);

        Intent intent = getIntent();
        String filePath = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
        System.out.println("CodeView " + filePath);
        System.out.println("CodeView " + filePath.endsWith(".jpg"));
        extension = "";
        fileName = "";

        int i = filePath.lastIndexOf('.');
        int p = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));

        if (i > p) {
            extension = filePath.substring(i + 1);
            fileName = filePath.substring(p + 1);
        }
        System.out.println("CodeView " + extension);
        System.out.println("CodeView " + fileName);
        TextView textViewName = (TextView) findViewById(R.id.textViewFileName);
        System.out.println(textViewName);
        textViewName.setText(fileName);

        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings s = webView.getSettings();
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        s.setUseWideViewPort(false);
        s.setAllowFileAccess(true);
        s.setBuiltInZoomControls(true);
        s.setLightTouchEnabled(true);
        s.setLoadsImagesAutomatically(true);
        s.setSupportZoom(true);
        s.setSupportMultipleWindows(true);
        s.setJavaScriptEnabled(true);

        highlight(filePath);




//        webView.loadData(customHtml, "text/html", "UTF-8");
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
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

    public void searchFraze(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Search fraze");
        final EditText editText = new EditText(this);
        editText.setHint("Enter text to find");
        alertDialogBuilder
                .setView(editText)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        String fraze = editText.getText().toString();
                        webView.findAll(fraze);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.show();
    }

    public void clearSearch(View view) {
        webView.clearMatches();
    }

    public void nextResult(View view) {
        webView.findNext(true);
    }

    private void highlight(String filePath){
        File f = new File(filePath);
        System.out.println(f);
        long length = f.length();
        System.out.println(length);
        byte[] array = new byte[(int)length];

        InputStream is;
        try {
            is = new FileInputStream(f);
            is.read(array);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String sourceString = new String(array);
        System.out.println(sourceString);
        setTitle("SyntaX Highlighter");
        AssetManager assetManager = getAssets();
        String css = " ";
        try {
            InputStream assetIn = assetManager.open("prettify.css");
            css = convertStreamToString(assetIn);
            System.out.println(css);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("CSS:");
        System.out.println(css);
        String customHtml = "<html><head><style type='text/css'>" + css +"</style><title>" + fileName + "</title>";
        //TODO: zamienić pettify z ogólnego na poszczególne języki

//        customHtml += "<link href='file:///android_assets/prettify.css' rel='stylesheet' type='text/css'/> ";
        String script = " ";
        try {
            InputStream assetIn = assetManager.open("prettify.js");
            script = convertStreamToString(assetIn);
            System.out.println(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
        customHtml += "<script>" +
                script
                + "</script> ";
        customHtml +=  "</head><body onload='prettyPrint()'><code class='prettyprint'>";
        sourceString = sourceString.replace("\n", "<br>");
        customHtml += sourceString;
        customHtml += "</body></html>";
        System.out.println("PLIK HTML");
        System.out.println(customHtml);
        System.out.println("KONIEC HTML");

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadDataWithBaseURL("file:///android_asset/", customHtml,"text/html", "", "");
    }
}
