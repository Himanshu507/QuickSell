package com.himanshu.quicksell.Model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class User_Model {
    List<DocumentSnapshot> fav = new ArrayList<DocumentSnapshot>();

    public User_Model(List<DocumentSnapshot> fav) {
        this.fav = fav;
    }

    public List<DocumentSnapshot> getFav() {
        return fav;
    }

    public void setFav(List<DocumentSnapshot> fav) {
        this.fav = fav;
    }
}
