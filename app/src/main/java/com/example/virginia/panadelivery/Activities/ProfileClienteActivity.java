package com.example.virginia.panadelivery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.virginia.panadelivery.Adapters.PanaderiasListAdapter;
import com.example.virginia.panadelivery.Fragments.HistorialClienteFragment;
import com.example.virginia.panadelivery.Fragments.PanaderiasListFragment;
import com.example.virginia.panadelivery.Fragments.PedidoClienteFragment;
import com.example.virginia.panadelivery.Fragments.PedidoClienteVacioFragment;
import com.example.virginia.panadelivery.Fragments.ProductosListFragment;
import com.example.virginia.panadelivery.Fragments.detalleHistorialFragment;
import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class    ProfileClienteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private FirebaseAuth firebaseAuth;
        private FirebaseFirestore db = FirebaseFirestore.getInstance();
        private TextView name;
        private TextView email;

        private String TAG = "Firelog";


        //ELIMINAR
        private RecyclerView listaPanaderias;

    private List<Panaderia> panaderias;
    private PanaderiasListAdapter panaderiasListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.contenedorCliente, new PanaderiasListFragment(), "PANADERIAS");
        ft.commit();

        /*
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

                    if(doc.getType() == DocumentChange.Type.ADDED) {
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
*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentSeleccionado = fm.findFragmentById(R.id.contenedorCliente);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fragmentSeleccionado instanceof ProductosListFragment) {
            fm.beginTransaction().replace(R.id.contenedorCliente, new PanaderiasListFragment(), "PANADERIAS").commit();
        }
        else if (fragmentSeleccionado instanceof detalleHistorialFragment) {
            fm.beginTransaction().replace(R.id.contenedorCliente, new HistorialClienteFragment()).commit();
        }

        else {
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
            fragmentManager.beginTransaction().replace(R.id.contenedorCliente, new PanaderiasListFragment()).commit();
        } else if (id == R.id.nav_historial) {
            fragmentManager.beginTransaction().replace(R.id.contenedorCliente, new HistorialClienteFragment()).commit();
        } else if (id == R.id.nav_pedido) {
            fragmentManager.beginTransaction().replace(R.id.contenedorCliente, new PedidoClienteFragment()).commit();
        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(ProfileClienteActivity.this, MainActivity.class));
        } else if (id == R.id.nav_maps) { //Esto es una prueba para implementar en el transportista
            startActivity(new Intent(this, MapsActivity.class));
        } else if (id == R.id.nav_send){
            startActivity(new Intent(this, ProfileConductorActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void mostrarEmpty(){
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorCliente, new PedidoClienteVacioFragment() ).commit();
    }

    public void seleccionarPedidoActualDrawer() {
       NavigationView nav = findViewById(R.id.nav_view);
       nav.getMenu().getItem(2).setChecked(true);
       nav.getMenu().getItem(1).setChecked(false);
    }

}
