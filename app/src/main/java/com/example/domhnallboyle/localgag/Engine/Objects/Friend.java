package com.example.domhnallboyle.localgag.Engine.Objects;

/**
 * Created by DomhnallBoyle on 16/10/2016.
 */
public class Friend {

    private String username, firstname, lastname;

    public Friend(String username, String firstname, String lastname)
    {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername()
    {
        return username;
    }
    public String getFirstname()
    {
        return firstname;
    }
    public String getLastname()
    {
        return lastname;
    }
}
