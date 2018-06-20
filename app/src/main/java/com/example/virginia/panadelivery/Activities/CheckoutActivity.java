package com.example.virginia.panadelivery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.virginia.panadelivery.Adapters.CheckoutListAdapter;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;
import com.example.virginia.panadelivery.Services.FirestoreService;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Producto> checkout;
    private RecyclerView listaCheckout;
    private  CheckoutListAdapter checkoutListAdapter;
    private Button confirmarCheckout;
    private FirestoreService fss = new FirestoreService();
    private ArrayList<Producto> lCheckout;
    private String idPanaderia, nombrePanaderia;
    private int montoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        lCheckout = getIntent().getBundleExtra("listas").getParcelableArrayList("lCheckout");
        idPanaderia = getIntent().getBundleExtra("listas").getString("idPanaderia");
        nombrePanaderia = getIntent().getBundleExtra("listas").getString("nombrePanaderia");
        montoTotal = getIntent().getBundleExtra("listas").getInt("montoTotal");
        setContentView(R.layout.activity_checkout);

        listaCheckout =  (RecyclerView) findViewById(R.id.listaCheckout);
        confirmarCheckout = (Button) findViewById(R.id.confirmarCheckout);
        confirmarCheckout.setOnClickListener(this);



        checkoutListAdapter = new CheckoutListAdapter(lCheckout);
        listaCheckout.setLayoutManager(new LinearLayoutManager(this));
        listaCheckout.setAdapter(checkoutListAdapter);
        checkoutListAdapter.notifyDataSetChanged();
    }
    public void onClick(View view) {

        if (view == confirmarCheckout) {
            fss.checkout(lCheckout,idPanaderia, nombrePanaderia, montoTotal );
            Toast.makeText(this, "Se ha realizado el Checkout. ", Toast.LENGTH_SHORT).show();
            finish();

            startActivity(new Intent(this, MainActivity.class));
        }
    }


}
