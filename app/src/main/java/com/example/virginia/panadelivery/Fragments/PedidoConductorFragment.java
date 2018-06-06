package com.example.virginia.panadelivery.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.virginia.panadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;


public class PedidoConductorFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private String emailCliente;
    private String idPedido;
    private String TAG = "resultConductor";
    private TextView textViewEstado2;
    private TextView textViewMonto2;
    private TextView textViewConductor2;
    private TextView textViewPanaderia2;
    private Button buttonEstado;



    public PedidoConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        final String  emailConductor= firebaseAuth.getCurrentUser().getEmail();

        if(getArguments()!=null){
            emailCliente= getArguments().getString("emailCliente");
            idPedido= getArguments().getString("idPedido");
            Log.d(TAG, emailCliente);
            Log.d(TAG, idPedido);
            Query resultado = db
                    .collection("Usuarios").document(emailCliente)
                    .collection("pedidos").whereEqualTo("estado","En proceso").whereEqualTo("conductorEmail", emailConductor);

            if(resultado!=null){
                resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.d(TAG, e.getMessage());

                        }
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            //TODO: Agregar modified

                            if(doc.getType() == DocumentChange.Type.ADDED) {
                                String estado = "";
                                estado = doc.getDocument().getString("estado");
                                Log.d(TAG, estado);
                                String conductor = "";
                                conductor = doc.getDocument().getString("conductor");
                                String monto = "";
                                monto = doc.getDocument().getString("montoTotal");
                                String panaderia = "";
                                panaderia = doc.getDocument().getString("panaderia");

                                textViewEstado2.setText(estado);
                                textViewMonto2.setText(monto);
                                textViewConductor2.setText(conductor);
                                textViewPanaderia2.setText(panaderia);
                                configurarBoton(estado);
                            }
                        }
                    }
                });
            }
        }

        return inflater.inflate(R.layout.fragment_pedido_conductor, container, false);

    }

    private void configurarBoton(String estado){
        if(estado=="En proceso"){
            buttonEstado.setText("En tránsito");
            //buttonEstado.setBackgroundColor(R.color.colorAzul);
        }

    }
}
