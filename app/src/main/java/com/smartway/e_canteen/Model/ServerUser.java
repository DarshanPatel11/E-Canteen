package com.smartway.e_canteen.Model;

/**
 * Created by djsma on 05-02-2018.
 */

public class ServerUser {
    private String Name, Password, Phone, IsStaff;

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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public ServerUser(String name, String password) {
        Name = name;
        Password = password;
    }

    public ServerUser() {
    }
}
