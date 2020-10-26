package com.example.represent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String API_KEY = "AIzaSyCqB0F1g1rCvjfQYtjlIg9KDz2HmI90h2w";
    final String CIVIC_URL = "https://www.googleapis.com/civicinfo/v2/representatives";
    final String GEO_URL = "https://www.googleapis.com/geolocation/v1/";

    EditText location;
    List<String> representatives;
    RequestQueue queue;
    Intent next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        location = findViewById(R.id.enter_location);
        location.setText("Enter an address or zipcode");
        final Button current = findViewById(R.id.current_location);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getRepresentativesFromLocation();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        final Button random = findViewById(R.id.random_location);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepresentativesFromAddress("92116");
                //startActivity(ListActivity);
                //generate a random location and pass reps into list view
            }
        });
        TextWatcher t = new TextWatcher() {
            int startingLen;
            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c)
            {
                String string = s.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            {
                startingLen = s.length();
            }
            @Override
            public void afterTextChanged(Editable s) {
                //pass location into list view
                String string = s.toString().trim();
                getRepresentativesFromAddress(string);
            }
        };
        location.addTextChangedListener(t);
    }

    public void getRepresentativesFromAddress(String address) {
        String place = "?address=" + address.replaceAll("\\s", "+");
        String full_url = CIVIC_URL + place + "&key=" + API_KEY;
        StringRequest req = new StringRequest(Request.Method.GET, full_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        //this.queue.add(req);//is this even right
        String r = req.toString();
        System.out.println(r);
        next = new Intent(this, ListActivity.class);
        startActivity(next);
    }

    public void getRepresentativesFromLocation() throws JSONException {
        String src = GEO_URL + "geolocate?" + "&key=" + API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, src, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response.toString());
                try {
                    pList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","");
            }
        });
        this.queue.add(req);//is this even right
        next = new Intent(this, ListActivity.class);
        //startActivity(next);
    }
    public void pList(Object response) throws JSONException {
        System.out.println(response.toString());
        JsonObject curr_location= new JsonParser().parse(response.toString()).getAsJsonObject();
        JsonElement elm = curr_location.get("location");
        String s = elm.toString();
        s = s.substring(1, s.length() - 2);
        System.out.println(s);
        String[] chars = s.split(",");
        chars[0] = chars[0].substring(6, chars[0].length() - 2);
        chars[1] = chars[1].substring(6);
        convertToAddress(chars);
    }
    public void convertToAddress(String[] cord) {
        String lat = cord[0];
        String lon = cord[1];
        String gps_url = "?latlng=" + lat + "," + lon;
        String full_url = GEO_URL + gps_url + "&key=" + API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, full_url, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response.toString());
                try {
                    getFormat(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","");
            }
        });
        System.out.println("got here");
        this.queue.add(req);
        next = new Intent(this, ListActivity.class);
        //startActivity(next);
    }
    public void getFormat(Object response) throws JSONException {
        System.out.println(response.toString());
        JsonObject curr_location= new JsonParser().parse(response.toString()).getAsJsonObject();
        JsonElement elm = curr_location.get("results");
        String s = elm.toString();
        System.out.println(s);
    }
}