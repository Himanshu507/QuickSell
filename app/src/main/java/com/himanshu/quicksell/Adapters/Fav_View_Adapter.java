package com.himanshu.quicksell.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.himanshu.quicksell.Model.Add_item_model;
import com.himanshu.quicksell.Model.Add_item_model;
import com.himanshu.quicksell.Model.Fav_Model;
import com.himanshu.quicksell.Model.User_Model;
import com.himanshu.quicksell.R;

import java.util.ArrayList;
import java.util.List;

public class Fav_View_Adapter extends FirestoreRecyclerAdapter<Fav_Model, Fav_View_Adapter.MyViewHolder> {


    private Context mContext;
    FirebaseFirestore db;
    List<DocumentSnapshot> document_id = new ArrayList<DocumentSnapshot>();
    DocumentReference documentReference;
    User_Model user_model;
    CollectionReference collectionReference;

    public Fav_View_Adapter(@NonNull FirestoreRecyclerOptions<Fav_Model> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fav_View_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.main_recycler_item, parent, false);
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Users");
        documentReference = collectionReference.document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return new Fav_View_Adapter.MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Fav_View_Adapter.MyViewHolder myViewHolder, int i, @NonNull final Fav_Model add_item_model) {
        /*String url = add_item_model.getProduct_images().get(0);
        Glide.with(mContext).load(url).into(myViewHolder.product_img);
        myViewHolder.title.setText(add_item_model.getTitle());
        String rs = mContext.getResources().getString(R.string.Rs);
        myViewHolder.price.setText(rs + " " + add_item_model.getCost());

        myViewHolder.fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(myViewHolder.getAdapterPosition());
                snapshot.getId();
                Glide.with(mContext).load(R.drawable.ic_favorite_color).into(myViewHolder.fav_img);
                
                documentReference.update("fav", FieldValue.arrayRemove(snapshot)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Glide.with(mContext).load(R.drawable.fav_border).into(myViewHolder.fav_img);
                        Toast.makeText(mContext, "Successfully Created", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView fav_img, product_img;
        TextView price, title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_img = itemView.findViewById(R.id.fav_img_border);
            price = itemView.findViewById(R.id.price);
            product_img = itemView.findViewById(R.id.product_img);
            title = itemView.findViewById(R.id.title);
        }
    }

}
