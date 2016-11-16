package com.example.robert.lab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ForecastQuery foreQuery;
    TextView txtv1, txtv2, txtv3;
    ImageView iv;
    public String url = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            foreQuery = new ForecastQuery();
            foreQuery.execute(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        ProgressBar bar2;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            bar2 = (ProgressBar) findViewById(R.id.progressBar);
            bar2.setVisibility(View.VISIBLE);
            bar2.setProgress(progress[0]);
            if (progress[0] == 100) {
                bar2.setVisibility(View.INVISIBLE);
            }
        }

        protected void onPostExecute(String result) {
            txtv1 = (TextView) findViewById(R.id.textView4);
            txtv2 = (TextView) findViewById(R.id.textView5);
            txtv3 = (TextView) findViewById(R.id.textView6);

            String[] ss = result.split(" ");
            txtv1.setText("Min: " + ss[0]);
            txtv2.setText("Current: " + ss[2]);
            txtv3.setText("Max: " + ss[1]);

            iv = (ImageView) findViewById(R.id.imageView3);
            try {

                String filename = ss[4];
                switch (filename) {
                    case "broken":
                        filename = "broken";
                        break;
                    case "sunny":
                        filename = "sunny";
                        break;
                    case "overcast":
                        filename = "overcast";
                        break;
                    default:
                        filename = "clear";
                        break;
                }
                FileInputStream inputStream;
                inputStream = openFileInput(filename);
                iv.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... params) {
            Integer progress = 0;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();
                int eventType = parser.getEventType();
                StringBuilder sb = new StringBuilder();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                    } else if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("temperature")) {
                            if (parser.getAttributeCount() > 0)
                                for (int i = 0; i < parser.getAttributeCount(); i++) {
                                    sb.append((parser.getAttributeValue(i) + " "));
                                    publishProgress(progress += 25);
                                    SystemClock.sleep(300);
                                }
                        }
                        if (parser.getName().equals("weather")) {
                            if (parser.getAttributeCount() > 0)
                                sb.append((parser.getAttributeValue(1)));
                            String filename;
                            FileOutputStream outputStream;

                            switch (parser.getAttributeValue(1)) {
                                case "sunny":
                                    try {
                                        filename = "sunny";
                                        File file = new File(filename);
                                        if(!file.exists()) {
                                            HttpUtils hu = new HttpUtils();
                                            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                            hu.getImage("http://www.freeiconspng.com/uploads/sunny-icon-17.png")
                                                    .compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                            outputStream.close();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), "FileNotFound", Toast.LENGTH_LONG);
                                        e.printStackTrace();
                                    }
                                    break;
                                case "broken clouds":
                                    try {
                                        filename = "broken";
                                        File file = new File(filename);
                                        if(!file.exists()) {
                                            HttpUtils hu = new HttpUtils();
                                            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                            hu.getImage(
                                                    "http://www.clipartkid.com/images/727/today-s-weather-is-mostly-cloudy-with-a-qrV0Q1-clipart.jpg")
                                                    .compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                            outputStream.close();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), "FileNotFound", Toast.LENGTH_LONG);
                                        e.printStackTrace();
                                    }
                                case "overcast clouds":
                                    try {
                                        filename = "overcast";
                                        File file = new File(filename);
                                        if(!file.exists()) {
                                            HttpUtils hu = new HttpUtils();
                                            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                            hu.getImage(
                                                    "http://www.clipartkid.com/images/727/today-s-weather-is-mostly-cloudy-with-a-qrV0Q1-clipart.jpg")
                                                    .compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                            outputStream.close();
                                        }

                                    } catch (Exception e) {
                                       // Toast.makeText(getBaseContext(), "FileNotFound", Toast.LENGTH_LONG);
                                        e.printStackTrace();
                                    }
                                default:
                                    try {
                                        filename = "clear";
                                        File file = new File(filename);
                                        if(!file.exists()) {
                                            HttpUtils hu = new HttpUtils();
                                            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                            hu.getImage(
                                                    "https://investmentscientist.files.wordpress.com/2011/11/clear-sky.jpg")
                                                    .compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                            outputStream.close();
                                        }
                                    } catch(FileNotFoundException e) {
                                        Toast.makeText(getBaseContext(), "FileNotFound", Toast.LENGTH_LONG);
                                        e.printStackTrace();
                                    }
                            }
                            break;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                    } else if (eventType == XmlPullParser.TEXT) {
                    }
                    eventType = parser.next();
                }
                return sb.toString();
            } catch (XmlPullParserException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    class HttpUtils {
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }
}