package com.example.virginia.panadelivery.Services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpService {

    public HttpService() {

    }

    public String getTiempo(String latitudDestino, String longitudDestino, String latitudConductor, String longitudConductor) throws IOException {

        APIKeys ApiKeys = new APIKeys();
        String key = "&key=" + ApiKeys.getDirectionsKey();
        String direccionOrigen = "origin=" + latitudConductor + "," + longitudConductor;
        String direccionDestino = "&destination=" + latitudDestino + "," + longitudDestino;
        String request = "https://maps.googleapis.com/maps/api/directions/json?" + direccionOrigen + direccionDestino + key;
        Log.d("URL", request);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, request, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                            Log.d("RES",  response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        VolleyApp.getInstance().getRequestQueue().add(jsonObjectRequest);
        return "lol";
    }

}

