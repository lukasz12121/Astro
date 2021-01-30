package com.example.astro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.astro.DataHandlerWeather;
import com.example.astro.R;

public class WeatherFragment extends Fragment {

    public static TextView tvDay1;
    public static TextView tvDay2;
    public static TextView tvDay3;
    public static TextView tvDay4;
    public static TextView tvDay5;
    public static ImageView ivImg1;
    public static ImageView ivImg2;
    public static ImageView ivImg3;
    public static ImageView ivImg4;
    public static ImageView ivImg5;
    public static TextView tvWeather1;
    public static TextView tvWeather2;
    public static TextView tvWeather3;
    public static TextView tvWeather4;
    public static TextView tvWeather5;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weather, container, false);
        tvDay1 =  (TextView)rootView.findViewById(R.id.tvDay1);
        tvDay2 =  (TextView)rootView.findViewById(R.id.tvDay2);
        tvDay3 =  (TextView)rootView.findViewById(R.id.tvDay3);
        tvDay4 =  (TextView)rootView.findViewById(R.id.tvDay4);
        tvDay5 =  (TextView)rootView.findViewById(R.id.tvDay5);
        ivImg1 = (ImageView)rootView.findViewById(R.id.ivImg1);
        ivImg2 = (ImageView)rootView.findViewById(R.id.ivImg2);
        ivImg3 = (ImageView)rootView.findViewById(R.id.ivImg3);
        ivImg4 = (ImageView)rootView.findViewById(R.id.ivImg4);
        ivImg5 = (ImageView)rootView.findViewById(R.id.ivImg5);
        tvWeather1 =  (TextView)rootView.findViewById(R.id.tvWeather1);
        tvWeather2 =  (TextView)rootView.findViewById(R.id.tvWeather2);
        tvWeather3 =  (TextView)rootView.findViewById(R.id.tvWeather3);
        tvWeather4 =  (TextView)rootView.findViewById(R.id.tvWeather4);
        tvWeather5 =  (TextView)rootView.findViewById(R.id.tvWeather5);

        DataHandlerWeather process = new DataHandlerWeather(getContext());
        process.execute();

        return rootView;
    }
}
