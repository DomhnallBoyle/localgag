package com.example.domhnallboyle.localgag.Engine.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by DomhnallBoyle on 20/08/2016.
 */
public class UploadImage extends AsyncTask<Bitmap, Void, String> {

    private Context context;
    private CircularProgressView progressView;
    private String upload_type, comment_set_id;

    public UploadImage(Context context, CircularProgressView progressView, String upload_type, String comment_set_id)
    {
        this.context = context;
        this.progressView = progressView;
        this.upload_type = upload_type;
        this.comment_set_id = comment_set_id;
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
    protected String doInBackground(Bitmap... params) {

        String result = "";
        Bitmap bitmap = params[0];
        String username = DeviceStorage.getStringSharedPreferences(context, "Username", "");
        String link = "http://www.localgag.x10host.com/functions.php?function_type=upload_image";

        try
        {
            link += "&upload_type=" + URLEncoder.encode(upload_type, "UTF-8");
        }
        catch(Exception e)
        {
            return new String("Exception: " + e.getMessage());
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inPurgeable = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos);
        byte[] byteImage_photo  = baos.toByteArray();
        String encodedImage =Base64.encodeToString(byteImage_photo,Base64.DEFAULT);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("base64", encodedImage));

        if (upload_type.equalsIgnoreCase("profile_picture"))
        {
            nameValuePairs.add(new BasicNameValuePair("image_name", username + ".jpg"));
        }
        else
        {
            nameValuePairs.add(new BasicNameValuePair("image_name", username + "-" + comment_set_id + ".jpg"));
        }


        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(link);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            result = EntityUtils.toString(response.getEntity());
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
        if (progressView != null)
        {
            progressView.stopAnimation();
            progressView.setVisibility(View.INVISIBLE);
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }
}
