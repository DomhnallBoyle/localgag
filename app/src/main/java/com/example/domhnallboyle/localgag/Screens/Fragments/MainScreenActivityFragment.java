package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Database.FunctionType;
import com.example.domhnallboyle.localgag.Engine.Database.Functions;
import com.example.domhnallboyle.localgag.Engine.Database.GetComments;
import com.example.domhnallboyle.localgag.Engine.Objects.MainListAdapter;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.example.domhnallboyle.localgag.R;
import com.example.domhnallboyle.localgag.Engine.Objects.Status;
import com.example.domhnallboyle.localgag.Engine.Objects.StatusType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A placeholder fragment containing a simple view.
 */
@SuppressLint("ValidFragment")
public class MainScreenActivityFragment extends Fragment {

    private JSONObject jsonObject;
    private MainListAdapter listAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MainScreenActivityFragment(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ArrayList<Status> posts = createPosts();

        listAdapter = new MainListAdapter(getActivity(), posts);

        View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);

        listView = (ListView) rootView.findViewById(R.id.statusList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Status status = posts.get(position);
                new GetComments(getContext().getApplicationContext(), getActivity().getSupportFragmentManager(), status, null)
                        .execute();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.wall_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Functions(getActivity().getApplicationContext(), getActivity().getSupportFragmentManager(), FunctionType.GET_POSTS, null)
                        .execute(DeviceStorage.getStringSharedPreferences(getActivity().getApplicationContext(), "Username", ""));
            }
        });

        return rootView;
    }

    private ArrayList<Status> createPosts()
    {
        ArrayList<Status> statuses = new ArrayList<Status>();
        Iterator<?> keys = jsonObject.keys();

        try {
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject internal_object = new JSONObject(jsonObject.get(key).toString());
                StatusType status_type;
                Toast.makeText(getContext(), internal_object.getString("post_type"), Toast.LENGTH_LONG).show();
                if (internal_object.getString("post_type").equalsIgnoreCase("STATUS_WITH_IMAGE"))
                    status_type = StatusType.STATUS_WITH_IMAGE;
                else
                    status_type = StatusType.STATUS_WITHOUT_IMAGE;
                statuses.add(new Status(status_type,
                        internal_object.getString("username"),
                        internal_object.getString("text"), internal_object.getInt("votes"),
                        internal_object.getInt("comments"),
                        internal_object.getInt("comment_set_id")));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return statuses;
    }

}
