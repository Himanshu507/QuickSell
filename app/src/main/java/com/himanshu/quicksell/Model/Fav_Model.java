package com.himanshu.quicksell.Model;

import java.util.ArrayList;
import java.util.List;

public class Fav_Model extends Add_item_model {
    List<Add_item_model> fav = new ArrayList<>();

    public Fav_Model(List<Add_item_model> fav) {
        this.fav = fav;
    }

    public List<Add_item_model> getFav() {
        return fav;
    }

    public void setFav(List<Add_item_model> fav) {
        this.fav = fav;
    }
}
