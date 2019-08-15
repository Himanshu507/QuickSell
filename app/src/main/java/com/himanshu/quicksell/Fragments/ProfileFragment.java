package com.himanshu.quicksell.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.himanshu.quicksell.R;
import com.himanshu.quicksell.SignUp.LoginActivity;

public class ProfileFragment extends Fragment {

    Button logout_btn;
    Context mContext;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init_Views(view);
        mContext = container.getContext();
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
        return view;
    }

    private void init_Views(View view) {
        logout_btn = view.findViewById(R.id.logout);

    }

    public void Logout() {
        AuthUI.getInstance().signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mContext, "Successfully Logout", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(mContext, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });
    }
}
