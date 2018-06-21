package com.example.virginia.panadelivery.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virginia.panadelivery.Activities.CheckoutActivity;
import com.example.virginia.panadelivery.Adapters.ProductosListAdapter;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.example.virginia.panadelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class ProductosListFragment extends Fragment {
    private RecyclerView listaProductos;
    private TextView sumaMontos;
    private String TAG = "Firelog";
    private String TAG2 = "probando";
    private List<Producto> lProductos, lCheckout;
    private ProductosListAdapter productosListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button checkout;
    private String idPanaderia, nombrePanaderia;
    private TextView montoTotal;
    boolean validar=false;

    private FirebaseAuth firebaseAuth;
    private FragmentManager fm;



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
        validarSolicitud();

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
                //boolean validar = validarSolicitud();
                    if(validar){
                        validar();
                    } else if (!validar){
                        Log.d(TAG, "Se hara checkout");
                        Log.d(TAG, Integer.toString(lCheckout.size()));
                        Intent intent = new Intent(getContext(), CheckoutActivity.class);
                        Bundle b = new Bundle();
                        b.putParcelableArrayList("lCheckout", (ArrayList<? extends Parcelable>) lCheckout);
                        b.putString("idPanaderia", idPanaderia);
                        b.putString("nombrePanaderia", nombrePanaderia);
                        int mt = Integer.parseInt(montoTotal.getText().toString());
                        b.putInt("montoTotal", mt);
                        intent.putExtra("listas", b);
                        startActivity(intent);
                    }

            }

        });
    }

    public void validarSolicitud() {
        firebaseAuth = FirebaseAuth.getInstance();
        final String  email= firebaseAuth.getCurrentUser().getEmail();
        Log.d("CHECKOUT", email);

        Query resultado = db
                .collection("Pedidos").whereEqualTo("cliente", email);

        if(resultado!=null){
            resultado.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Log.d(TAG, e.getMessage());

                    }
                    for (int i = 0; i < queryDocumentSnapshots.getDocumentChanges().size(); i++ ) {
                        List<DocumentSnapshot> a = queryDocumentSnapshots.getDocuments();
                        DocumentChange doc = queryDocumentSnapshots.getDocumentChanges().get(i);
                        if(doc.getType() == DocumentChange.Type.ADDED || doc.getType() == DocumentChange.Type.MODIFIED) {
                            if (Integer.parseInt(doc.getDocument().get("activo").toString())== 1){
                                //&& i == queryDocumentSnapshots.getDocumentChanges().size()
                                //validar();
                                validar=true;
                                Log.d(TAG2, "esta dentro de que es 1!");
                                return;

                            }
                            else {
                                //fss.checkout(lCheckout,idPanaderia, nombrePanaderia, montoTotal);
                                Log.d(TAG2, "Entro en en else");
                                validar=false;
                            }
                        }

                    }
                    if (queryDocumentSnapshots.getDocumentChanges().size() == 0) {
                        //validar();

                        validar=false;

                        //validar=true;

                    }
                }
            });
        } else {
            //validar();
            validar=true;
        }
    }

    public void validar() {
        Toast.makeText(getActivity().getApplicationContext(), "Ya tienes un pedido en curso, debes esperar a que finalice!", Toast.LENGTH_LONG).show();
    }


}






