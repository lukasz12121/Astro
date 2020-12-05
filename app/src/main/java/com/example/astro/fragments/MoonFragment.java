package com.example.astro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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



        location = new AstroCalculator.Location(-51.74, 19.44);
        SunFragment sunFragment = new SunFragment();
        AstroCalculator astroCalculator = new AstroCalculator(sunFragment.AstroDateTime(), location);
        tvTimer = (TextView)rootView.findViewById(R.id.tvTimer);
        content();

        tvMnRiseTime.setText(astroCalculator.getMoonInfo().getMoonrise().getHour() + ":" + astroCalculator.getMoonInfo().getMoonrise().getMinute() + ":" + astroCalculator.getMoonInfo().getMoonrise().getSecond());
        tvMnDawnTime.setText(astroCalculator.getMoonInfo().getMoonset().getHour() + ":" + astroCalculator.getMoonInfo().getMoonset().getMinute() + ":" + astroCalculator.getMoonInfo().getMoonset().getSecond());
        tvMnNewDate.setText(astroCalculator.getMoonInfo().getNextNewMoon().getDay()+"."+astroCalculator.getMoonInfo().getNextNewMoon().getMonth()+"."+astroCalculator.getMoonInfo().getNextNewMoon().getYear());
        tvMnFullDate.setText(astroCalculator.getMoonInfo().getNextFullMoon().getDay()+"."+astroCalculator.getMoonInfo().getNextFullMoon().getMonth()+"."+astroCalculator.getMoonInfo().getNextFullMoon().getYear());
        tvMnPhase.setText(SunFragment.round(astroCalculator.getMoonInfo().getIllumination(),2)*100+"%");


        Date nextNewMoonDate = new GregorianCalendar(astroCalculator.getMoonInfo().getNextNewMoon().getYear(), astroCalculator.getMoonInfo().getNextNewMoon().getMonth()-1, astroCalculator.getMoonInfo().getNextNewMoon().getDay(), 23, 59).getTime();
        Date today = new Date();
        long diff =  today.getTime() - nextNewMoonDate.getTime();
        int lunarMonthDay = (int) (diff / (1000 * 60 * 60 * 24))+29;
        tvMnSynDay.setText(String.valueOf(lunarMonthDay));

        return rootView;
    }
    public void content(){
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
                content();
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }
}
