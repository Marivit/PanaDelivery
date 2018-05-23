package com.example.virginia.panadelivery.Services;

import android.util.Log;

import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.Modelos.Producto;
import com.google.firebase.firestore.DocumentChange;
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


}
