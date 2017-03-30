package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.annotation.SuppressLint;
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
import com.example.domhnallboyle.localgag.Engine.Objects.Message;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.example.domhnallboyle.localgag.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DomhnallBoyle on 16/10/2016.
 */
@SuppressLint("ValidFragment")
public class MessageFragment extends Fragment {

    private JSONObject jsonObject;

    public MessageFragment(JSONObject jsonObject)
    {
        this.jsonObject = jsonObject;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_messages, container, false);

        return view;
    }

    private ArrayList<Message> get_messages()
    {
        return null;
    }
}
