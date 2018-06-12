package com.example.virginia.panadelivery.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.virginia.panadelivery.Activities.CheckoutActivity;
import com.example.virginia.panadelivery.Activities.ProfileClienteActivity;
import com.example.virginia.panadelivery.Adapters.ProductosListAdapter;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;
import com.example.virginia.panadelivery.Services.FirestoreService;
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
    private TextView sumaMontos;
    private String TAG = "Firelog";
    private List<Producto> lProductos, lCheckout;
    private ProductosListAdapter productosListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button checkout;
    private String idPanaderia, nombrePanaderia;
    private TextView montoTotal;


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
        lCheckout = new ArrayList<>();
        montoTotal = (TextView) mView.findViewById(R.id.sumaMontos);
        montoTotal = montoTotal;
        listaProductos = (RecyclerView) mView.findViewById(R.id.productos);
        productosListAdapter = new ProductosListAdapter(lProductos, lCheckout, montoTotal);
        listaProductos.setHasFixedSize(true);
        listaProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos.setAdapter(productosListAdapter);
        idPanaderia = this.getArguments().getString("id");
        nombrePanaderia = this.getArguments().getString("nombre");
        checkout = (Button) mView.findViewById(R.id.checkout);


        bind(mView);

        db.collection("Panaderias").document(idPanaderia).collection("Productos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, e.getMessage());

                }

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {


                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String name = doc.getDocument().getString("nombre");

                        Producto producto = doc.getDocument().toObject(Producto.class);

                        producto.setId(doc.getDocument().getId());
                        lProductos.add(producto);
                        Log.d(TAG, "Se agrego algo a la lista!");
                        productosListAdapter.notifyDataSetChanged();
                    }
                    if (doc.getType() == DocumentChange.Type.MODIFIED) {
                        for (int i = 0; i < lProductos.size(); i++) {
                            if (lProductos.get(i).getId() == doc.getDocument().getId()) {
                                Producto productoCambiar = doc.getDocument().toObject(Producto.class);
                                lProductos.get(i).setCantidad(productoCambiar.getCantidad());
                                productosListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

    }
});
        return mView;

    }
    public void bind(final View view) {
        checkout.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {

                    Log.d(TAG, "Se hara checkout");
                    Log.d(TAG, Integer.toString(lCheckout.size()));
                    Intent intent = new Intent(getContext(), CheckoutActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("lCheckout", (ArrayList<? extends Parcelable>) lCheckout);
                    b.putString("idPanaderia", idPanaderia);
                    b.putString("nombrePanaderia", nombrePanaderia);
                    intent.putExtra("listas", b);
                    startActivity(intent);

            }

        });
    }


}






