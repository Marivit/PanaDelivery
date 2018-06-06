package com.example.virginia.panadelivery.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.virginia.panadelivery.Adapters.CheckoutListAdapter;
import com.example.virginia.panadelivery.Adapters.PanaderiasListAdapter;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
       private List<Producto> checkout;
       private RecyclerView listaCheckout;
       private  CheckoutListAdapter checkoutListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ArrayList<Producto> lCheckout = getIntent().getBundleExtra("listas").getParcelableArrayList("lCheckout");

        setContentView(R.layout.activity_checkout);

        listaCheckout =  (RecyclerView) findViewById(R.id.listaCheckout);





        checkoutListAdapter = new CheckoutListAdapter(lCheckout);
        listaCheckout.setLayoutManager(new LinearLayoutManager(this));
        listaCheckout.setAdapter(checkoutListAdapter);
        checkoutListAdapter.notifyDataSetChanged();
    }
}
