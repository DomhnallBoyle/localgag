package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Database.FunctionType;
import com.example.domhnallboyle.localgag.Engine.Database.Functions;
import com.example.domhnallboyle.localgag.Engine.Database.RetrieveImage;
import com.example.domhnallboyle.localgag.Engine.Database.UploadImage;
import com.example.domhnallboyle.localgag.Engine.Objects.ImageType;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.example.domhnallboyle.localgag.R;
import com.example.domhnallboyle.localgag.Screens.Fragments.ImageViewFragment;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private JSONObject jsonObject;
    private RelativeLayout fragment;
    private ImageView profileImage;
    private TextView profileName, profilePosts, profilePoints, profileUsername,
            profileGender, profileBio, profileDescription;
    private Button editProfile, saveProfile;
    private FragmentManager fragmentManager;
    private CircularProgressView progressView;
    private boolean isEditing = false;

    public ProfileFragment(JSONObject jsonObject, FragmentManager fragmentManager, CircularProgressView progressView)
    {
        this.jsonObject = jsonObject;
        this.fragmentManager = fragmentManager;
        this.progressView = progressView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_own_profile, container, false);

        fragment = (RelativeLayout)view.findViewById(R.id.fragment);
        profileImage = (ImageView)view.findViewById(R.id.profile_image);
        profileName = (EditText)view.findViewById(R.id.profile_name);
        profilePosts = (TextView)view.findViewById(R.id.profile_posts);
        profilePoints = (TextView)view.findViewById(R.id.profile_points);
        profileUsername = (TextView)view.findViewById(R.id.profile_username);
        profileGender = (EditText)view.findViewById(R.id.profile_gender);
        profileBio = (EditText)view.findViewById(R.id.profile_bio);
        profileDescription = (EditText)view.findViewById(R.id.profile_description);
        editProfile = (Button)view.findViewById(R.id.profile_edit);
        saveProfile = (Button)view.findViewById(R.id.profile_save);

        profileName.setEnabled(false);
        profileGender.setEnabled(false);
        profileBio.setEnabled(false);
        profileDescription.setEnabled(false);

        try
        {
            profileName.setText(jsonObject.getString("firstname") + " " + jsonObject.getString("lastname"));
            profilePosts.setText(jsonObject.getString("number_posts"));
            profilePoints.setText(jsonObject.getString("number_points"));
            profileUsername.setText("Username: " + jsonObject.getString("username"));
            if (jsonObject.getString("gender").equalsIgnoreCase("M"))
                profileGender.setText("Male");
            else
                profileGender.setText("Female");
            profileBio.setText(jsonObject.getString("bio"));
            profileDescription.setText(jsonObject.getString("description"));
            fragment.setBackgroundColor(Color.parseColor(jsonObject.getString("background_colour")));
            new RetrieveImage(getActivity().getApplicationContext(), profileImage, progressView, ImageType.PROFILE)
                    .execute("http://www.localgag.x10host.com/profile_images/"+jsonObject.getString("username")+".jpg");
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        profileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (isEditing)
                {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);
                }
                else
                {
                    ImageViewFragment imageViewFragment = new ImageViewFragment(fragmentManager, profileImage, progressView);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, imageViewFragment).commit();
                }
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isEditing = true;
                profileName.setEnabled(true);
                profileGender.setEnabled(true);
                profileBio.setEnabled(true);
                profileDescription.setEnabled(true);
                profileImage.setEnabled(true);
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO More validation needed here
                String fullname = profileName.getText().toString();
                String[] splitname = fullname.split(" ");
                String gender = profileGender.getText().toString();
                String bio = profileBio.getText().toString();
                String description = profileDescription.getText().toString();
                //String imageName = DeviceStorage.getStringSharedPreferences(getActivity().getApplicationContext(), "Username", "") + ".jpg";
                if (gender.equalsIgnoreCase("Male"))
                    gender = "M";
                else if (gender.equalsIgnoreCase("Female"))
                    gender = "F";
                if (splitname.length == 2) {
                    new Functions(getActivity().getApplicationContext(), fragmentManager, FunctionType.SAVE_PROFILE, progressView)
                            .execute(DeviceStorage.getStringSharedPreferences(getActivity().getApplicationContext(), "Username", ""),
                                    splitname[0], splitname[1], gender, bio, description);//, imageName);
                    profileName.setEnabled(false);
                    profileGender.setEnabled(false);
                    profileBio.setEnabled(false);
                    profileDescription.setEnabled(false);
                }
                isEditing = false;
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            try {
                Uri chosenImageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), chosenImageUri);
                profileImage.setImageBitmap(bitmap);
                new UploadImage(getActivity().getApplicationContext(), progressView, "profile_picture", "").execute(bitmap);
            }
            catch(IOException e)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Cannot find image file", Toast.LENGTH_LONG).show();
            }
        }
    }

}
