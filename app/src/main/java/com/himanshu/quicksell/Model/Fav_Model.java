package com.himanshu.quicksell.Model;

import java.util.ArrayList;
import java.util.List;

public class Fav_Model extends Add_item_model {
    List<User_Model> fav = new ArrayList<>();

    public Fav_Model(List<User_Model> fav) {
        this.fav = fav;
    }

    public List<User_Model> getFav() {
        return fav;
    }

    public void setFav(List<User_Model> fav) {
        this.fav = fav;
    }
}
