package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.domhnallboyle.localgag.Engine.Database.RetrieveImage;
import com.example.domhnallboyle.localgag.Engine.Objects.Comment;
import com.example.domhnallboyle.localgag.Engine.Objects.ImageType;
import com.example.domhnallboyle.localgag.Engine.Objects.Status;
import com.example.domhnallboyle.localgag.Engine.Objects.StatusListAdapter;
import com.example.domhnallboyle.localgag.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint("ValidFragment")
public class SimpleStatusFragment extends Fragment {

    private StatusListAdapter listAdapter;
    private ListView listView;
    private Status status;
    private JSONObject jsonObject;
    private ImageView profilePicture;
    private TextView username;
    private TextView text;
    private Button upvote, downvote;
    private TextView votes, comments;

    public SimpleStatusFragment(Status status, JSONObject jsonObject) {
        this.status = status;
        this.jsonObject = jsonObject;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simple_status, container, false);

        listView = (ListView)view.findViewById(R.id.status_listview_simple);
        profilePicture = (ImageView)view.findViewById(R.id.status_profile_picture_simple);
        username = (TextView)view.findViewById(R.id.status_username_simple);
        text = (TextView)view.findViewById(R.id.status_text_simple);
        upvote = (Button)view.findViewById(R.id.status_upvote_simple);
        downvote = (Button)view.findViewById(R.id.status_downvote_simple);
        votes = (TextView)view.findViewById(R.id.status_votes_simple);
        comments = (TextView)view.findViewById(R.id.status_comment_simple);

        new RetrieveImage(getActivity().getApplicationContext(), profilePicture, null, ImageType.PROFILE)
                .execute("http://www.localgag.x10host.com/profile_images/" + status.getUsername() + ".jpg");
        username.setText(status.getUsername());
        text.setText(status.getText());
        votes.setText(status.getVotes() + "");
        comments.setText(status.getComments() + " comments");

        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.getUpVoted() && status.getDownVoted())
                {
                    status.setVotes(status.getVotes() + 2);
                    status.setUpVoted(true);
                    status.setDownVoted(false);
                }
                else if (!status.getUpVoted() && !status.getDownVoted())
                {
                    status.setVotes(status.getVotes() + 1);
                    status.setUpVoted(true);
                }
                votes.setText("" + status.getVotes());
            }
        });

        downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.getDownVoted() && status.getUpVoted())
                {
                    status.setVotes(status.getVotes() - 2);
                    status.setDownVoted(true);
                    status.setUpVoted(false);
                }
                else if (!status.getDownVoted() && !status.getUpVoted())
                {
                    status.setVotes(status.getVotes() - 1);
                    status.setDownVoted(true);
                }
                votes.setText("" + status.getVotes());
            }
        });

        ArrayList<Comment> comments = createComments();
        listAdapter = new StatusListAdapter(getActivity(), comments);
        listView.setAdapter(listAdapter);

        return view;
    }

    private ArrayList<Comment> createComments()
    {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        Iterator<?> keys = jsonObject.keys();

        try {
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject internal_object = new JSONObject(jsonObject.get(key).toString());
                comments.add(new Comment(internal_object.getString("username"),
                        internal_object.getString("text"),
                        internal_object.getInt("votes")));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return comments;
    }
}
