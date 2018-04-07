package com.smartway.e_canteen.Model;

/**
 * Created by djsma on 04-02-2018.
 */

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String IsStaff;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public User(){

    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
    public User(String name, String password) {
        this.Name = name;
        this.Password = password;
        this.IsStaff = "false";
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public User(String name, String password, String phone) {
        this.Name = name;
        this.Password = password;
        this.Phone = phone;
        this.IsStaff = "false";

    }


}
