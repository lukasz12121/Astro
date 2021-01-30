package com.example.astro.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.astro.DataHandlerAdditional;
import com.example.astro.R;

public class AdditionalFragment extends Fragment {

    public static TextView tvWindSpeed;
    public static TextView tvWindDirection;
    public static TextView tvHumidity;
    public static TextView tvVisibility;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_additional, container, false);

        tvWindSpeed =  (TextView)rootView.findViewById(R.id.tvWindSpeed);
        tvWindDirection = (TextView)rootView.findViewById(R.id.tvWindDirection);
        tvHumidity = (TextView)rootView.findViewById(R.id.tvHumidity);
        tvVisibility = (TextView)rootView.findViewById(R.id.tvVisibility);

        DataHandlerAdditional process = new DataHandlerAdditional(getContext());
        process.execute();

        return rootView;
    }
}
