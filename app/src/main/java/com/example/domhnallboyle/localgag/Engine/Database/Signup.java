package com.example.domhnallboyle.localgag.Engine.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DomhnallBoyle on 23/07/2016.
 */
public class Signup extends AsyncTask<String, Void, String> {

    private Context context;
    private CircularProgressView progressView;

    public Signup(Context context, CircularProgressView progressView)
    {
        this.context = context;
        this.progressView = progressView;
    }

    protected void onPreExecute(){
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        String username = arg0[0];
        String email = arg0[1];
        String password = arg0[2];
        String firstname = arg0[3];
        String lastname = arg0[4];
        String date_created = arg0[5];

        String link, data, result;
        BufferedReader bufferedReader;

        try
        {
            data = "?username=" + URLEncoder.encode(username, "UTF-8");
            data += "&email=" + URLEncoder.encode(email, "UTF-8");
            data += "&password=" + URLEncoder.encode(password, "UTF-8");
            data += "&firstname=" + URLEncoder.encode(firstname, "UTF-8");
            data += "&lastname=" + URLEncoder.encode(lastname, "UTF-8");
            data += "&date_created=" + URLEncoder.encode(date_created, "UTF-8");

            link = "http://www.localgag.x10host.com/signup.php" + data;
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        }
        catch(Exception e)
        {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        progressView.stopAnimation();
        progressView.setVisibility(View.INVISIBLE);
        String jsonStr = result;
        if (jsonStr != null)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(jsonStr);

                String query_result = jsonObject.getString("query_result");
                if (query_result.equals("SUCCESS"))
                {
                    Toast.makeText(context, "Signup successful!", Toast.LENGTH_LONG).show();
                }
                else if (query_result.equals("FAILURE"))
                {
                    Toast.makeText(context, "Signup failed!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context, "Couldn't connect to remote database!", Toast.LENGTH_LONG).show();
                }
            }
            catch(JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(context, "Couldn't get the JSON data", Toast.LENGTH_LONG).show();
        }
    }

}
