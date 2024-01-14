package com.devansh.weather;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainData extends AppCompatActivity {

    private static final String API_KEY = "ceecee58599f49ca96660005241401";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=";

    private EditText editTextCity;
    private ImageView search;
    private TextView city_name, updatetime, conditionTextview,temp,pressureview,windview,humidityview,updatedat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_data);
        editTextCity = findViewById(R.id.city_et);
        search = findViewById(R.id.search_bar_iv);
        city_name = findViewById(R.id.name_tv);
        updatetime = findViewById(R.id.updated_at_tv);
        conditionTextview = findViewById(R.id.conditionDesc_tv);
        temp = findViewById(R.id.temp_tv);
        updatedat = findViewById(R.id.update);
        pressureview = findViewById(R.id.pressure_tv);
        windview = findViewById(R.id.wind_tv);
        humidityview = findViewById(R.id.humidity_tv);


        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextCity.getWindowToken(), 0);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWeatherData();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextCity.getWindowToken(), 0);

            }
        });


        editTextCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Perform the action when the "Done" or "Enter" key is pressed
                    performAction();
                    return true;
                }
                return false;
            }
        });


    }



    private void getWeatherData() {
        String city = editTextCity.getText().toString().trim();
        String url = BASE_URL + city;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject current = response.getJSONObject("current");
                            String time = current.getString("last_updated");
                            String tempc = current.getString("temp_c");
                            String condition = current.getJSONObject("condition").getString("text");
                            String tempcview = tempc + " °C";

                            String wind = current.getString("wind_kph");
                            String pressure = current.getString("pressure_mb");
                            String humidity = current.getString("humidity");

                            String windviews = wind + " km/h";
                            String pressureviews = pressure + " mb";
                            String humidviews = humidity + "%";
                            city_name.setText(city);
                            editTextCity.setText("");
                            updatetime.setText(time);
                            windview.setText(windviews);
                            pressureview.setText(pressureviews);
                            temp.setText(tempcview);
                            humidityview.setText(humidviews);
                            conditionTextview.setText(condition);
                            updatedat.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainData.this, "Error fetching weather data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);


    }




    // Add the method to perform the action
    private void performAction() {
        getWeatherData();
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextCity.getWindowToken(), 0);

    }



}