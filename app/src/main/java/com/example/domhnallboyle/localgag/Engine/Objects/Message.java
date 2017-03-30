package com.example.domhnallboyle.localgag.Engine.Objects;

/**
 * Created by DomhnallBoyle on 16/10/2016.
 */
public class Message {

    private String text, datetime;

    public Message(String text, String datetime)
    {
        this.text = text;
        this.datetime = datetime;
    }

    public String getText()
    {
        return text;
    }

    public String getDatetime()
    {
        return datetime;
    }
}
