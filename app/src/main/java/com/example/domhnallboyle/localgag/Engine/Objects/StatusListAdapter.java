package com.example.domhnallboyle.localgag.Engine.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.domhnallboyle.localgag.Engine.Database.RetrieveImage;
import com.example.domhnallboyle.localgag.R;

import java.util.ArrayList;

/**
 * Created by DomhnallBoyle on 04/09/2016.
 */
public class StatusListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Comment> commentList;

    public StatusListAdapter(Context context, ArrayList<Comment> commentList)
    {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
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
            final Comment comment = commentList.get(position);
            v = inflater.inflate(R.layout.comment_row_layout, parent, false);
            ImageView profile_picture = (ImageView)v.findViewById(R.id.comment_profile_picture);
            TextView username = (TextView)v.findViewById(R.id.comment_username);
            TextView text = (TextView)v.findViewById(R.id.comment_text);
            Button upvote = (Button)v.findViewById(R.id.comment_upvotes);
            Button downvote = (Button)v.findViewById(R.id.comment_downvotes);
            final TextView votes = (TextView)v.findViewById(R.id.comment_votes);
            
            new RetrieveImage(context, profile_picture, null, ImageType.PROFILE)
                    .execute("http://www.localgag.x10host.com/profile_images/" + comment.getUsername() + ".jpg");
            username.setText(comment.getUsername());
            text.setText(comment.getText());
            votes.setText(comment.getVotes() + "");
            
            upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!comment.getUpVoted() && comment.getDownVoted())
                    {
                        comment.setVotes(comment.getVotes() + 2);
                        comment.setUpVoted(true);
                        comment.setDownVoted(false);
                    }
                    else if (!comment.getUpVoted() && !comment.getDownVoted())
                    {
                        comment.setVotes(comment.getVotes() + 1);
                        comment.setUpVoted(true);
                    }
                    votes.setText("" + comment.getVotes());
                }
            });
            
            downvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!comment.getDownVoted() && comment.getUpVoted())
                    {
                        comment.setVotes(comment.getVotes() - 2);
                        comment.setDownVoted(true);
                        comment.setUpVoted(false);
                    }
                    else if (!comment.getDownVoted() && !comment.getUpVoted())
                    {
                        comment.setVotes(comment.getVotes() - 1);
                        comment.setDownVoted(true);
                    }
                    votes.setText("" + comment.getVotes());
                }
            });
        }
        return v;
    }


}
