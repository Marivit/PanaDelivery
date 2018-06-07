package com.example.virginia.panadelivery.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.virginia.panadelivery.Fragments.PedidoConductorFragment;
import com.example.virginia.panadelivery.Fragments.PedidosListFragment;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class ProfileConductorActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private String TAG = "Prueba";
    private String TAG2 = "PEDIDO ACTUAL";
    private String TAG3 = "CorreoCliente";

    private List<Pedido> lPedidos;
    private Pedido pedidoActual;
    private List<String> listaEmails;
    private String correoCliente;
    private String correoConductor;
    private String correoUsuario;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.containerConductor, new PedidosListFragment()).commit();
                    return true;
                case R.id.navigation_pedidoActual:
                    getPedidoActual();
                    //Pasar algunos parametros necesarios
                    Log.d(TAG3, correoCliente);
                    Log.d(TAG, correoConductor);
                    Bundle args = new Bundle();
                    args.putString("emailCliente", correoCliente);
                    args.putString("emailConductor", correoConductor);

                    //Cambiar de fragment al del pedido actual
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    final PedidoConductorFragment fragmentoP = new PedidoConductorFragment();
                    fragmentoP.setArguments(args);
                    ft.replace(R.id.containerConductor, fragmentoP);
                    ft.commit();

                    //fragmentManager.beginTransaction().replace(R.id.containerConductor, new PedidoConductorFragment()).commit();
                    return true;
                case R.id.navigation_historial:
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerConductor, new PedidosListFragment()).commit();


    }

    private void openFragmentPedido() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerConductor, new PedidoConductorFragment()).commit();
    }

    private void getPedidoActual(){

        listaEmails = new ArrayList<>();
        lPedidos = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        correoConductor = firebaseAuth.getCurrentUser().getEmail();

        db.collection("Usuarios").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    //TODO: Agregar modified

                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String email = doc.getDocument().getString("email");
                        listaEmails.add(email);
                    }
                }
                Log.d(TAG, String.valueOf(listaEmails));
                for(String i:listaEmails){
                    correoUsuario = i;
                    Query resultado = db
                            .collection("Usuarios").document(i)
                            .collection("pedidos").whereEqualTo("estado","En proceso");
                    resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            if (e != null) {
                                Log.d(TAG, e.getMessage());

                            }

                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                //TODO: Agregar modified

                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Pedido pedido = doc.getDocument().toObject(Pedido.class);
                                    if(pedido.getConductorEmail().equals(firebaseAuth.getCurrentUser().getEmail())){
                                        pedidoActual=pedido;
                                        Log.d(TAG2, String.valueOf(pedidoActual));
                                        Log.d(TAG, correoUsuario);
                                        correoCliente=correoUsuario;
                                    }
                                    //lPedidos.add(pedido);
                                    //Log.d(TAG, String.valueOf(pedido));
                                }

                            }

                        }
                    });
                }
            }
        });
    }

}
