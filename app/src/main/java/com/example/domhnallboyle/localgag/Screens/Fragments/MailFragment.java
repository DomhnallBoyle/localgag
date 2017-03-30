package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.domhnallboyle.localgag.Engine.Objects.Friend;
import com.example.domhnallboyle.localgag.Engine.Objects.FriendListAdapter;
import com.example.domhnallboyle.localgag.Engine.Objects.Status;
import com.example.domhnallboyle.localgag.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DomhnallBoyle on 16/10/2016.
 */
@SuppressLint("ValidFragment")
public class MailFragment extends Fragment {

    private JSONObject jsonObject;
    private ListView listView;
    private ListAdapter listAdapter;

    public MailFragment(JSONObject jsonObject)
    {
        this.jsonObject = jsonObject;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mail, container, false);

        final ArrayList<Friend> friends = get_friends();

        listAdapter = new FriendListAdapter(getActivity(), friends);

        listView = (ListView) view.findViewById(R.id.statusList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }

    private ArrayList<Friend> get_friends()
    {
        return null;
    }

}
