package com.example.virginia.panadelivery.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.virginia.panadelivery.Adapters.PanaderiasListAdapter;
import com.example.virginia.panadelivery.Adapters.ProductosListAdapter;
import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class testActivity extends AppCompatActivity {
    private RecyclerView listaPanaderias;
    private String TAG = "Firelog";
    private List<Panaderia> panaderias;
    private PanaderiasListAdapter panaderiasListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment inicial

        /*
        listaProductos = (RecyclerView) findViewById(R.id.productos);
        productos = new ArrayList<>();
        productosListAdapter = new ProductosListAdapter(productos);
        listaProductos.setHasFixedSize(true);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));
        listaProductos.setAdapter(productosListAdapter);
        */

        listaPanaderias = (RecyclerView) findViewById(R.id.listaPanaderias);

        panaderias = new ArrayList<>();
        panaderiasListAdapter = new PanaderiasListAdapter(panaderias);
        listaPanaderias.setHasFixedSize(true);
        listaPanaderias.setLayoutManager(new LinearLayoutManager(this));
        listaPanaderias.setAdapter(panaderiasListAdapter);


        db.collection("Panaderias").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, e.getMessage());

                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    //TODO: Agregar modified

                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String name = doc.getDocument().getString("nombre");
                        Log.d(TAG, name);

                        Panaderia panaderia = doc.getDocument().toObject(Panaderia.class);

                        panaderias.add(panaderia);
                        Log.d(TAG, Integer.toString(panaderiasListAdapter.getItemCount()));
                        panaderiasListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}