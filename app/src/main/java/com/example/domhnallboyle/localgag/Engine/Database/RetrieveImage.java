package com.example.domhnallboyle.localgag.Engine.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Objects.ImageType;
import com.example.domhnallboyle.localgag.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DomhnallBoyle on 20/08/2016.
 */
public class RetrieveImage extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private ImageView imageView;
    private CircularProgressView progressView;
    private ImageType imageType;

    public RetrieveImage(Context context, ImageView imageView, CircularProgressView progressView, ImageType imageType)
    {
        this.context = context;
        this.imageView = imageView;
        this.progressView = progressView;
        this.imageType = imageType;
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
    protected Bitmap doInBackground(String... params) {

        String url = params[0];

        try
        {
            URL location = new URL(url);
            HttpURLConnection con =  (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream = location.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
            else
            {
                if (imageType == ImageType.PROFILE)
                   return BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_face);
                else
                    return BitmapFactory.decodeResource(context.getResources(), R.drawable.info);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (progressView != null)
        {
            progressView.stopAnimation();
            progressView.setVisibility(View.INVISIBLE);
        }
        imageView.setImageBitmap(bitmap);
    }
}
