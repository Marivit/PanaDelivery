package com.example.virginia.panadelivery.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.ProductosListAdapter;
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


public class ProductosListFragment extends Fragment {
    private RecyclerView listaProductos;
    private String TAG = "Firelog";
    private List<Producto> lProductos;
    private ProductosListAdapter productosListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


       //TODO Cambiar





    public ProductosListFragment() {
        // Required empty public constructor
    }

    public void setListaProductos(RecyclerView listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_productos_list, container, false);
        lProductos = new ArrayList<>();
        listaProductos = (RecyclerView) mView.findViewById(R.id.productos);
        productosListAdapter = new ProductosListAdapter(lProductos);
        listaProductos.setHasFixedSize(true);
        listaProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos.setAdapter(productosListAdapter);
        String id = this.getArguments().getString("id");


        db.collection("Panaderias").document(id).collection("Productos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, e.getMessage());

                }

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    //TODO: Agregar modified

                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String name = doc.getDocument().getString("nombre");
                        Log.d(TAG, name);
                        Producto producto = doc.getDocument().toObject(Producto.class);

                        lProductos.add(producto);
                        Log.d(TAG, "Se agrego algo a la lista!");
                        productosListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
return mView;

    }


}






