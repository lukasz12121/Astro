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
import com.astrocalculator.AstroDateTime;
import com.example.astro.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SunFragment extends Fragment {

    TextView tvSnRiseTime;
    TextView tvSnDawnTime;
    TextView tvSnCvRise;
    TextView tvSnCvDawn;
    TextView tvCoords;
    TextView tvTimer;
    int refresh_Freq;
    float latitude;
    float longitude;



    private AstroCalculator.Location location;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sun,container,false);


         tvSnRiseTime = (TextView)rootView.findViewById(R.id.tvrSnRiseTime);
         tvSnDawnTime = (TextView)rootView.findViewById(R.id.tvrSnDawnTime);
         tvSnCvRise = (TextView)rootView.findViewById(R.id.tvSnCvRise);
         tvSnCvDawn = (TextView)rootView.findViewById(R.id.tvSnCvDawn);
         tvCoords = (TextView)rootView.findViewById(R.id.tvCoords);

         tvTimer = (TextView)rootView.findViewById(R.id.tvTimer);
         timerRefresh();

        try {
             latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
             longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
             refresh_Freq = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("refresh", "0"));
        }catch(Exception e){
            latitude = 0;
            longitude = 0;
        }
       // contentRefresh(refresh_Freq);
        tvCoords.setText(latitude + " " + longitude);
        location = new AstroCalculator.Location(latitude, longitude);
        AstroCalculator astroCalculator = new AstroCalculator(AstroDateTime(), location);
        String sunRiseTime = "Czas: " + astroCalculator.getSunInfo().getSunrise().getHour() + ":" + astroCalculator.getSunInfo().getSunrise().getMinute() + ":"+ astroCalculator.getSunInfo().getSunrise().getSecond();
        String sunsSetTime = "Czas: " + astroCalculator.getSunInfo().getSunset().getHour() + ":" + astroCalculator.getSunInfo().getSunset().getMinute() + ":"+ astroCalculator.getSunInfo().getSunset().getSecond();
        /*int month = astroCalculator.getSunInfo().getSunrise().getMonth();
        int day = astroCalculator.getSunInfo().getSunrise().getDay();

        if(month > 3 && month < 10){
            tvSnRiseTime.setText(astroCalculator.getSunInfo().getSunrise().getHour() + ":" + astroCalculator.getSunInfo().getSunrise().getMinute() + ":"+ astroCalculator.getSunInfo().getSunrise().getSecond());
            tvSnDawnTime.setText(astroCalculator.getSunInfo().getSunset().getHour() + ":" + astroCalculator.getSunInfo().getSunset().getMinute() + ":"+ astroCalculator.getSunInfo().getSunset().getSecond());
        }else{
            tvSnRiseTime.setText((astroCalculator.getSunInfo().getSunrise().getHour() - 1) + ":" + astroCalculator.getSunInfo().getSunrise().getMinute() + ":"+ astroCalculator.getSunInfo().getSunrise().getSecond());
            tvSnDawnTime.setText((astroCalculator.getSunInfo().getSunset().getHour() - 1) + ":" + astroCalculator.getSunInfo().getSunset().getMinute() + ":"+ astroCalculator.getSunInfo().getSunset().getSecond());
        }*/

        String riseAzim = "Azymut" +  round(astroCalculator.getSunInfo().getAzimuthRise(), 2) + "°";
        String dawnAzim = "Azymut" + round(astroCalculator.getSunInfo().getAzimuthSet(),2)+"°";
        tvSnRiseTime.setText(sunRiseTime + "   " + riseAzim);
        tvSnDawnTime.setText(sunsSetTime + "   " + dawnAzim);
        tvSnCvRise.setText(String.valueOf(astroCalculator.getSunInfo().getTwilightMorning()));
        tvSnCvDawn.setText(String.valueOf(astroCalculator.getSunInfo().getTwilightEvening()));


        return rootView;
    }

    public AstroDateTime AstroDateTime() {
        long deviceDate = System.currentTimeMillis();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.ENGLISH).format(deviceDate));
        int month = Integer.parseInt(new SimpleDateFormat("MM", Locale.ENGLISH).format(deviceDate));
        int day = Integer.parseInt(new SimpleDateFormat("dd", Locale.ENGLISH).format(deviceDate));
        int hour = Integer.parseInt(new SimpleDateFormat("hh", Locale.GERMANY).format(deviceDate));
        int minute = Integer.parseInt(new SimpleDateFormat("mm", Locale.GERMANY).format(deviceDate));
        int second = Integer.parseInt(new SimpleDateFormat("ss", Locale.GERMANY).format(deviceDate));
        int timeZoneOffset = 1;
        boolean dayLightSaving = true;
        return new AstroDateTime(year, month, day, hour, minute, second, timeZoneOffset, dayLightSaving);
    }
    public static double round(double value, int precision) {
        if (precision < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, precision);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void contentRefresh(int time){
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        AstroCalculator astroCalculator = new AstroCalculator(AstroDateTime(), location);
        String sunRiseTime = "Czas: " + astroCalculator.getSunInfo().getSunrise().getHour() + ":" + astroCalculator.getSunInfo().getSunrise().getMinute() + ":"+ astroCalculator.getSunInfo().getSunrise().getSecond();
        String sunsSetTime = "Czas: " + astroCalculator.getSunInfo().getSunset().getHour() + ":" + astroCalculator.getSunInfo().getSunset().getMinute() + ":"+ astroCalculator.getSunInfo().getSunset().getSecond();
        String riseAzim = "Azymut: " +  round(astroCalculator.getSunInfo().getAzimuthRise(), 2) + "°";
        String dawnAzim = "Azymut: " + round(astroCalculator.getSunInfo().getAzimuthSet(),2)+"°";
        tvSnRiseTime.setText(sunRiseTime + "   " + riseAzim);
        tvSnDawnTime.setText(sunsSetTime + "   " + dawnAzim);
        tvSnCvRise.setText(String.valueOf(astroCalculator.getSunInfo().getTwilightMorning()));
        tvSnCvDawn.setText(String.valueOf(astroCalculator.getSunInfo().getTwilightEvening()));
        time = time * 60 * 1000;
        refresh(time);
    }
    public void timerRefresh(){
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
                timerRefresh();
                //contentRefresh(refresh_Freq);
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }

}

   