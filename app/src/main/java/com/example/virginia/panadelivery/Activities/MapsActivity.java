package com.example.virginia.panadelivery.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.virginia.panadelivery.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_LOCATION_REQUEST_CODE=1;
    private static final int PLACE_PICKER_REQUEST=1;
    private GoogleMap mMap;
    private String valor_latitud;
    private String valor_longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        valor_latitud=bundle.getString("latitud");
        valor_longitud=bundle.getString("longitud");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       //LatLng sydney = new LatLng(10.501522500000002, -66.82144921874998);

        LatLng ubicacion = new LatLng(Double.parseDouble(valor_latitud), Double.parseDouble(valor_longitud));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker in MI CASA"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(ubicacion).zoom(14.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }

}
