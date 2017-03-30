package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.domhnallboyle.localgag.Engine.Database.FunctionType;
import com.example.domhnallboyle.localgag.Engine.Database.Functions;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.example.domhnallboyle.localgag.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

@SuppressLint("ValidFragment")
public class ImageViewFragment extends Fragment {

    private FragmentManager fragmentManager;
    private CircularProgressView progressView;
    private ImageView imageView;
    private ImageView onScreenView;
    private Button back;

    public ImageViewFragment(FragmentManager fragmentManager, ImageView imageView, CircularProgressView progressView) {
        this.imageView = imageView;
        this.fragmentManager = fragmentManager;
        this.progressView = progressView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_image_view, container, false);

        onScreenView = (ImageView)view.findViewById(R.id.inflated_imageView);
        onScreenView.setImageBitmap(((BitmapDrawable)imageView.getDrawable()).getBitmap());

        back = (Button)view.findViewById(R.id.inflated_back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Functions(getActivity().getApplicationContext(), fragmentManager, FunctionType.GET_PROFILE, progressView)
                        .execute(DeviceStorage.getStringSharedPreferences(getActivity().getApplicationContext(), "Username", ""));
            }
        });

        return view;
    }

}
