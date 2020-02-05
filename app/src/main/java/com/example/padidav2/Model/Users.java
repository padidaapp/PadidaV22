package com.example.padidav2.Model;

public class Users
{
    private String Name, Password,Roll_no,Email_ID;

    public Users()
    {

    }

    public Users(String name, String password, String roll_no, String email_ID) {
        Name = name;
        Password = password;
        Roll_no = roll_no;
        Email_ID = email_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRoll_no() {
        return Roll_no;
    }

    public void setRoll_no(String roll_no) {
        Roll_no = roll_no;
    }

    public String getEmail_ID() {
        return Email_ID;
    }

    public void setEmail_ID(String email_ID) {
        Email_ID = email_ID;
    }
}
