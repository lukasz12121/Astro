package com.example.astro;

import android.content.Context;
import android.os.AsyncTask;

import com.example.astro.fragments.BasicFragment;
import com.example.astro.fragments.WeatherFragment;

import org.json.JSONArray;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataHandlerWeather extends AsyncTask<Void, Void, Void> {
    String day1;
    String day2;
    String day3;
    String day4;
    String day5;
    String weather1;
    String weather2;
    String weather3;
    String weather4;
    String weather5;
    String data = "";
    String description1;
    String description2;
    String description3;
    String description4;
    String description5;

    public static Context mContext;

    public DataHandlerWeather (Context context){
        mContext = context;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        if(isConnectedToServer(1000) == true) {
            try {
                String unit;
                String units;
                boolean bUnits;
                bUnits = BasicFragment.units;
                if (bUnits == false) {
                    units = "metric";
                    unit = "°C";
                } else {
                    units = "imperial";
                    unit = "°F";
                }
                URL url1 = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + DataHandlerBasic.lati + "6&lon=" + DataHandlerBasic.longi + "&units=" + units + "&exclude=hourly,minutely,current,alerts&appid=1778e1fb21f584645f258e134cdf81c4");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONObject jsonObjectDaily = new JSONObject(data);
                JSONArray jsonArrayDaily = jsonObjectDaily.getJSONArray("daily");
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> weather = new ArrayList<String>();
                ArrayList<String> description = new ArrayList<String>();
                ArrayList<String> temp = new ArrayList<String>();
                for (int i = 0; i < jsonArrayDaily.length(); ++i) {
                    JSONObject jsonObject = jsonArrayDaily.getJSONObject(i);
                    list.add(jsonObject.getString("dt"));
                    JSONArray jsonArray2 = jsonObject.getJSONArray("weather");
                    for (int j = 0; j < jsonArray2.length(); j++) {
                        JSONObject jsonObject3 = jsonArray2.getJSONObject(j);
                        weather.add(jsonObject3.getString("main"));
                        description.add(jsonObject3.getString("description"));
                    }
                    JSONObject jsonObject4 = jsonObject.getJSONObject("temp");
                    temp.add(jsonObject4.getString("day"));
                }

                ArrayList<String> dates = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    long lDate = Long.parseLong(list.get(i));
                    Date date = new java.util.Date(lDate * 1000L);
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(date);
                    dates.add(formattedDate);
                }
                day1 = dates.get(1);
                day2 = dates.get(2);
                day3 = dates.get(3);
                day4 = dates.get(4);
                day5 = dates.get(5);
                weather1 = weather.get(1);
                weather2 = weather.get(2);
                weather3 = weather.get(3);
                weather4 = weather.get(4);
                weather5 = weather.get(5);
                description1 = temp.get(1) + unit + " " + description.get(1);
                description2 = temp.get(2) + unit + " " + description.get(2);
                description3 = temp.get(3) + unit + " " + description.get(3);
                description4 = temp.get(4) + unit + " " + description.get(4);
                description5 = temp.get(5) + unit + " " + description.get(5);

                /*
                JSONArray jsonArray = jsonObject8.getJSONArray("daily");
                for(int i=1;i <= jsonArray.length();i++){
                    JSONObject jsonObject = jsonArrayDaily.getJSONObject(i);
                    jsonObject.put("dt", dates.get(i));
                    jsonObject.put("descr", temp.get(i) + unit + " " + description.get(i));
                    jsonObject.put("weather", weather.get(i));
                    jsonArray.add(jsonObject);
                }*/
                JSONObject jsonObject8 = new JSONObject();
                JSONObject jsonObject9 = new JSONObject();
                JSONObject jsonObject10 = new JSONObject();
                JSONObject jsonObject11 = new JSONObject();
                JSONObject jsonObject12 = new JSONObject();
                jsonObject8.put("dt",day1);
                jsonObject8.put("descr",description1);
                jsonObject8.put("weather",weather1);
                jsonObject9.put("dt",day2);
                jsonObject9.put("descr",description2);
                jsonObject9.put("weather",weather2);
                jsonObject10.put("dt",day3);
                jsonObject10.put("descr",description3);
                jsonObject10.put("weather",weather3);
                jsonObject11.put("dt",day4);
                jsonObject11.put("descr",description4);
                jsonObject11.put("weather",weather4);
                jsonObject12.put("dt",day5);
                jsonObject12.put("descr",description5);
                jsonObject12.put("weather",weather5);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject8);
                jsonArray.put(jsonObject9);
                jsonArray.put(jsonObject10);
                jsonArray.put(jsonObject11);
                jsonArray.put(jsonObject12);
                JSONObject jsonObject7 = new JSONObject();
                jsonObject7.put("daily",jsonArray);

                String userString = jsonObject7.toString();
                DataHandlerBasic.saveToJSON(mContext,userString, "daily.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                ArrayList<String> day = new ArrayList<String>();
                ArrayList<String> weather = new ArrayList<String>();
                ArrayList<String> description = new ArrayList<String>();
                String str = DataHandlerBasic.readJSON(mContext, "daily.json");
                JSONObject jsonObject = new JSONObject(str);
                //JSONObject jsonObject = new JSONObject((String) AstroActivity.dailyJson);
                JSONArray jsonArray = jsonObject.getJSONArray("daily");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    day.add(jsonObject1.getString("dt"));
                    weather.add(jsonObject1.getString("descr"));
                    description.add(jsonObject1.getString("weather"));
                }
                day1 = day.get(0);
                day2 = day.get(1);
                day3 = day.get(2);
                day4 = day.get(3);
                day5 = day.get(4);
                weather1 = description.get(0);
                weather2 = description.get(1);
                weather3 = description.get(2);
                weather4 = description.get(3);
                weather5 = description.get(4);
                description1 = weather.get(0);
                description2 = weather.get(1);
                description3 = weather.get(2);
                description4 = weather.get(3);
                description5 = weather.get(4);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        WeatherFragment.tvDay1.setText(this.day1);
        WeatherFragment.tvDay2.setText(this.day2);
        WeatherFragment.tvDay3.setText(this.day3);
        WeatherFragment.tvDay4.setText(this.day4);
        WeatherFragment.tvDay5.setText(this.day5);
        WeatherFragment.tvWeather1.setText(this.description1);
        WeatherFragment.tvWeather2.setText(this.description2);
        WeatherFragment.tvWeather3.setText(this.description3);
        WeatherFragment.tvWeather4.setText(this.description4);
        WeatherFragment.tvWeather5.setText(this.description5);


        if(weather1.equals("Clear")){
            WeatherFragment.ivImg1.setImageResource(R.drawable.clear);
        }else if(weather1.equals("Thunderstorm")){
            WeatherFragment.ivImg1.setImageResource(R.drawable.thunderstorm);
        }else if(weather1.equals("Rain") || weather1.equals("Drizzle")){
            WeatherFragment.ivImg1.setImageResource(R.drawable.rain);
        }else if(weather1.equals("Mist") || weather1.equals("Haze") || weather1.equals("Fog")) {
            WeatherFragment.ivImg1.setImageResource(R.drawable.mist);
        }else if(weather1.equals("Snow")){
            WeatherFragment.ivImg1.setImageResource(R.drawable.snow);
        }else if(weather1.equals("Clouds")){
            WeatherFragment.ivImg1.setImageResource(R.drawable.clouds);
        }
        if(weather2.equals("Clear")){
            WeatherFragment.ivImg2.setImageResource(R.drawable.clear);
        }else if(weather2.equals("Thunderstorm")){
            WeatherFragment.ivImg2.setImageResource(R.drawable.thunderstorm);
        }else if(weather2.equals("Rain") || weather2.equals("Drizzle")){
            WeatherFragment.ivImg2.setImageResource(R.drawable.rain);
        }else if(weather2.equals("Mist") || weather2.equals("Haze") || weather2.equals("Fog")) {
            WeatherFragment.ivImg2.setImageResource(R.drawable.mist);
        }else if(weather2.equals("Snow")){
            WeatherFragment.ivImg2.setImageResource(R.drawable.snow);
        }else if(weather2.equals("Clouds")){
            WeatherFragment.ivImg2.setImageResource(R.drawable.clouds);
        }
        if(weather3.equals("Clear")){
            WeatherFragment.ivImg3.setImageResource(R.drawable.clear);
        }else if(weather3.equals("Thunderstorm")){
            WeatherFragment.ivImg3.setImageResource(R.drawable.thunderstorm);
        }else if(weather3.equals("Rain") || weather3.equals("Drizzle")){
            WeatherFragment.ivImg3.setImageResource(R.drawable.rain);
        }else if(weather3.equals("Mist") || weather3.equals("Haze") || weather3.equals("Fog")) {
            WeatherFragment.ivImg3.setImageResource(R.drawable.mist);
        }else if(weather3.equals("Snow")){
            WeatherFragment.ivImg3.setImageResource(R.drawable.snow);
        }else if(weather3.equals("Clouds")){
            WeatherFragment.ivImg3.setImageResource(R.drawable.clouds);
        }
        if(weather4.equals("Clear")){
            WeatherFragment.ivImg4.setImageResource(R.drawable.clear);
        }else if(weather4.equals("Thunderstorm")){
            WeatherFragment.ivImg4.setImageResource(R.drawable.thunderstorm);
        }else if(weather4.equals("Rain") || weather4.equals("Drizzle")){
            WeatherFragment.ivImg4.setImageResource(R.drawable.rain);
        }else if(weather4.equals("Mist") || weather4.equals("Haze") || weather4.equals("Fog")) {
            WeatherFragment.ivImg4.setImageResource(R.drawable.mist);
        }else if(weather4.equals("Snow")){
            WeatherFragment.ivImg4.setImageResource(R.drawable.snow);
        }else if(weather4.equals("Clouds")){
            WeatherFragment.ivImg4.setImageResource(R.drawable.clouds);
        }
        if(weather5.equals("Clear")){
            WeatherFragment.ivImg5.setImageResource(R.drawable.clear);
        }else if(weather5.equals("Thunderstorm")){
            WeatherFragment.ivImg5.setImageResource(R.drawable.thunderstorm);
        }else if(weather5.equals("Rain") || weather5.equals("Drizzle")){
            WeatherFragment.ivImg5.setImageResource(R.drawable.rain);
        }else if(weather5.equals("Mist") || weather5.equals("Haze") || weather5.equals("Fog")) {
            WeatherFragment.ivImg5.setImageResource(R.drawable.mist);
        }else if(weather5.equals("Snow")){
            WeatherFragment.ivImg5.setImageResource(R.drawable.snow);
        }else if(weather5.equals("Clouds")){
            WeatherFragment.ivImg5.setImageResource(R.drawable.clouds);
        }
    }
    public static boolean isConnectedToServer(int timeout) {
        try {
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
