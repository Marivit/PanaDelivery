package com.example.virginia.panadelivery;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.virginia.panadelivery.Modelos.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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

        listaProductos = (RecyclerView) findViewById(R.id.productos);
        productos = new ArrayList<>();
        productosListAdapter = new ProductosListAdapter(productos);

        listaProductos.setLayoutManager(new LinearLayoutManager(this));
        listaProductos.setAdapter(productosListAdapter);
        //Fragment inicial



        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */




        // Prueba FireStore

        db.collection("Productos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, e.getMessage());

                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    //TODO: Agregar modified

                    if(doc.getType() == DocumentChange.Type.ADDED) {
                        String name = doc.getDocument().getString("nombre");
                        Log.d(TAG, name);

                        Producto producto = doc.getDocument().toObject(Producto.class);

                        productos.add(producto);
                        Log.d(TAG, Integer.toString(productosListAdapter.getItemCount()));
                        productosListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
