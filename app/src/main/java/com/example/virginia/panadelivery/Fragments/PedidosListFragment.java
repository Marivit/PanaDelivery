package com.example.virginia.panadelivery.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.PedidosListAdapter;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class PedidosListFragment extends Fragment {
    private RecyclerView listaPedidos;
    private String TAG = "Firelog";
    private String TAG2 = "Lista de emails";
    private String TAG3 = "bichito";
    private List<Pedido> lPedidos;
    private List<String> listaEmails;
    private PedidosListAdapter pedidosListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FloatingActionButton buttonUbicacion;
    private FloatingActionButton buttonElegir;

    public PedidosListFragment() {
        // Required empty public constructor
    }

    public void setListaPedidos(RecyclerView listaPedidos) {
        this.listaPedidos = listaPedidos;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_pedidos_list, container, false);
       // buttonUbicacion = (FloatingActionButton) mView.findViewById(R.id.buttonUbicacion);
        //bind();
        lPedidos = new ArrayList<>();

        listaPedidos = (RecyclerView) mView.findViewById(R.id.pedidosActuales);

        Context cont = getActivity().getApplicationContext();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        pedidosListAdapter = new PedidosListAdapter(lPedidos, cont, fm);
        listaPedidos.setHasFixedSize(true);
        listaPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaPedidos.setAdapter(pedidosListAdapter);
        //String id = this.getArguments().getString("id");

        //PRUEBA
        listaEmails = new ArrayList<>();
        /*Query resultado = db
                .collection("Usuarios").document("evitali44@gmail.com")
                .collection("pedidos").whereEqualTo("estado","En espera");*/

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
                        //Log.d(TAG, email);
                        listaEmails.add(email);
                        Log.d(TAG, "Se agrego un email!");
                    }
                }
                Log.d(TAG2, String.valueOf(listaEmails));
                for(String i:listaEmails){
                    final String correoUsuario = i;
                    //Log.d(TAG3, i);
                    Query resultado = db
                            .collection("Usuarios").document(i)
                            .collection("pedidos").whereEqualTo("estado","En espera");
                    resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            if (e != null) {
                                Log.d(TAG, e.getMessage());

                            }

                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                //TODO: Agregar modified

                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    //String email = doc.getDocument().getString("email");
                                    //Log.d(TAG, email);
                                    Log.d("ID BUG", doc.getDocument().getId());
                                    Log.d("OBJETO",doc.getDocument().toString());
                                    Pedido pedido = doc.getDocument().toObject(Pedido.class);
                                    pedido.setIdPedido(doc.getDocument().getId());
                                    pedido.setCorreoCliente(correoUsuario);
                                    lPedidos.add(pedido);
                                    Log.d(TAG, "Se agrego algo a la lista!");
                                    Log.d(TAG, String.valueOf(pedido));
                                    pedidosListAdapter.notifyDataSetChanged();
                                }

                            }

                        }
                    });
                }
            }
        });

        return mView;
    }


}







