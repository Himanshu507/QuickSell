package com.himanshu.quicksell.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.himanshu.quicksell.Adapters.Main_Views_Adapter;
import com.himanshu.quicksell.Model.Add_item_model;
import com.himanshu.quicksell.R;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class HomeFragment extends Fragment {

    FirebaseFirestore db;
    private CollectionReference noteRef;
    RecyclerView recyclerView;
    List<Add_item_model> items_list = new ArrayList<>();
    ViewGroup temp;
    Main_Views_Adapter main_views_adapter;


    @Override
    public void onStart() {
        super.onStart();
        main_views_adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        main_views_adapter.stopListening();
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        temp = container;

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init_Views(view);

        db = FirebaseFirestore.getInstance();
        noteRef = db.collection("Products");

        Get_All_Doc();

        return view;

    }

    private void Get_All_Doc() {

        Query query = noteRef.orderBy("cost", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Add_item_model> options = new FirestoreRecyclerOptions.Builder<Add_item_model>()
                .setQuery(query, Add_item_model.class)
                .build();

        main_views_adapter = new Main_Views_Adapter(options, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(temp.getContext(), 2));
        recyclerView.setAdapter(main_views_adapter);

        /*noteRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Add_item_model item = documentSnapshot.toObject(Add_item_model.class);
                    items_list.add(item);
                }
                Main_Views_Adapter main_views_adapter = new Main_Views_Adapter(temp.getContext(), "HomeFragment", items_list);
                recyclerView.setLayoutManager(new GridLayoutManager(temp.getContext(), 2));
                recyclerView.setAdapter(main_views_adapter);
            }
        });*/
    }

    private void init_Views(View view) {
        recyclerView = view.findViewById(R.id.main_recycler);

    }

}
