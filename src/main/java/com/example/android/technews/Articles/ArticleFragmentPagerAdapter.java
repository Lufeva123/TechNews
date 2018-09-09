package com.example.android.technews.Articles;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.example.android.technews.Fragments.MainFragment;

public class ArticleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ArticleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();

        if (position == 0) {

            bundle.putString("section", "world");
            fragment.setArguments(bundle);
            return fragment;

        } else if (position == 1){

            bundle.putString("section", "technology");
            fragment.setArguments(bundle);
            return fragment;

        } else {

            bundle.putString("section", "business");
            fragment.setArguments(bundle);
            return fragment;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position) {

            case 0:
                return "WORLD NEWS";
            case 1:
                return  "TECHNOLOGY";
            case 2:
                return "BUSINESS";
            default:
                return null;
        }
    }
}
