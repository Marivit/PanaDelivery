package com.example.virginia.panadelivery.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.PedidosListAdapter;
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


public class PedidosListFragment extends Fragment {
    private RecyclerView listaPedidos;
    private String TAG = "Firelog";
    private String TAG2 = "Lista de emails";
    private String TAG3 = "probando";
    private List<Pedido> lPedidos;
    private List<String> listaEmails;
    private PedidosListAdapter pedidosListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FloatingActionButton buttonUbicacion;
    private FloatingActionButton buttonElegir;
    private FirebaseAuth firebaseAuth;
    boolean validar=false;

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
                             Bundle savedInstanceState) throws java.lang.RuntimeException {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_pedidos_list, container, false);
       // buttonUbicacion = (FloatingActionButton) mView.findViewById(R.id.buttonUbicacion);
        //bind();
        validarSolicitud();
        lPedidos = new ArrayList<>();

        listaPedidos = (RecyclerView) mView.findViewById(R.id.pedidosActuales);
        listaPedidos.addItemDecoration(new DividerItemDecoration(listaPedidos.getContext(), DividerItemDecoration.HORIZONTAL));
        Context cont = getActivity().getApplicationContext();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Log.d(TAG3, String.valueOf(validar));
        pedidosListAdapter = new PedidosListAdapter(lPedidos, cont, fm, validar);
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
                            try  {
                                Pedido pedido = doc.getDocument().toObject(Pedido.class);


                                pedido.setIdPedido(doc.getDocument().getId());
                                pedido.setCorreoCliente((String) doc.getDocument().get("Cliente"));
                                lPedidos.add(pedido);
                                Log.d(TAG, "Se agrego algo a la lista!");
                                Log.d(TAG, String.valueOf(pedido));
                                pedidosListAdapter.notifyDataSetChanged();
                            } catch (java.lang.RuntimeException a) {
                                Log.d("AD", doc.getDocument().toString());
                                getActivity().finish();
                            }
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

    public void validarSolicitud() {
        firebaseAuth = FirebaseAuth.getInstance();
        final String  email= firebaseAuth.getCurrentUser().getEmail();
        Log.d("CHECKOUT", email);

        Query resultado = db
                .collection("Pedidos").whereEqualTo("conductor", email);

        if(resultado!=null){
            resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(TAG, e.getMessage());

                    }
                    for (int i = 0; i < queryDocumentSnapshots.getDocumentChanges().size(); i++ ) {

                        DocumentChange doc = queryDocumentSnapshots.getDocumentChanges().get(i);
                        if(doc.getType() == DocumentChange.Type.ADDED || doc.getType() == DocumentChange.Type.MODIFIED) {
                            if (Integer.parseInt(doc.getDocument().get("activo").toString())== 1){
                                validar=true;
                                Log.d(TAG2, "esta dentro de que es 1!");
                                return;

                            }
                            else {
                                Log.d(TAG2, "Entro en en else");
                                validar=false;
                            }
                        }

                    }
                    if (queryDocumentSnapshots.getDocumentChanges().size() == 0) {
                        //validar();
                        //validar=true;
                    }
                }
            });
        } else {
            validar=true;
        }
    }

}







