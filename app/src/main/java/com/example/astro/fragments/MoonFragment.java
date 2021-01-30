package com.example.astro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.astrocalculator.AstroCalculator;
import com.example.astro.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class MoonFragment extends Fragment {

    TextView tvTimer;
    private AstroCalculator.Location location;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_moon,container,false);

        TextView tvMnRiseTime = (TextView)rootView.findViewById(R.id.tvMnRiseTime);
        TextView tvMnDawnTime = (TextView)rootView.findViewById(R.id.tvMnDawnTime);
        TextView tvMnNewDate = (TextView)rootView.findViewById(R.id.tvMnNewDate);
        TextView tvMnFullDate = (TextView)rootView.findViewById(R.id.tvMnFullDate);
        TextView tvMnPhase = (TextView)rootView.findViewById(R.id.tvMnPhase);
        TextView tvMnSynDay = (TextView)rootView.findViewById(R.id.tvMnSynDay);
        TextView tvCoords = (TextView)rootView.findViewById(R.id.tvCoords2);

        float latitude;
        float longitude;
        int refresh_Freq = 0;

        try {
            latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
            longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
            refresh_Freq = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("refresh", "0"));
        }catch(Exception e){
            latitude = 0;
            longitude = 0;
        }


        //contentRefresh(refresh_Freq);
        location = new AstroCalculator.Location(latitude, longitude);
        SunFragment sunFragment = new SunFragment();
        AstroCalculator astroCalculator = new AstroCalculator(sunFragment.AstroDateTime(), location);
        tvTimer = (TextView)rootView.findViewById(R.id.tvTimer);
        timeRefresh();


        tvCoords.setText(latitude + " " + longitude);
        tvMnRiseTime.setText("Moonrise: " + astroCalculator.getMoonInfo().getMoonrise().getHour() + ":" + astroCalculator.getMoonInfo().getMoonrise().getMinute() + ":" + astroCalculator.getMoonInfo().getMoonrise().getSecond());
        tvMnDawnTime.setText("Moonset: " + astroCalculator.getMoonInfo().getMoonset().getHour() + ":" + astroCalculator.getMoonInfo().getMoonset().getMinute() + ":" + astroCalculator.getMoonInfo().getMoonset().getSecond());
        tvMnNewDate.setText("Nearest new moon: "  + astroCalculator.getMoonInfo().getNextNewMoon().getDay()+"."+astroCalculator.getMoonInfo().getNextNewMoon().getMonth()+"."+astroCalculator.getMoonInfo().getNextNewMoon().getYear());
        tvMnFullDate.setText("Nearest full moon: " + astroCalculator.getMoonInfo().getNextFullMoon().getDay()+"."+astroCalculator.getMoonInfo().getNextFullMoon().getMonth()+"."+astroCalculator.getMoonInfo().getNextFullMoon().getYear());
        tvMnPhase.setText("Lunar phase:  " + SunFragment.round(astroCalculator.getMoonInfo().getIllumination(),2)*100+"%");


        Date nextNewMoonDate = new GregorianCalendar(astroCalculator.getMoonInfo().getNextNewMoon().getYear(), astroCalculator.getMoonInfo().getNextNewMoon().getMonth()-1, astroCalculator.getMoonInfo().getNextNewMoon().getDay(), 23, 59).getTime();
        Date today = new Date();
        long diff =  today.getTime() - nextNewMoonDate.getTime();
        int lunarMonthDay = (int) (diff / (1000 * 60 * 60 * 24))+29;
        tvMnSynDay.setText("Day of Lunar Month: " + String.valueOf(lunarMonthDay));

        return rootView;
    }
    public void contentRefresh(int time){
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        //tvTimer.setText(formatter.format(date));
        time = time * 60 * 1000;
        refresh(time);
    }
    public void timeRefresh(){
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        tvTimer.setText(formatter.format(date));

        refresh(1000);
    }
    public void refresh(int miliseconds){
        final Handler handler = new Handler();
        final Runnable  runnable = new Runnable() {
            @Override
            public void run() {
                timeRefresh();
                //contentRefresh(0);
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }
}
