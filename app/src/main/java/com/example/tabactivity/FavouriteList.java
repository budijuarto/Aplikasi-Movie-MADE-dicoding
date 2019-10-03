package com.example.tabactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.tabactivity.adapter.ViewPagerAdapter;
import com.example.tabactivity.fragment.MoviesFragment;
import com.example.tabactivity.fragment.TvSeriesFragment;
import com.example.tabactivity.fragment_favourite.MovFavFragment;
import com.example.tabactivity.fragment_favourite.TvFavFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FavouriteList extends AppCompatActivity  {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private DatabaseAdapter adapter;
    private MovieHelper noteHelper;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Notes");

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        addTabs(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setTitle("Favorite Movie");



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MovFavFragment(), getString(R.string.movies));
        adapter.addFrag(new TvFavFragment(), getString(R.string.tv_series));
        viewPager.setAdapter(adapter);
    }




}
