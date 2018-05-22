package com.example.virginia.panadelivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Modelos.Producto;
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
    private List<Producto> productos;
    private ProductosListAdapter productosListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProductosListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaProductos = (RecyclerView) getActivity().findViewById(R.id.productos);
        productos = new ArrayList<>();
        productosListAdapter = new ProductosListAdapter(productos);
        listaProductos.setHasFixedSize(true);
        listaProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos.setAdapter(productosListAdapter);

        db.collection("Productos").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                        Producto producto = doc.getDocument().toObject(Producto.class);

                        productos.add(producto);
                        Log.d(TAG, Integer.toString(productosListAdapter.getItemCount()));
                        productosListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productos_list, container, false);
    }


}
