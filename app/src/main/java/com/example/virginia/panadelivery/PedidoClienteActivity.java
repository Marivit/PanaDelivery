package com.example.virginia.panadelivery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;



public class PedidoClienteActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;
    private TextView getDireccion;
    int PLACE_PICKER_REQUEST=1;
    private GoogleApiClient mGoogleApiClient;
    private boolean mLocationPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_cliente);

        /*mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .build();*/

        getDireccion = (TextView) findViewById(R.id.textDireccion);
        getDireccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*if (ActivityCompat.checkSelfPermission( PedidoClienteActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PedidoClienteActivity.this, "Faltan los permisos", Toast.LENGTH_LONG).show();
                    return;
                }*/

                if (ContextCompat.checkSelfPermission(PedidoClienteActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                } else {
                    ActivityCompat.requestPermissions(PedidoClienteActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }

                try {

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent i = builder.build(PedidoClienteActivity.this);
                    startActivityForResult(i, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    //Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    //Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
                } catch (Exception e) {
                    //Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode== RESULT_OK){
                Place place= PlacePicker.getPlace(this, data);
                if (place == null) {
                    Toast.makeText(this, "No place selected", Toast.LENGTH_LONG).show();
                    //Log.i(TAG, "No place selected");
                    return;
                }
                String address = String.format("Place: %s", place.getAddress());
                //Mensajito
                Toast.makeText(this, address, Toast.LENGTH_LONG).show();
                getDireccion.setText(address);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        //updateLocationUI();
    }
}
