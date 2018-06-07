package com.example.virginia.panadelivery.Fragments;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


public class PedidoConductorFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private String emailCliente;
    private String emailConductor;
    private String TAG = "resultConductor";
    private TextView textViewEstado2;
    private TextView textViewMonto2;
    private TextView textViewConductor2;
    private TextView textViewPanaderia2;
    private Button buttonEstado;
    private String idPedido;



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

         View view = inflater.inflate(R.layout.fragment_pedido_conductor, container, false);

        buttonEstado = (Button) view.findViewById(R.id.buttonLogin);
        textViewEstado2 = (TextView) view.findViewById(R.id.textViewEstado2);
        textViewMonto2 = (TextView) view.findViewById(R.id.textViewMonto2);
        textViewPanaderia2 = (TextView) view.findViewById(R.id.textViewPanaderia2);
        textViewConductor2 = (TextView) view.findViewById(R.id.textViewConductor2);

        obtenerPedidoActual();

        buttonEstado.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DocumentReference resultado = db.collection("Usuarios").document(emailCliente)
                    .collection("pedidos").document(idPedido);
                Map<String, Object> actualizarEstado = new HashMap<>();

                if(textViewEstado2.equals("En proceso")){
                    actualizarEstado.put("estado", "En tránsito");
                    resultado.set(actualizarEstado, SetOptions.merge());
                    textViewEstado2.setText("En tránsito");
                }
                if(textViewEstado2.equals("En tránsito")){
                    actualizarEstado.put("estado", "Completado");
                    resultado.set(actualizarEstado, SetOptions.merge());
                    textViewEstado2.setText("Completado");
                }
                if(textViewEstado2.equals("Completado")){
                    actualizarEstado.put("estado", "Entregado");
                    resultado.set(actualizarEstado, SetOptions.merge());
                    textViewEstado2.setText("Entregado");
                }

                configurarBoton(textViewEstado2);
            }
        });
        return view;

    }

    @SuppressLint("ResourceAsColor")
    private void configurarBoton(TextView estado){
        if(estado.equals("En proceso")){
            buttonEstado.setText("En tránsito");
            buttonEstado.setBackgroundColor(R.color.colorAzul);
        }
        if(estado.equals("En tránsito")){
            buttonEstado.setText("Completado");
            buttonEstado.setBackgroundColor(R.color.colorMorado);
        }
        if(estado.equals("Completado")){
            buttonEstado.setText("Entregado");
            buttonEstado.setBackgroundColor(R.color.colorVerde);
        }

    }

    private void obtenerPedidoActual() {
        if(getArguments()!=null){
            emailCliente= getArguments().getString("emailCliente");
            emailConductor= getArguments().getString("emailConductor");
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
                                idPedido=doc.getDocument().getId();
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
                                configurarBoton(textViewEstado2);
                            }
                        }
                    }
                });
            }
        }

    }
}
