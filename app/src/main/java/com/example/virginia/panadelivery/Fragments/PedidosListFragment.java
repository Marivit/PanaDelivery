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

        db.collection("Pedidos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        if (Integer.parseInt(doc.getDocument().get("activo").toString())== 1
                                && doc.getDocument().get("conductor") == null) {
                            Pedido pedido = doc.getDocument().toObject(Pedido.class);
                            pedido.setIdPedido(doc.getDocument().getId());
                            pedido.setCorreoCliente((String) doc.getDocument().get("Cliente"));
                            lPedidos.add(pedido);
                            Log.d(TAG, "Se agrego algo a la lista!");
                            Log.d(TAG, String.valueOf(pedido));
                            pedidosListAdapter.notifyDataSetChanged();
                        }
                    }
                    if (doc.getType() == DocumentChange.Type.MODIFIED) {
                        for (int i = 0; i < lPedidos.size(); i++) {
                            if (lPedidos.get(i).getIdPedido() == doc.getDocument().getId()) {
                                if (Integer.parseInt(doc.getDocument().get("activo").toString())== 1
                                        && doc.getDocument().get("conductor") == null) {
                                    Pedido pedidonuevo = doc.getDocument().toObject(Pedido.class);
                                    pedidonuevo.setIdPedido(doc.getDocument().getId());
                                    pedidonuevo.setCorreoCliente((String) doc.getDocument().get("Cliente"));
                                    lPedidos.set(i, pedidonuevo);
                                    pedidosListAdapter.notifyDataSetChanged();
                                }
                                else {
                                    lPedidos.remove(i);
                                    pedidosListAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                    }
                    if (doc.getType() == DocumentChange.Type.REMOVED) {
                        for (int i = 0; i < lPedidos.size(); i++) {
                            if (lPedidos.get(i).getIdPedido() == doc.getDocument().getId()) {
                                lPedidos.remove(i);
                                pedidosListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });


        return mView;
    }


}







