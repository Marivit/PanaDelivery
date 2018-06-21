package com.example.virginia.panadelivery.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.HistorialPedidosAdapter;
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


public class HistorialClienteFragment extends Fragment {
    private FirebaseAuth fireAuth;
    private FirebaseFirestore db;
    private List<Pedido> historialPedidos;
    private RecyclerView vistaHistorial;
    private HistorialPedidosAdapter adaptador;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View mView = inflater.inflate(R.layout.fragment_historial_cliente, container, false);
        vistaHistorial = (RecyclerView)  mView.findViewById(R.id.historialPedidos);
        historialPedidos = new ArrayList<>();
        adaptador = new HistorialPedidosAdapter(historialPedidos);
        vistaHistorial.setHasFixedSize(true);
        vistaHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        vistaHistorial.setAdapter(adaptador);
        fireAuth = fireAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        final String email = fireAuth.getCurrentUser().getEmail();
        Query resultado = db
                .collection("Pedidos").whereEqualTo("cliente", email);

        if (resultado != null) {
            resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d("E", e.getMessage());
                    }
                    else {
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED ) {
                                if (Integer.parseInt(doc.getDocument().get("activo").toString())== 0){
                                    Pedido pedido = doc.getDocument().toObject(Pedido.class);
                                    historialPedidos.add(pedido);
                                    adaptador.notifyDataSetChanged();
                                }

                            }
                        }
                    }
                }
            });
        }
        return mView;
    }

}
