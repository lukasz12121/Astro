package com.example.astro.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.astro.DataHandlerBasic;
import com.example.astro.R;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicFragment extends Fragment {

    TextView tvTimer;
    public static TextView tvCity;
    public static TextView tvTemperature;
    public static TextView tvDescr;
    public static TextView tvCoords;
    public static ImageView ivImage;
    public static String city;
    public static boolean units;
    public static String weatherCondition;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_basic, container, false);

        try {
            city = (PreferenceManager.getDefaultSharedPreferences(getContext()).getString("location1", "0"));
            units = (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("units", false));
        }catch(Exception e){
            city = "Warsaw";
            units = false;
        }
        tvCity =  (TextView)rootView.findViewById(R.id.tvCity);
        tvTemperature = (TextView)rootView.findViewById(R.id.tvTemperature);
        tvDescr = (TextView)rootView.findViewById(R.id.tvDescr);
        tvCoords = (TextView)rootView.findViewById(R.id.tvCoords);
        ivImage = (ImageView)rootView.findViewById(R.id.ivImage);
        //czas
        tvTimer = (TextView)rootView.findViewById(R.id.tvTimer);
        timeRefresh();

        DataHandlerBasic process = new DataHandlerBasic(getContext());
        process.execute();

        if(isConnectedToServer(1000)) {
            toast();
        }

        return rootView;
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
    private void toast(){
        Context context = getContext();
        CharSequence text = "No internet connection! Data can be out of date!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public static boolean isConnectedToServer(int timeout) {
        try{
            URL myUrl = new URL("https://api.openweathermap.org/data/2.5/weather?q=Lodz&units=metric&appid=1778e1fb21f584645f258e134cdf81c4");
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
