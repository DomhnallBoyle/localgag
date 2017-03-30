package com.example.domhnallboyle.localgag.Engine.Objects;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.domhnallboyle.localgag.Engine.Database.RetrieveImage;
import com.example.domhnallboyle.localgag.R;
import com.example.domhnallboyle.localgag.Screens.Fragments.MessageFragment;

import java.util.ArrayList;

/**
 * Created by DomhnallBoyle on 16/10/2016.
 */
public class FriendListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Friend> friends;

    public FriendListAdapter(Context context, ArrayList<Friend> friends)
    {
        this.context = context;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Friend friend = (Friend)getItem(position);
            v = inflater.inflate(R.layout.friends_row_layout, parent, false);
            ImageView friend_pic = (ImageView)v.findViewById(R.id.friends_profile_pic);
            TextView friend_username = (TextView)v.findViewById(R.id.friends_username);
            TextView messages = (TextView)v.findViewById(R.id.friends_new_messages);

            new RetrieveImage(context, friend_pic, null, ImageType.PROFILE)
                    .execute("http://www.localgag.x10host.com/profile_images/" + friend.getUsername() + ".jpg");
            friend_username.setText(friend.getFirstname() + " " + friend.getLastname());

        }
        return v;
    }
}
