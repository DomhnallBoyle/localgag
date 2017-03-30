package com.example.domhnallboyle.localgag.Engine.Objects;

/**
 * Created by DomhnallBoyle on 06/08/2016.
 */
public class Status {

    private StatusType statusType;
    private String text, username;
    private int votes, comments, comment_set_id;
    private boolean upVoted, downVoted;

    public Status(StatusType statusType, String username, String text, int votes, int comments, int comment_set_id)
    {
        this.statusType = statusType;
        this.text = text;
        this.username = username;
        this.votes = votes;
        this.comments = comments;
        this.comment_set_id = comment_set_id;
        this.upVoted = false;
        this.downVoted = false;
    }

    public StatusType getStatusType()
    {
        return statusType;
    }

    public String getText()
    {
        return text;
    }

    public String getUsername() { return username; }

    public int getVotes() { return votes; }

    public int getComments() { return comments; }

    public int getComment_set_id() { return comment_set_id; }

    public void setVotes(int votes)
    {
        this.votes = votes;
    }

    public boolean getUpVoted()
    {
        return upVoted;
    }

    public void setUpVoted(boolean upVoted)
    {
        this.upVoted = upVoted;
    }

    public boolean getDownVoted()
    {
        return downVoted;
    }

    public void setDownVoted(boolean downVoted)
    {
        this.downVoted = downVoted;
    }
}
