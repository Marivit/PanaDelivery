package com.example.virginia.panadelivery;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.virginia.panadelivery.Adapters.ProductosListAdapter;
import com.example.virginia.panadelivery.Modelos.Producto;
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
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView name;
    private TextView email;
    private RecyclerView listaProductos;
    private String TAG = "Firelog";
    private List<Producto> productos;
    private ProductosListAdapter productosListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.panaderias, new panaderia_listFragment()).commit();


    }
}
