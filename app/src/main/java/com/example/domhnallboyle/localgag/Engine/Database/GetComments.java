package com.example.domhnallboyle.localgag.Engine.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Objects.StatusType;
import com.example.domhnallboyle.localgag.R;
import com.example.domhnallboyle.localgag.Screens.Fragments.ComplexStatusFragment;
import com.example.domhnallboyle.localgag.Screens.Fragments.SimpleStatusFragment;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DomhnallBoyle on 03/09/2016.
 */
public class GetComments extends AsyncTask<Void, Void, String> {

    private Context context;
    private FragmentManager fragmentManager;
    private com.example.domhnallboyle.localgag.Engine.Objects.Status status;
    private CircularProgressView progressView;

    public GetComments(Context context, FragmentManager fragmentManager, com.example.domhnallboyle.localgag.Engine.Objects.Status status, CircularProgressView progressView)
    {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.status = status;
        this.progressView = progressView;
    }

    @Override
    protected String doInBackground(Void... params) {

        String link, data, result;
        BufferedReader bufferedReader;

        try
        {
            data = "?function_type=" + URLEncoder.encode("get_comments", "UTF-8");
            data += "&comment_set_id=" + URLEncoder.encode("" + status.getComment_set_id(), "UTF-8");

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
                Fragment fragment;
                if (status.getStatusType() == StatusType.STATUS_WITH_IMAGE)
                    fragment = new ComplexStatusFragment(status, jsonObject);
                else
                    fragment = new SimpleStatusFragment(status, jsonObject);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
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
