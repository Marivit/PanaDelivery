package com.example.virginia.panadelivery.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
       private List<Producto> checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }
}
