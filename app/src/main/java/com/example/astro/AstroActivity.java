package com.example.astro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.example.astro.fragments.MoonFragment;
import com.example.astro.fragments.SunFragment;

import java.util.ArrayList;
import java.util.List;

public class AstroActivity extends AppCompatActivity {
    private VerticalViewPager viewPager;
    private PagerAdapter pagerAdapter;

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
        pagerAdapter = new VPagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(pagerAdapter);
    }
}