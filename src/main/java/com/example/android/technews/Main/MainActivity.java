package com.example.android.technews.Main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.technews.Articles.ArticleFragmentPagerAdapter;
import com.example.android.technews.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.tabLayoutColor));
        tabLayout.setTabTextColors(getResources().getColor(R.color.tabLayoutTitleColorUnselected), getResources().getColor(R.color.tabLayoutTitleColorSelected));

        ArticleFragmentPagerAdapter adapter = new ArticleFragmentPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);
    }
}
