package com.example.astro;

import android.content.Context;
import android.os.AsyncTask;

import com.example.astro.fragments.AdditionalFragment;
import com.example.astro.fragments.BasicFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DataHandlerAdditional extends AsyncTask<Void, Void, Void> {
    String data = "";
    String windSpeed = "";
    String windDirection = ""; //in degrees
    String humidity = "";
    String visibility = "";

    public static Context mContext;

    public DataHandlerAdditional (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(isConnectedToServer(1000) == true) {
            try {
                String city;
                String units;
                boolean bUnits;

                city = BasicFragment.city;
                bUnits = BasicFragment.units;
                if (bUnits == false) {
                    units = "metric";
                } else {
                    units = "imperial";
                }

                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=" + units + "&appid=1778e1fb21f584645f258e134cdf81c4");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }

                JSONObject jsonObject1 = new JSONObject(data);
                JSONObject jsonObject2 = jsonObject1.getJSONObject("main");
                JSONObject jsonObject4 = jsonObject1.getJSONObject("wind");
                if (bUnits == false) {
                    windSpeed = jsonObject4.getString("speed") + "m/s";
                } else {
                    windSpeed = jsonObject4.getString("speed") + "mph";
                }
                windDirection = jsonObject4.getString("deg") + "°";
                humidity = jsonObject2.getString("humidity") + "%";
                visibility = jsonObject1.getString("visibility") + "m";

                JSONObject jsonObject8 = new JSONObject();
                jsonObject8.put("wspeed", windSpeed);
                jsonObject8.put("wdirection", windDirection);
                jsonObject8.put("humidity", humidity);
                jsonObject8.put("visibility", visibility);

                String userString = jsonObject8.toString();
                DataHandlerBasic.saveToJSON(mContext,userString, "additional.json");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                String str = DataHandlerBasic.readJSON(mContext, "additional.json");
                JSONObject jsonObject = new JSONObject(str);
                windSpeed = jsonObject.getString("wspeed");
                windDirection = jsonObject.getString("wdirection");
                humidity = jsonObject.getString("humidity");
                visibility = jsonObject.getString("visibility");
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        AdditionalFragment.tvWindSpeed.setText(this.windSpeed);
        AdditionalFragment.tvWindDirection.setText(this.windDirection);
        AdditionalFragment.tvHumidity.setText(this.humidity);
        AdditionalFragment.tvVisibility.setText(this.visibility);
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
