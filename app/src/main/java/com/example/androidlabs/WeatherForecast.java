package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import android.util.Xml;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        currentTemp = findViewById(R.id.currentTemperature);
        minTemp =findViewById(R.id.minTemperature);
        maxTemp= findViewById(R.id.maxTemperature);
        uvRating= findViewById(R.id.uvRating);
        weatherImage= findViewById(R.id.currentWeather);

        ForecastQuery networkThreadOne = new ForecastQuery();
        networkThreadOne.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
        Log.i(ACTIVITY_NAME,"In onCreate()");
    }

    protected static Bitmap getImage(URL url) {
        Log.i(ACTIVITY_NAME, "In getImage");
        HttpURLConnection iconConn = null;
        try {
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            if (response == 200) {
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (iconConn != null) {
                iconConn.disconnect();
            }
        }
    }

    public boolean fileExistance(String fileName) {
        Log.i(ACTIVITY_NAME, "In fileExistance");
        Log.i(ACTIVITY_NAME, getBaseContext().getFileStreamPath(fileName).toString());
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String min,max,current,iconName;
        Bitmap icon;
        double aDouble;

        @Override
        protected String doInBackground(String... params) {
            Log.i(ACTIVITY_NAME, "In doInBackground");
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    if (parser.getName().equals("temperature")) {
                        current = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    if (parser.getName().equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName+".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            icon = BitmapFactory.decodeStream(inputStream);
                            Log.i(ACTIVITY_NAME, "Image already exists");
                        } else {
                            URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                            icon = getImage(iconUrl);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Log.i(ACTIVITY_NAME, "Adding new image");
                        }
                        Log.i(ACTIVITY_NAME, "file name="+iconFile);
                        publishProgress(100);
                    }


                    //Start of JSON reading of UV factor:

                    //create the network connection:
                    URL UVurl = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                    HttpURLConnection UVConnection = (HttpURLConnection) UVurl.openConnection();
                    InputStream inStream = UVConnection.getInputStream();


                    //create a JSON object from the response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString();

                    //now a JSON table:
                    JSONObject jObject = new JSONObject(result);
                    aDouble = jObject.getDouble("value");
                    publishProgress(125);
                    Log.i("UV is:", ""+ aDouble);

                    //END of UV rating

                    //Thread.sleep(20); //pause for 2000 milliseconds to watch the progress bar spin

                }
            }catch (Exception ex)
            {   ex.printStackTrace();
                Log.e("Crash!!", ex.getMessage() );
            }
            return null;


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String s){
            String degree = Character.toString((char) 0x00B0);
            currentTemp.setText(currentTemp.getText()+current+degree+"C");
            minTemp.setText(minTemp.getText()+min+degree+"C");
            maxTemp.setText(maxTemp.getText()+max+degree+"C");
            weatherImage.setImageBitmap(icon);
            uvRating.setText(uvRating.getText()+ String.valueOf(aDouble));
            progressBar.setVisibility(View.INVISIBLE);

        }

    }
    protected static final String ACTIVITY_NAME = "WeatherForecast";
    private ProgressBar progressBar;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private ImageView weatherImage;
    private TextView uvRating;


}
