package com.example.domhnallboyle.localgag.Engine.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DomhnallBoyle on 24/07/2016.
 */
public class Signup_Functions extends AsyncTask<String, Void, String> {


    public Signup_Functions()
    {

    }

    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String... args)
    {
        String text = args[0];
        String type = args[1];

        String link, data, result = "";
        BufferedReader bufferedReader;

        try
        {
            data = "?type=" + URLEncoder.encode(type, "UTF-8");
            data += "&text=" + URLEncoder.encode(text, "UTF-8");

            link = "http://www.localgag.x10host.com/signup_functions.php" + data;
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = bufferedReader.readLine();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {

    }
}
