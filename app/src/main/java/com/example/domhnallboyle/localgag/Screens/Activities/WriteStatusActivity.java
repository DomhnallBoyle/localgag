package com.example.domhnallboyle.localgag.Screens.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Database.WriteStatus;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.example.domhnallboyle.localgag.R;

import java.io.IOException;

public class WriteStatusActivity extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int RESULT_LOAD_IMAGE = 1;

    private Dialog dialog;
    private EditText write_status;
    private Button create, back;
    private ImageButton get_picture;
    private Bitmap status_photo;
    private TextView username, character_count;
    private int max_chars = 200;
    private Button take_picture, from_gallery, cancel;

    public WriteStatusActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_status);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        take_picture = (Button)dialog.findViewById(R.id.take_picture);
        from_gallery = (Button)dialog.findViewById(R.id.from_gallery);
        cancel = (Button)dialog.findViewById(R.id.dialog_cancel);

        write_status = (EditText)findViewById(R.id.status_text);
        create = (Button)findViewById(R.id.create);
        back = (Button)findViewById(R.id.back_from_create);
        get_picture = (ImageButton)findViewById(R.id.camera);
        username = (TextView)findViewById(R.id.write_status_username);
        character_count = (TextView)findViewById(R.id.number_chars);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = write_status.getText().length();
                if (length <= max_chars && length > 0)
                {
                    String username = DeviceStorage.getStringSharedPreferences(getApplicationContext(), "Username", "");
                    //create status
                    if (status_photo != null)
                    {
                        new WriteStatus(getApplicationContext(), null, status_photo).execute(username, write_status.getText().toString(), "STATUS_WITH_IMAGE");
                    }
                    else
                    {
                        new WriteStatus(getApplicationContext(), null, null).execute(username, write_status.getText().toString(), "STATUS_WITHOUT_IMAGE");
                    }
                    finish();
                }
                else
                {
                    //error message
                    Toast.makeText(getApplicationContext(), "Cannot create an empty status!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        get_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, CAMERA_REQUEST);
            }
        });

        from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent gallery_intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery_intent, RESULT_LOAD_IMAGE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        write_status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int no_left = max_chars - s.length();
                character_count.setText(String.valueOf(no_left));
                if (no_left < 0)
                    character_count.setTextColor(Color.parseColor("#FF0000"));
                else
                    character_count.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.setText(DeviceStorage.getStringSharedPreferences(getApplicationContext(), "Username", null));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null)
        {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                status_photo = (Bitmap) data.getExtras().get("data");
                get_picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK)
            {
                try
                {
                    Uri chosenImageUri = data.getData();
                    status_photo = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenImageUri);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            get_picture.setImageBitmap(status_photo);
        }
    }

    @Override
    public void onBackPressed() {
        // back pressed
    }
}
