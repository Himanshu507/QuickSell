package com.himanshu.quicksell;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.himanshu.quicksell.Adapters.MainActivityFragmentAdaptor;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    ViewPager viewPager;
    MainActivityFragmentAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initViews and some onclicks intialization
        init_Views();
        Button_Clicks();
        BottomAppBar toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

        //set adapter to viewpager
        adaptor = new MainActivityFragmentAdaptor(getSupportFragmentManager());
        viewPager.setAdapter(adaptor);
        viewPager.setCurrentItem(0);

    }

    private void init_Views() {
        floatingActionButton = findViewById(R.id.fab);
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        viewPager = findViewById(R.id.mainViewPager);

    }

    public void Button_Clicks() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Add_Item.class);
                startActivity(i);
                overridePendingTransition(R.anim.open_activity_from_bottom, R.anim.open_top);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bottom_app_bar_menu, menu);

        CircleImageView user_img = menu.findItem(R.id.user_details).getActionView().findViewById(R.id.user_pic);
        final ImageView home_img = menu.findItem(R.id.home).getActionView().findViewById(R.id.home_item);
        final ImageView fav_img = menu.findItem(R.id.fav).getActionView().findViewById(R.id.fav_item);

        home_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Home button Pressed", Toast.LENGTH_SHORT).show();
                home_img.setImageResource(R.drawable.colored_home);
                fav_img.setImageResource(R.drawable.ic_favorite_white);
                viewPager.setCurrentItem(0);

            }
        });

        fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Favourite button Pressed", Toast.LENGTH_SHORT).show();
                home_img.setImageResource(R.drawable.home);
                fav_img.setImageResource(R.drawable.ic_favorite_color);
                viewPager.setCurrentItem(1);
            }
        });

        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logout();
                //Toast.makeText(getApplicationContext(), "User Details button Pressed", Toast.LENGTH_SHORT).show();
                home_img.setImageResource(R.drawable.home);
                fav_img.setImageResource(R.drawable.ic_favorite_white);
                viewPager.setCurrentItem(2);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
