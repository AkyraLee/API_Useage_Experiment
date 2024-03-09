package com.example.api_useage_experiment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class MainActivity extends AppCompatActivity {

    // member variable for holding the ImageView
    // in which images will be loaded
    ImageView mDogImageView;
    Button nextDogButton;

    double currentWeather = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the ImageView and the Button
        mDogImageView = findViewById(R.id.dogImageView);
        nextDogButton = findViewById(R.id.nextDogButton);

        // attaching on click listener to the button so that `loadDogImage()`
        // function is called everytime after clicking it.
        nextDogButton.setOnClickListener(View -> loadAirPollution());

        // image of a dog will be loaded once at the start of the app
//        loadDogImage();

        loadAirPollution();
    }

    private void loadDogImage() {
        // getting a new volley request queue for making new requests
        RequestQueue volleyQueue = Volley.newRequestQueue(MainActivity.this);
        // url of the api through which we get random dog images
        String url = "https://dog.ceo/api/breeds/image/random";

        // since the response we get from the api is in JSON, we
        // need to use `JsonObjectRequest` for parsing the
        // request response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                // we are using GET HTTP request method
                Request.Method.GET,
                // url we want to send the HTTP request to
                url,
                // this parameter is used to send a JSON object to the
                // server, since this is not required in our case,
                // we are keeping it `null`
                null,

                // lambda function for handling the case
                // when the HTTP request succeeds
                (Response.Listener<JSONObject>) response -> {
                    // get the image url from the JSON object
                    String dogImageUrl;
                    try {
                        dogImageUrl = response.getString("message");
                        // load the image into the ImageView using Glide.
                        Glide.with(MainActivity.this).load(dogImageUrl).into(mDogImageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                // lambda function for handling the case
                // when the HTTP request fails
                (Response.ErrorListener) error -> {
                    // make a Toast telling the user
                    // that something went wrong
                    Toast.makeText(MainActivity.this, "Some error occurred! Cannot fetch dog image", Toast.LENGTH_LONG).show();
                    // log the error message in the error stream
                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}");
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }



//    public float getTemperatureFromJson(String jsonResponse) {
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
//
//        if (jsonObject.has("main")) {
//            JsonObject mainObject = jsonObject.getAsJsonObject("main");
//            if (mainObject.has("temp")) {
//                // Assuming temperature is in Celsius
//                float temperature = mainObject.get("temp").getAsFloat();
//                return temperature;
//            }
//        }
//    }


    private void loadWeather() {
        // getting a new volley request queue for making new requests
        RequestQueue volleyQueue = Volley.newRequestQueue(MainActivity.this);
        // url of the api through which we get random dog images
        String url_currentWeather = "https://api.openweathermap.org/data/2.5/weather?q=Irvine,us&APPID=8bfe49aded06faec8f335c6abf44e846";

        // since the response we get from the api is in JSON, we
        // need to use `JsonObjectRequest` for parsing the
        // request response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                // we are using GET HTTP request method
                Request.Method.GET,
                // url we want to send the HTTP request to
                url_currentWeather,
                // this parameter is used to send a JSON object to the
                // server, since this is not required in our case,
                // we are keeping it `null`
                null,

                // lambda function for handling the case
                // when the HTTP request succeeds
                (Response.Listener<JSONObject>) response -> {
                    // get the image url from the JSON object
                    try {

                        JSONObject coordObject = response.getJSONObject("main");
                        String temp = coordObject.getString("temp");

                        coordObject = response.getJSONObject("coord");
                        String coordinates = coordObject.getString("lat") + ", " + coordObject.getString("lon");

                        Toast.makeText(this, "Coordinates: " + coordinates, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Temperature: " + temp, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                // lambda function for handling the case
                // when the HTTP request fails
                (Response.ErrorListener) error -> {
                    // make a Toast telling the user
                    // that something went wrong
                    Toast.makeText(MainActivity.this, "ERROR CANT GET WEATHER", Toast.LENGTH_LONG).show();
                    // log the error message in the error stream
                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}");
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }




    private void loadAirPollution() {
        // getting a new volley request queue for making new requests
        RequestQueue volleyQueue = Volley.newRequestQueue(MainActivity.this);
        // url of the api through which we get random dog images
        //ENSURE: string starts with https://
        String url_currentPollution = "https://api.openweathermap.org/data/2.5/air_pollution?lat=33.6695&lon=-117.8231&appid=8bfe49aded06faec8f335c6abf44e846";

        // since the response we get from the api is in JSON, we
        // need to use `JsonObjectRequest` for parsing the
        // request response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                // we are using GET HTTP request method
                Request.Method.GET,
                // url we want to send the HTTP request to
                url_currentPollution,
                // this parameter is used to send a JSON object to the
                // server, since this is not required in our case,
                // we are keeping it `null`
                null,

                // lambda function for handling the case
                // when the HTTP request succeeds
                (Response.Listener<JSONObject>) response -> {
                    // get the image url from the JSON object

                    try {

                        JSONArray listArray = response.getJSONArray("list");

                        for (int i = 0; i < listArray.length(); i++) {
                            JSONObject listItem = listArray.getJSONObject(i);
                            JSONObject mainObject = listItem.getJSONObject("main");
                            int aqi = mainObject.getInt("aqi");
                            Toast.makeText(this, "Air Quality Index: " + aqi, Toast.LENGTH_SHORT).show();
                        }

//                        String coord = response.getString("coord");
//                        Toast.makeText(this, "Coordinate: " + coord, Toast.LENGTH_SHORT).show();

//                        JSONObject coordObject = response.getJSONObject("list").getJSONObject("0").getJSONObject("main");
//                        JSONObject coordObject = response.getJSONObject("list").getJSONObject("main");
//                        String aqi = coordObject.getString("aqi");
//
//                        Toast.makeText(this, "Air Quality Index: " + aqi, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                // lambda function for handling the case
                // when the HTTP request fails
                (Response.ErrorListener) error -> {
                    // make a Toast telling the user
                    // that something went wrong
                    Toast.makeText(MainActivity.this, "ERROR CANT GET AQI", Toast.LENGTH_LONG).show();
                    // log the error message in the error stream
                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}");
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }





}