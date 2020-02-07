package com.example.padidav2.Model;

public class Users
{
    public String Name,Password,Roll_no,email;

    public Users()
    {

    }
    public Users(String name, String password, String roll_no, String email_ID) {
        Name = name;
        Password = password;
        Roll_no = roll_no;
        email = email_ID;
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
        return email;
    }

    public void setEmail_ID(String email_ID) {
        email = email_ID;
    }
}
