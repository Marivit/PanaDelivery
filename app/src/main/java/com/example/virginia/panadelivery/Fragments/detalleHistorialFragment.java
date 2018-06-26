package com.example.virginia.panadelivery.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.ProductosPedidosAdapter;
import com.example.virginia.panadelivery.Modelos.Pedido;
import com.example.virginia.panadelivery.Modelos.Producto;
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

public class detalleHistorialFragment extends Fragment {


    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String idPedido;
    private List<Producto> lProductos;
    private OnFragmentInteractionListener mListener;
    private  ProductosPedidosAdapter adapter;
    public detalleHistorialFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static detalleHistorialFragment newInstance(String param1, String param2) {
        detalleHistorialFragment fragment = new detalleHistorialFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lProductos = new ArrayList<>();
        View mView = inflater.inflate(R.layout.fragment_detalle_historial, container, false);
        RecyclerView listaProductos = mView.findViewById(R.id.productosHistorial);
        listaProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductosPedidosAdapter(lProductos);
        listaProductos.setAdapter(adapter);
        listaProductos.setHasFixedSize(true);


        Query resultado = db
                .collection("Pedidos").document(getArguments().getString("idPedido")).collection("Productos");

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
                                Producto producto = doc.getDocument().toObject(Producto.class);
                                lProductos.add(producto);
                                adapter.notifyDataSetChanged();



                            }
                        }
                    }
                }
            });
        }
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
