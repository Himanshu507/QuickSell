package com.himanshu.quicksell.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.himanshu.quicksell.Adapters.Main_Views_Adapter;
import com.himanshu.quicksell.R;

public class FavFragment extends Fragment {

    RecyclerView recyclerView;


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

      /*  Main_Views_Adapter main_views_adapter = new Main_Views_Adapter(container.getContext(), "FavFragment");
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        recyclerView.setAdapter(main_views_adapter);
*/
        return view;

    }

    private void init_Views(View view) {
        recyclerView = view.findViewById(R.id.main_recycler);

    }

}
