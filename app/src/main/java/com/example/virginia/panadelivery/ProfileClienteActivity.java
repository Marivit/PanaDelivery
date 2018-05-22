package com.example.virginia.panadelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.virginia.panadelivery.Adapters.ProductosListAdapter;
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

public class ProfileClienteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_profile_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listaProductos = (RecyclerView) findViewById(R.id.productos);
        productos = new ArrayList<>();
        productosListAdapter = new ProductosListAdapter(productos);
        listaProductos.setHasFixedSize(true);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_cliente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_home) {
            //startActivity(new Intent(this, ProfileClienteActivity.class));
            fragmentManager.beginTransaction().replace(R.id.contenedorCliente, new panaderia_listFragment()).commit();
        } else if (id == R.id.nav_historial) {
            //fragmentManager.beginTransaction().replace(R.id.contenedorCliente, new historialClienteFragment()).commit();
        } else if (id == R.id.nav_pedido) {
            //fragmentManager.beginTransaction().replace(R.id.contenedorCliente, new pedidoClienteFragment()).commit();
        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(ProfileClienteActivity.this, MainActivity.class));
        } else if (id == R.id.nav_maps) {
            startActivity(new Intent(this, MapsActivity.class));
        }
        else if (id == R.id.nav_send) {
            startActivity(new Intent(this, testActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
