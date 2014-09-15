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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

public class CodeViewActivity extends Activity {

    WebView webView;
    String extension;
    String fileName;

    List<String> extensions = fileExtension();

    private static List<String> fileExtension() {
        List<String> extensions = new LinkedList<String>();
        extensions.add("java");
        return extensions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);

        Intent intent = getIntent();
        String filePath, host = null;
        boolean isUrl = false;
        if (intent.hasExtra(MenuActivity.EXTRA_MESSAGE))
            filePath = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
        else {
            host = intent.getData().getHost();
            filePath = intent.getData().getPath();
        }
        if(host != null) {
            isUrl = true;
            if(host.contains("github"))
                filePath = filePath.replace("blob","raw");
            filePath = "https://" + host + filePath;
        }
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

//        if(!extensions.contains(extension)){
//            Intent menu = new Intent(this, MenuActivity.class);
//            startActivity(menu);
//            Toast.makeText(getApplicationContext(), "File extension not supported", Toast.LENGTH_SHORT).show();
//            return;
//        }

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

        highlight(filePath, isUrl);
//        javaPrettify(filePath, extension);
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

    private void highlight(String filePath, boolean isUrl) {
        System.out.println(filePath);
        String sourceString = null;
        if(!isUrl) {
            File f = new File(filePath);
            long length = f.length();
            System.out.println(length);
            byte[] array = new byte[(int) length];

            InputStream is;
            try {
                is = new FileInputStream(f);
                is.read(array);
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            sourceString = new String(array);
        } else {
            try {
                sourceString = new RetrieveFile().execute(filePath).get();
                System.out.println(sourceString);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        setTitle("SyntaX Highlighter");
        AssetManager assetManager = getAssets();
        String css = " ";
        try {
            InputStream assetIn = assetManager.open("prettify.css");
            css = convertStreamToString(assetIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder htmlPage = new StringBuilder();
        htmlPage.append("<html><head><style type='text/css'>" + css + "</style><title>" + fileName + "</title>");
        //TODO: zamienić pettify z ogólnego na poszczególne języki

//        customHtml += "<link href='file:///android_assets/prettify.css' rel='stylesheet' type='text/css'/> ";
        String script = " ";
        try {
            InputStream assetIn = assetManager.open("prettify.js");
            script = convertStreamToString(assetIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlPage.append("<script>");
        htmlPage.append(script);
        htmlPage.append("</script>");
        htmlPage.append("</head><body onload='prettyPrint()'><code class='prettyprint'>");
        sourceString = sourceString.replace("\n", "<br>");
        htmlPage.append(sourceString);
        htmlPage.append("</body></html>");
        System.out.println("PLIK HTML");
        System.out.println(htmlPage.toString());
        System.out.println("KONIEC HTML");

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadDataWithBaseURL("file:///android_asset/", htmlPage.toString(), "text/html", "", "");
    }


    Map<String, String> COLORS = buildColorsMap();
    String FONT_PATTERN = "<font color=\"#%s\">%s</font>";

    private void javaPrettify(String filePath, String extension) {
        Parser parser = new PrettifyParser();
        StringBuilder stringBuilder = new StringBuilder();
        File f = new File(filePath);
        System.out.println(f);
        long length = f.length();
        System.out.println(length);
        byte[] array = new byte[(int) length];

        InputStream is;
        try {
            is = new FileInputStream(f);
            is.read(array);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String sourceString = new String(array);
        sourceString = sourceString.replace("\n", "<br>");

        List<ParseResult> parseResults = parser.parse(extension, sourceString);
        for (ParseResult result : parseResults) {
            String type = result.getStyleKeys().get(0);
            String content = sourceString.substring(result.getOffset(), result.getOffset() + result.getLength());
            stringBuilder.append(String.format(FONT_PATTERN, getColor(type), content));
        }
        System.out.println("javaPrettify: " + stringBuilder.toString());

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadDataWithBaseURL("file:///android_asset/", stringBuilder.toString(), "text/html", "", "");
    }

    private String getColor(String type) {
        return COLORS.containsKey(type) ? COLORS.get(type) : COLORS.get("pln");
    }

    private static Map<String, String> buildColorsMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("typ", "87cefa");
        map.put("kwd", "00ff00");
        map.put("lit", "ffff00");
        map.put("com", "999999");
        map.put("str", "ff4500");
        map.put("pun", "eeeeee");
        map.put("pln", "ffffff");
        return map;
    }
}
