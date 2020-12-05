package com.example.astro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astro.fragments.SunFragment;

public class SettingsActivity extends AppCompatActivity {


    private Spinner spinnerFreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etLatitudeDeg = (EditText)findViewById(R.id.etLatitudeDeg);
                EditText etLatitudeMin = (EditText)findViewById(R.id.etLatitudeMin);
                EditText etLongitudeDeg = (EditText)findViewById(R.id.etLongitudeDeg);
                EditText etLongitudeMin = (EditText)findViewById(R.id.etLongitudeMin);
                String sLatitudeDeg = etLatitudeDeg.getText().toString();
                String sLatitudeMin = etLatitudeMin.getText().toString();
                String sLongitudeDeg = etLongitudeDeg.getText().toString();
                String sLongitudeMin = etLongitudeMin.getText().toString();

                spinnerFreq = (Spinner)findViewById(R.id.spinnerFreq);
                fillRefreshTime();

                String err1 = "Formularz niekompletny";
                String err2 = "Nieprawidłowa szerokość geograficzna";
                String err3 = "Nieprawidłowa długość geograficzna";
                String succ = "Zapisano dane";
                boolean errorFlag1 = true;
                boolean errorFlag2 = true;
                boolean errorFlag3 = true;
                String sLatitude;
                String sLongitude;

                if(sLatitudeDeg.isEmpty()){
                    errorFlag1 = false;
                }else{
                    int iLatitudeDeg = Integer.parseInt(sLatitudeDeg);
                    if(iLatitudeDeg > 90 || iLatitudeDeg < -90) {
                        errorFlag2 = false;
                    }else{
                    }
                }
                if(sLatitudeMin.isEmpty()) {
                    errorFlag1 = false;
                }else {
                    int iLatitudeMin = Integer.parseInt(sLatitudeMin);
                    if(iLatitudeMin > 60 || iLatitudeMin < 0) {
                        errorFlag2 = false;
                    }else{
                    }
                }
                if(sLongitudeDeg.isEmpty()){
                    errorFlag1 = false;
                }else{
                    int iLongitudeDeg = Integer.parseInt(sLongitudeDeg);
                    if(iLongitudeDeg > 180 || iLongitudeDeg < -180) {
                        errorFlag3 = false;
                    }else{

                    }
                }
                if(sLongitudeMin.isEmpty()){
                    errorFlag1 = false;
                }else{
                    int iLongitudeMin = Integer.parseInt(sLongitudeMin);
                    if(iLongitudeMin > 60 || iLongitudeMin < 0) {
                        errorFlag3 = false;
                    }else{
                    }
                }

                if(errorFlag1 == false){
                    toast(err1);
                }else{
                    if(errorFlag2 == false){
                        toast(err2);
                    }else{
                        if(errorFlag3 == false){
                            toast(err3);
                        }
                        else{
                            sLatitude = sLatitudeDeg + "." + sLatitudeMin;
                            sLongitude = sLongitudeDeg + "." + sLongitudeMin;
                            toast(succ);
                            //wysłanie danych do aktywności Astro
                            Intent myIntent = new Intent(SettingsActivity.this, SunFragment.class);
                            myIntent.putExtra("LATITUDE",sLatitude);
                            myIntent.putExtra("LONGITUDE",sLongitude);
                            startActivity(myIntent);
                        }
                    }

                }
            }
        });
    }
    public void toast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void fillRefreshTime(){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        adapter.add("10");
        adapter.add("15");
        adapter.add("30");
        spinnerFreq.setAdapter(adapter);

    }
}