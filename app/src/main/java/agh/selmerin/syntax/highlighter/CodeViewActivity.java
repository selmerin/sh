package agh.selmerin.syntax.highlighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import static android.text.Html.escapeHtml;

public class CodeViewActivity extends Activity {

    public final static String EXTRA_MESSAGE = "agh.selmerin.syntax.highlighter.MESSAGE";

    WebView webView;
    String extension;
    String fileName;
    String filePath;

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
        filePath = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
        String filePath, host = null;
        boolean isUrl = false;
        if (intent.hasExtra(MenuActivity.EXTRA_MESSAGE))
            filePath = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
        else {
            host = intent.getData().getHost();
            filePath = intent.getData().getPath();
        }
        if(host != null) {
            if(host.contains("github")){
                filePath = filePath.replace("blob","raw");
                filePath = "https://" + host + filePath;
                isUrl = true;
            }
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
        s.setDomStorageEnabled(true);
        s.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d("MyApplication", cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId());
                return true;
            }
        });

        if(extension.equals("c")){
            highlightC(filePath, isUrl);
        }else{
            highlight(filePath, isUrl);
        }

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

    private String readFile(String filePath, boolean isUrl){
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
        return sourceString;
    }

    private void highlight(String filePath, boolean isUrl) {
        String sourceString = readFile(filePath, isUrl);
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
//        htmlPage.append("<html><head><style type='text/css'>" + css + "</style><title>" + fileName + "</title>");
        htmlPage.append("<html><head><link href='prettify.css' rel='stylesheet' type='text/css'/><title>" + fileName + "</title>");
        //TODO: zamienić pettify z ogólnego na poszczególne języki

//        customHtml += "<link href='file:///android_assets/prettify.css' rel='stylesheet' type='text/css'/> ";
        String script = " ";
        try {
            InputStream assetIn = assetManager.open("prettify.js");
            script = convertStreamToString(assetIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        htmlPage.append("<script src='prettify.js'>");
//        htmlPage.append(script);
        htmlPage.append("</script>");
        htmlPage.append("</head><body onload='prettyPrint()'><code class='prettyprint'>");
        sourceString = escapeHtml(sourceString);
        sourceString = sourceString.replace("&#13;&#10;", "<br />");
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

    public void editFile(View view) {

        System.out.println(filePath);
        Intent open = new Intent(this, EditActivity.class);
        open.putExtra(EXTRA_MESSAGE, filePath);
        startActivity(open);
    }
    public List<String> specialRead(String filePath, boolean isUrl){
        BufferedReader br = null;
        String line;
        List<String> lineList = new LinkedList<String>();
        if(!isUrl) {
            try {
                br = new BufferedReader(new FileReader(filePath));

                while ((line = br.readLine()) != null) {
//                line = escapeHtml(line);
//                line = line.replace("&#13;&#10;", "\n");
                    lineList.add(line);

                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lineList;
        } else {
            //TODO: chce dostać tutaj listę stringów a nie jeden String potrzebne do Piotrka skryptu
            try {
                String s = new RetrieveFile().execute(filePath).get();
                System.out.println(s);
                return lineList;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        return lineList;
    }

    private void highlightC(String filePath, boolean isUrl) {
        String sourceString = readFile(filePath, isUrl);
        List<String> lineList = specialRead(filePath, isUrl);
        setTitle("SyntaX Highlighter");

        StringBuilder htmlPage = new StringBuilder();
        htmlPage.append("<html><head><title>" + fileName + "</title><link href='lib-prettify/prettify.css' rel='stylesheet' type='text/css'/>");
        htmlPage.append("<script src='lib-prettify/prettify.js'>");
        htmlPage.append("</script>");
        htmlPage.append("<script type='text/javascript' src='lib/jquery-2.1.1.min.js'></script>");
        htmlPage.append("<script src='lib-syntaX/parsers/c/grammar.js'></script>");
        htmlPage.append("<link href='lib-syntaX/syntax.css' rel='stylesheet' type='text/css'/>");
        htmlPage.append("<script src='lib-syntaX/syntax.js'></script>");
        htmlPage.append("<script src='lib-syntaX/line-generator.js'></script>");

        htmlPage.append("</head><body><code class='prettyprint'>");
//        sourceString = sourceString.replace("\n", "<br>");
        htmlPage.append("</code><script type='text/javascript'>");
        htmlPage.append("generateCode(");
//        htmlPage.append(sourceString);
        for(String line : lineList){
            htmlPage.append("'");
            htmlPage.append(line);
            htmlPage.append("'\n");
            htmlPage.append('+');
        }
        htmlPage.append("'");


        htmlPage.append("', function() { prettyPrint(); });");

        htmlPage.append("</script></body></html>");
        System.out.println("PLIK HTML: "+ htmlPage.toString());

        System.out.println("KONIEC HTML");

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadDataWithBaseURL("file:///android_asset/", htmlPage.toString(), "text/html", "", "");
    }
}
