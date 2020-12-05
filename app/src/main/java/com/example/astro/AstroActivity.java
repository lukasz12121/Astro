package com.example.astro;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.example.astro.fragments.MoonFragment;
import com.example.astro.fragments.SunFragment;

import java.util.ArrayList;
import java.util.List;

public class AstroActivity extends AppCompatActivity {
    private VerticalViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Fragment mySunFragment;
    private Fragment myMoonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro);

        //pobieranie danych z aktywności Settings
        Intent intent = getIntent();
        String sLatitude = intent.getStringExtra("LATITUDE");
        String sLongitude = intent.getStringExtra("LONGITUDE");

        getSupportFragmentManager();

        List<Fragment> list = new ArrayList<>();
        list.add(new SunFragment());
        list.add(new MoonFragment());

        viewPager = findViewById(R.id.pager);

        if(viewPager!= null) {
        pagerAdapter = new VPagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(pagerAdapter);
        } else {
            mySunFragment = new SunFragment();
            myMoonFragment = new MoonFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, mySunFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, myMoonFragment)
                    .commit();
        }
    }
}