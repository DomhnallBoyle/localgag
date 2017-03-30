package com.example.domhnallboyle.localgag.Engine.Objects;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Database.FunctionType;
import com.example.domhnallboyle.localgag.Engine.Database.Functions;
import com.example.domhnallboyle.localgag.Engine.Database.RetrieveImage;
import com.example.domhnallboyle.localgag.R;

import java.util.ArrayList;

/**
 * Created by DomhnallBoyle on 06/08/2016.
 */
public class MainListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Status> statusList;

    public MainListAdapter(Context context, ArrayList<Status> statusList)
    {
        this.context = context;
        this.statusList = statusList;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e(statusList.get(position).getStatusType() + "", "");
        if (statusList.get(position).getStatusType() == StatusType.STATUS_WITHOUT_IMAGE)
            return 0;
        else
            return 1;
    }

    @Override
    public int getCount() {
        return statusList.size();
    }

    @Override
    public Object getItem(int position) {
        return statusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View v = convertView;
        int type = getItemViewType(position);
        if (v == null) {
            // Inflate the layout according to the view type
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Status status = statusList.get(position);
            if (type == 0) {
                // Inflate the layout without image
                v = inflater.inflate(R.layout.simple_row_layout, parent, false);
                ImageView profile_picture = (ImageView)v.findViewById(R.id.wall_profile_picture_simple);
                TextView username = (TextView)v.findViewById(R.id.wall_username_simple);
                TextView status_text = (TextView)v.findViewById(R.id.wall_status_text_simple);
                Button upvote = (Button)v.findViewById(R.id.upvote_simple);
                Button downvote = (Button)v.findViewById(R.id.downvote_simple);
                final TextView votes = (TextView)v.findViewById(R.id.wall_status_votes_simple);
                TextView comments = (TextView)v.findViewById(R.id.wall_status_comment_simple);

                new RetrieveImage(context, profile_picture, null, ImageType.PROFILE)
                        .execute("http://www.localgag.x10host.com/profile_images/" + status.getUsername() + ".jpg");
                username.setText(status.getUsername());
                status_text.setText(status.getText());
                votes.setText("" + status.getVotes());
                comments.setText(status.getComments() + " comments");

                upvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!status.getUpVoted() && status.getDownVoted()) {
                            status.setVotes(status.getVotes() + 2);
                            status.setUpVoted(true);
                            status.setDownVoted(false);
                        } else if (!status.getUpVoted() && !status.getDownVoted()) {
                            status.setVotes(status.getVotes() + 1);
                            status.setUpVoted(true);
                        }
                        votes.setText("" + status.getVotes());
                        new Functions(context, null, FunctionType.VOTE, null);
                    }
                });

                downvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!status.getDownVoted() && status.getUpVoted()) {
                            status.setVotes(status.getVotes() - 2);
                            status.setDownVoted(true);
                            status.setUpVoted(false);
                        } else if (!status.getDownVoted() && !status.getUpVoted()) {
                            status.setVotes(status.getVotes() - 1);
                            status.setDownVoted(true);
                        }
                        votes.setText("" + status.getVotes());
                        new Functions(context, null, FunctionType.VOTE, null);
                    }
                });
            }
            else {
                // inflate with image
                v = inflater.inflate(R.layout.complex_row_layout, parent, false);
                ImageView profile_picture = (ImageView)v.findViewById(R.id.wall_profile_picture_complex);
                TextView username = (TextView)v.findViewById(R.id.wall_username_complex);
                ImageView status_image = (ImageView)v.findViewById(R.id.wall_status_image_complex);
                TextView status_text = (TextView)v.findViewById(R.id.wall_status_text_complex);
                Button upvote = (Button)v.findViewById(R.id.upvote_complex);
                Button downvote = (Button)v.findViewById(R.id.downvote_complex);
                final TextView votes = (TextView)v.findViewById(R.id.wall_status_votes_complex);
                TextView comments = (TextView)v.findViewById(R.id.wall_status_comments_complex);

                new RetrieveImage(context, profile_picture, null, ImageType.PROFILE)
                        .execute("http://www.localgag.x10host.com/profile_images/" + status.getUsername() + ".jpg");
                new RetrieveImage(context, status_image, null, ImageType.STATUS)
                        .execute("http://www.localgag.x10host.com/status_images/" + status.getUsername() + "-" + status.getComment_set_id() + ".jpg");

                username.setText(status.getUsername());
                status_text.setText(status.getText());
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
                        new Functions(context, null, FunctionType.VOTE, null);
                    }
                });

                downvote.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
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
                        new Functions(context, null, FunctionType.VOTE, null);
                    }
                });
            }
        }

        return v;
    }


}
