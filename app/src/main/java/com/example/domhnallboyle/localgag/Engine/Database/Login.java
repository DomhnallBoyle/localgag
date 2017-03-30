package com.example.domhnallboyle.localgag.Engine.Database;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Screens.Activities.MainScreenActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;

/**
 * Created by DomhnallBoyle on 23/07/2016.
 */
public class Login extends AsyncTask<String, Void, String> {

    private Context context;
    private CircularProgressView progressView;

    public Login(Context context, CircularProgressView progressView)
    {
        this.context = context.getApplicationContext();
        this.progressView = progressView;
    }

    protected void onPreExecute(){
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        String username_email = arg0[0];
        String password = arg0[1];

        String link, data, result;
        BufferedReader bufferedReader;

        try
        {
            data = "?username_email=" + URLEncoder.encode(username_email, "UTF-8");
            data += "&password=" + URLEncoder.encode(password, "UTF-8");

            link = "http://www.localgag.x10host.com/login.php" + data;
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
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        if (jsonStr != null)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(jsonStr);

                String query_result = jsonObject.getString("query_result");
                String username = jsonObject.getString("username");
                String name = jsonObject.getString("name");
                String bio = jsonObject.getString("bio");
                if (query_result.equals("SUCCESS"))
                {
                    DeviceStorage.setValueSharedPreferences(context, "Session", 1);
                    DeviceStorage.setValueSharedPreferences(context, "Username", username);
                    DeviceStorage.setValueSharedPreferences(context, "Name", name);
                    DeviceStorage.setValueSharedPreferences(context, "Bio", bio);
                    Toast.makeText(context, "Login successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if (query_result.equals("FAILURE"))
                {
                    Toast.makeText(context, "Incorrect username or password!", Toast.LENGTH_LONG).show();
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
