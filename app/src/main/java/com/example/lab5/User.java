package com.example.lab5;

public class User
{
    private String bdate;
    private String username;
    private String password;

    public User() {
    }
    public User(String bdate, String username, String password)
    {
        this.bdate = bdate;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getbDate()
    {
        return bdate;
    }

    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setbDate(String bDate)
    {
        this.bdate = bDate;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}
