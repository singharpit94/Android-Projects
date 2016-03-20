package com.example.arpit.ashish;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by arpit on 2/4/16.
 */
public class ContactAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ContactAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                GETR tab1 = new GETR();
                return tab1;
            case 1:
                GALL tab2 = new GALL();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
