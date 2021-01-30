package com.example.astro;

import android.content.Context;
import android.os.AsyncTask;

import com.example.astro.fragments.BasicFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DataHandlerBasic extends AsyncTask<Void, Void, Void> {
    String data = "";
    String name = "";
    String temperature = "";
    String decsription = "";
    String coords = "";
    String weatherCategory = "";
    public static String longi;
    public static String lati;
    public static String userString;

    public static Context mContext;

    public DataHandlerBasic (Context context){
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
                //BasicData
                String longitude;
                String latitude;
                float lon;
                float lat;
                name = jsonObject1.getString("name");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("main");
                if (bUnits == false) {
                    temperature = jsonObject2.getString("temp") + "°C";
                } else {
                    temperature = jsonObject2.getString("temp") + "°F";
                }
                JSONObject jsonObject3 = jsonObject1.getJSONObject("coord");
                lon = BigDecimal.valueOf(jsonObject3.getDouble("lon")).floatValue();
                lat = BigDecimal.valueOf(jsonObject3.getDouble("lat")).floatValue();
                longitude = String.valueOf(lon);
                longi = longitude;
                latitude = String.valueOf(lat);
                lati = latitude;
                longitude = splitCoords(longitude);
                latitude = splitCoords(latitude);
                coords = longitude + " " + latitude;

                JSONArray jsonArray = jsonObject1.getJSONArray("weather");
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    decsription = jsonObject.getString("description");
                    weatherCategory = jsonObject.getString("main");
                }

                JSONObject jsonObject8 = new JSONObject();
                jsonObject8.put("name", name);
                jsonObject8.put("temperature", temperature);
                jsonObject8.put("description", decsription);
                jsonObject8.put("coords", coords);
                jsonObject8.put("weather", weatherCategory);
                String userString = jsonObject8.toString();
                saveToJSON(mContext,userString, "basic.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                String str = readJSON(mContext, "basic.json");

                JSONObject jsonObject = new JSONObject(str);
                name = jsonObject.getString("name");
                temperature = jsonObject.getString("temperature");
                coords = jsonObject.getString("coords");
                decsription = jsonObject.getString("description");
                weatherCategory = jsonObject.getString("weather");


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        BasicFragment.tvCity.setText(this.name);
        BasicFragment.tvTemperature.setText(this.temperature);
        BasicFragment.tvDescr.setText(this.decsription);
        BasicFragment.tvCoords.setText(this.coords);

        if(weatherCategory.equals("Clear")){
            BasicFragment.ivImage.setImageResource(R.drawable.clear);
        }else if(weatherCategory.equals("Thunderstorm")){
            BasicFragment.ivImage.setImageResource(R.drawable.thunderstorm);
        }else if(weatherCategory.equals("Rain") || weatherCategory.equals("Drizzle")){
            BasicFragment.ivImage.setImageResource(R.drawable.rain);
        }else if(weatherCategory.equals("Mist") || weatherCategory.equals("Haze") || weatherCategory.equals("Fog")) {
            BasicFragment.ivImage.setImageResource(R.drawable.mist);
        }else if(weatherCategory.equals("Snow")){
            BasicFragment.ivImage.setImageResource(R.drawable.snow);
        }else if(weatherCategory.equals("Clouds")){
            BasicFragment.ivImage.setImageResource(R.drawable.clouds);
        }

    }
    private String splitCoords(String coord){

        String[] parts = coord.split("\\.");
        String minutes;
        String degrees = parts[0] + "°";
        if(!parts[1].isEmpty()){
            minutes = parts[1].substring(0, 2) + "'";
        }else{
            minutes = "00'";
        }

        String result = degrees + minutes;

        return result;
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

    public static void saveToJSON(Context context, String userString, String filename) throws IOException {
        File file = new File(context.getFilesDir(),filename);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();
    }
    public static String readJSON(Context context, String filename) throws IOException {
        File file = new File(context.getFilesDir(),filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        return String.valueOf(stringBuilder);
    }
}
