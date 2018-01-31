package com.example.anjana.binmaster.HomePage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Admin on 3/1/2017.
 */

public class MyAdapter  extends FragmentPagerAdapter {


    public MyAdapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DailyService();
            case 1:
                return new FoodDonation();



        }
        return null;
    }

    @Override
    public int getCount() {


        return TabFragment.int_items;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Daily Service";
            case 1:
                return "Food Donation";


        }

        return null;
    }
}
