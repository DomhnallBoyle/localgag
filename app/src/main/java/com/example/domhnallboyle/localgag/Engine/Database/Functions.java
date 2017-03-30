package com.example.domhnallboyle.localgag.Engine.Database;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.R;
import com.example.domhnallboyle.localgag.Screens.Fragments.MailFragment;
import com.example.domhnallboyle.localgag.Screens.Fragments.MainScreenActivityFragment;
import com.example.domhnallboyle.localgag.Screens.Fragments.ProfileFragment;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DomhnallBoyle on 13/08/2016.
 */
public class Functions extends AsyncTask<String, Void, String> {

    private Context context;
    private FragmentManager fragmentManager;
    private FunctionType functionType;
    private CircularProgressView progressView;

    public Functions(Context context, FragmentManager fragmentManager, FunctionType functionType,
                     CircularProgressView progressView)
    {
        this.context = context;
        this.functionType = functionType;
        this.progressView = progressView;
        this.fragmentManager = fragmentManager;
    }

    protected void onPreExecute(){
        if (progressView != null)
        {
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
        }
    }

    @Override
    protected String doInBackground(String... params)
    {
        String username = params[0];

        String link, data, result;
        BufferedReader bufferedReader;

        try
        {
            data = "?function_type=" + URLEncoder.encode(functionType.toString().toLowerCase(), "UTF-8");
            data += "&username=" + URLEncoder.encode(username, "UTF-8");

            switch (functionType)
            {
                case GET_PROFILE:
                    break;
                case SAVE_PROFILE:
                    data += "&firstname=" + URLEncoder.encode(params[1], "UTF-8");
                    data += "&lastname=" + URLEncoder.encode(params[2], "UTF-8");
                    data += "&gender=" + URLEncoder.encode(params[3], "UTF-8");
                    data += "&bio=" + URLEncoder.encode(params[4], "UTF-8");
                    data += "&description=" + URLEncoder.encode(params[5], "UTF-8");
                    break;
                case GET_POSTS:
                    break;
                case VOTE:
                    data += "&post_id=" + URLEncoder.encode(params[1], "UTF-8");
                    data += "&votes=" + URLEncoder.encode(params[2], "UTF-8");
                    break;
                case COMMENT:
                    break;
                case WRITE_STATUS:
                    data += "&status=" + URLEncoder.encode(params[1], "UTF-8");
                    data += "&status_type=" + URLEncoder.encode(params[2], "UTF-8");
                    break;
                case GET_FRIENDS:
                    break;
                default:
                    break;
            }

            link = "http://www.localgag.x10host.com/functions.php" + data;
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
        if (progressView != null)
        {
            progressView.stopAnimation();
            progressView.setVisibility(View.INVISIBLE);
        }

        if (result != null)
        {
            try
            {
                //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                JSONObject jsonObject = new JSONObject(result);

                switch(functionType)
                {
                    case GET_PROFILE:
                        ProfileFragment profileFragment = new ProfileFragment(jsonObject, fragmentManager, progressView);
                        this.fragmentManager.beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                        break;
                    case SAVE_PROFILE:
                        break;
                    case GET_MESSAGES:
                        break;
                    case GET_POSTS:
                        MainScreenActivityFragment mainscreenFragment = new MainScreenActivityFragment(jsonObject);
                        this.fragmentManager.beginTransaction().replace(R.id.fragment_container, mainscreenFragment).commit();
                        break;
                    case GET_FRIENDS:
                        MailFragment mailFragment = new MailFragment(jsonObject);
                        this.fragmentManager.beginTransaction().replace(R.id.messaging_fragment_container, mailFragment).commit();
                        break;
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
