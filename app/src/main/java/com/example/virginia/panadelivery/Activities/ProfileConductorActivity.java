package com.example.virginia.panadelivery.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.virginia.panadelivery.Fragments.EmptyPedidoFragment;
import com.example.virginia.panadelivery.Fragments.PedidoConductorFragment;
import com.example.virginia.panadelivery.Fragments.PedidosListFragment;
import com.example.virginia.panadelivery.Fragments.historialConductorFragment;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ProfileConductorActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private String TAG = "Prueba";
    private String TAG2 = "PEDIDO ACTUAL";
    private String TAG3 = "CorreoCliente";
    private static final int PERMISSIONS_REQUEST = 1;
    private List<Pedido> lPedidos;
    private Pedido pedidoActual;
    private List<String> listaEmails;
    private String correoCliente;
    private String correoConductor;
    private String correoUsuario;
    FragmentManager fragmentManager = getSupportFragmentManager();

    private TextView mTextMessage;

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.containerConductor, new PedidosListFragment()).commit();
                    return true;
                case R.id.navigation_pedidoActual:
                    fragmentManager.beginTransaction().replace(R.id.containerConductor, new PedidoConductorFragment()).commit();
                    return true;
                case R.id.navigation_historial:
                    fragmentManager.beginTransaction().replace(R.id.containerConductor, new historialConductorFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_conductor);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_pedidoActual);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerConductor,  new PedidoConductorFragment()).commit();

    }

    public void mostrarEmpty(){
        fragmentManager.beginTransaction().replace(R.id.containerConductor, new EmptyPedidoFragment()).commit();
    }

}
