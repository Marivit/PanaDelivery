package com.example.virginia.panadelivery.Services;

import com.google.android.gms.common.api.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpService {

    public HttpService() {

    }

    public String getTiempo(String latitud, String longitud) throws IOException {

        APIKeys ApiKeys = new APIKeys();
        String key = ApiKeys.getDirectionsKey();
        URL DirectionsApi = new URL("https://maps.googleapis.com/maps/api/directions/");
        HttpsURLConnection myConnection = (HttpsURLConnection) DirectionsApi.openConnection();

        return "lol";
}
