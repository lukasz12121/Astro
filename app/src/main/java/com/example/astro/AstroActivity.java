package com.example.astro;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.example.astro.fragments.AdditionalFragment;
import com.example.astro.fragments.BasicFragment;
import com.example.astro.fragments.MoonFragment;
import com.example.astro.fragments.SunFragment;
import com.example.astro.fragments.WeatherFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AstroActivity extends AppCompatActivity  {

    public static String basicJson;
    public static String additionalJson;
    public static String dailyJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_astro);

        //tryb offline
        basicJson = loadJSONFromAsset(this,"basic.json");
        additionalJson = loadJSONFromAsset(this,"additional.json");
        dailyJson = loadJSONFromAsset(this,"daily.json");

        getSupportFragmentManager();

        List<Fragment> list = new ArrayList<>();
        list.add(new SunFragment());
        list.add(new MoonFragment());
        list.add(new BasicFragment());
        list.add(new AdditionalFragment());
        list.add(new WeatherFragment());

        VerticalViewPager viewPager = findViewById(R.id.pager);

        if(viewPager != null) {
            PagerAdapter pagerAdapter = new VPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(pagerAdapter);
        } else {
            Fragment mySunFragment = new SunFragment();
            Fragment myMoonFragment = new MoonFragment();
            Fragment myBasicFragment = new BasicFragment();
            Fragment myAdditionalFragment = new AdditionalFragment();
            Fragment myWeatherFragment = new WeatherFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, mySunFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, myMoonFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment3, myBasicFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment4, myAdditionalFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment5, myWeatherFragment)
                    .commit();
        }
    }
    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}