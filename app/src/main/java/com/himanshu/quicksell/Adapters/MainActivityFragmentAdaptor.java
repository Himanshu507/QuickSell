package com.himanshu.quicksell.Adapters;


import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.himanshu.quicksell.Fragments.FavFragment;
import com.himanshu.quicksell.Fragments.HomeFragment;
import com.himanshu.quicksell.Fragments.ProfileFragment;

public class MainActivityFragmentAdaptor extends FragmentStatePagerAdapter {

    private static final int FRAGMENT_NUM = 3;

    public MainActivityFragmentAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return HomeFragment.newInstance();

            case 1:
                return FavFragment.newInstance();

            case 2:
                return ProfileFragment.newInstance();

            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_NUM;
    }
}
