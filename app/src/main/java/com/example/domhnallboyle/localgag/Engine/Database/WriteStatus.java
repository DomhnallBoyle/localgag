package com.example.domhnallboyle.localgag.Engine.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DomhnallBoyle on 09/10/2016.
 */
public class WriteStatus extends AsyncTask<String, Void, String>{

    private Context context;
    private CircularProgressView progressView;
    private Bitmap bitmap;

    public WriteStatus(Context context, CircularProgressView progressView, Bitmap bitmap)
    {
        this.context = context;
        this.progressView = progressView;
        this.bitmap = bitmap;
    }

    protected void onPreExecute()
    {
        if (progressView != null)
        {
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
        }
    }

    @Override
    protected String doInBackground(String... params) {

        String username = params[0];

        String link, data = "", result;
        BufferedReader bufferedReader;

        try {
            data += "&username=" + URLEncoder.encode(username, "UTF-8");
            data += "&status=" + URLEncoder.encode(params[1], "UTF-8");
            data += "&status_type=" + URLEncoder.encode(params[2], "UTF-8");

            link = "http://www.localgag.x10host.com/functions.php?function_type=write_status" + data;
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = bufferedReader.readLine();

            return result;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if (progressView != null)
        {
            progressView.stopAnimation();
            progressView.setVisibility(View.INVISIBLE);
        }

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if (result != null)
        {
            if (!result.equalsIgnoreCase("-1")) {
                new UploadImage(context, progressView, "status_picture", result).execute(bitmap);
            }
        }
        else
        {
            Toast.makeText(context, "Couldn't get the JSON data", Toast.LENGTH_LONG).show();
        }

    }
}
