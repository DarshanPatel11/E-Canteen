package com.smartway.e_canteen.Model;

/**
 * Created by Darshan Patel on 10-02-2018.
 */

public class Sender {
    public String to;
    public Notification notification;

    public Sender(String token, Notification notification) {
        this.to = token;
        this.notification = notification;
    }
}
