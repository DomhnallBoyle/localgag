package com.example.domhnallboyle.localgag.Engine.Objects;

/**
 * Created by DomhnallBoyle on 03/09/2016.
 */
public class Comment {

    String username;
    private int comment_set_id;
    private String text;
    private int votes;
    private boolean upVoted, downVoted;

    public Comment(String username, String text, int votes)
    {
        this.username = username;
        this.text = text;
        this.votes = votes;
        this.upVoted = false;
        this.downVoted = false;
    }

    public String getUsername()
    {
        return username;
    }

    public String getText()
    {
        return text;
    }

    public int getVotes()
    {
        return votes;
    }

    public void setVotes(int votes)
    {
        this.votes = votes;
    }

    public boolean getUpVoted()
    {
        return upVoted;
    }

    public boolean getDownVoted()
    {
        return downVoted;
    }

    public void setUpVoted(boolean upVoted)
    {
        this.upVoted = upVoted;
    }

    public void setDownVoted(boolean downVoted)
    {
        this.downVoted = downVoted;
    }
}
