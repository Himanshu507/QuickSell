package com.himanshu.quicksell.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.himanshu.quicksell.Adapters.Fav_View_Adapter;
import com.himanshu.quicksell.Model.Add_item_model;
import com.himanshu.quicksell.Model.Fav_Model;
import com.himanshu.quicksell.R;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    private CollectionReference noteRef;
    ViewGroup temp;
    Fav_View_Adapter fav_view_adapter;
    DocumentReference documentReference;

  /*  @Override
    public void onStart() {
        super.onStart();
        fav_view_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fav_view_adapter.stopListening();
    }
*/
    public static FavFragment newInstance() {
        return new FavFragment();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        init_Views(view);
        temp = container;
        db = FirebaseFirestore.getInstance();
        noteRef = db.collection("Users");
        documentReference = noteRef.document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Get_All_Doc();
        return view;

    }

    private void Get_All_Doc() {

        final Task<DocumentSnapshot> lists = documentReference.get();
        System.out.println(lists);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                List<String> groupp = (List<String>) document.get("fav");
               /* List<Fav_Model> group = (List<Fav_Model>) document.get("fav");
                List<Add_item_model> add_item_models = new ArrayList<>();
                for (int i = 0 ;i<group.size();i++){
                    add_item_models.add(group.get(i));
                }*/
                Log.d("TAG", groupp.toString());
            }
        });
    }


    private void init_Views(View view) {
        recyclerView = view.findViewById(R.id.main_recycler);

    }

}
