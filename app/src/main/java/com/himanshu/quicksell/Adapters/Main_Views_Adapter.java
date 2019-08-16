package com.himanshu.quicksell.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.himanshu.quicksell.Model.Add_item_model;
import com.himanshu.quicksell.R;

import java.util.List;

public class Main_Views_Adapter extends FirestoreRecyclerAdapter<Add_item_model, Main_Views_Adapter.MyViewHolder> {

    List<Add_item_model> posts;
    private Context mContext;

    public Main_Views_Adapter(@NonNull FirestoreRecyclerOptions<Add_item_model> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

   /* public Main_Views_Adapter(Context mContext, String from, List<Add_item_model> posts) {
        this.mContext = mContext;
        this.from = from;
        this.posts = posts;
    }
*/
   /* @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.main_recycler_item, parent, false);
        return new MyViewHolder(view);
    }*/

   /* @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (posts.size() > 0) {
            String url = posts.get(position).getProduct_images().get(0);
            Glide.with(mContext).load(url).into(holder.product_img);
            holder.title.setText(posts.get(position).getTitle());
            String rs = mContext.getResources().getString(R.string.Rs);
            holder.price.setText(rs + " " + posts.get(position).getCost());
        } else {
            holder.price.setText("Fetching issues.");
        }

        holder.fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(mContext).load(R.drawable.ic_favorite_color).into(holder.fav_img);
            }
        });
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.main_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

  /*  @Override
    public int getItemCount() {
        if (posts.size() > 0) {
            return posts.size();
        } else {
            return 1;
        }
    }*/

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i, @NonNull Add_item_model add_item_model) {
        String url = add_item_model.getProduct_images().get(0);
        Glide.with(mContext).load(url).into(myViewHolder.product_img);
        myViewHolder.title.setText(add_item_model.getTitle());
        String rs = mContext.getResources().getString(R.string.Rs);
        myViewHolder.price.setText(rs + " " + add_item_model.getCost());

        myViewHolder.fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(mContext).load(R.drawable.ic_favorite_color).into(myViewHolder.fav_img);
            }
        });
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
