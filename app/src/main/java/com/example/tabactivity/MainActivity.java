package com.example.tabactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tabactivity.adapter.ViewPagerAdapter;
import com.example.tabactivity.fragment.TvSeriesFragment;
import com.example.tabactivity.fragment.MoviesFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity  {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        addTabs(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setTitle("Aplikasi Movie");

        startActivity(new Intent(MainActivity.this,HomeActivity.class));


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MoviesFragment(), getString(R.string.movies));
        adapter.addFrag(new TvSeriesFragment(), getString(R.string.tv_series));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }


}
