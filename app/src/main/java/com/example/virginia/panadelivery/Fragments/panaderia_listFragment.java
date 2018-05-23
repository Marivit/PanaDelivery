package com.example.virginia.panadelivery.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.PanaderiasListAdapter;
import com.example.virginia.panadelivery.Modelos.Panaderia;

import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;


import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class panaderia_listFragment extends Fragment {

    private RecyclerView listaPanaderias;
    private String TAG = "Firelog";
    private List<Panaderia> panaderias;
    private PanaderiasListAdapter panaderiasListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment inicial

        /*
        listaProductos = (RecyclerView) findViewById(R.id.productos);
        productos = new ArrayList<>();
        productosListAdapter = new ProductosListAdapter(productos);
        listaProductos.setHasFixedSize(true);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));
        listaProductos.setAdapter(productosListAdapter);
        */

        listaPanaderias =  (RecyclerView) getActivity().findViewById(R.id.listaPanaderia);
        Log.d(TAG, getActivity().getPackageName());

        panaderias = new ArrayList<>();
        panaderiasListAdapter = new PanaderiasListAdapter(panaderias);
        listaPanaderias.setHasFixedSize(true);
        listaPanaderias.setLayoutManager(new LinearLayoutManager(getContext()));
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panaderia_list, container, false);
    }


}
