package agh.selmerin.syntax.highlighter;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RetrieveFile extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in;
        try {
            URLConnection conn = new URL(strings[0]).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String string;
            while ((string = in.readLine()) != null) {
                sb.append(string + "\n");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
