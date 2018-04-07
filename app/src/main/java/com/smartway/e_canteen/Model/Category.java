package com.smartway.e_canteen.Model;

/**
 * Created by djsma on 04-02-2018.
 */

public class Category {
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    private String Image;

    public Category() {
    }

    public Category(String name, String image) {

        this.Name = name;
        this.Image = image;
    }
}
