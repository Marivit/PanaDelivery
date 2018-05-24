package com.example.virginia.panadelivery.Services;

import android.util.Log;

import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class FirestoreService {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "FService";
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public FirestoreService() {}


   public List<Producto> getProductosPanaderia(String id ) {
       final List<Producto> listaProductos = new ArrayList<>();
       db.collection("Panaderias").document(id).collection("Productos").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                       listaProductos.add(producto);
                        Log.d(TAG, "Se agrego algo a la lista!");

                   }
               }

           }
       });

       return listaProductos;
    }

    public void checkout(List<Producto> productosCheckout, String idPanaderia) {
        String email = auth.getCurrentUser().getEmail();

        // Disminuir cantidad en stock
        CollectionReference reference = db.collection("Panaderias").document(idPanaderia).collection("Productos");
        for (int j = 0; j < productosCheckout.size(); j++) {
           


        }


        // Agregar a pedidos


    }


}
